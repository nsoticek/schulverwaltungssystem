package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Student extends Person implements IPerson {

    public Student(String userName) {
        super(userName);
    }

    @Override
    public void mainMenu() {
        String menuMessage = "\n----Dashboard----" + "\n1. Alle Kurse anzeigen" +
                "\n2. In einen Kurs einschreiben " + "\n3. Meine Kurs anzeigen";

        switch (userInput(menuMessage)){
            case "1": // print all courses
                printCourses();
                break;
            case "2": // enroll in course
                printCourses();
                String selectedCourseID = userInput("Wähle einen Kurs: (ID eingeben) ");
                insertSelectionInDb(selectedCourseID);
                break;
            case "3": // print my courses
                printAllAssignedCourses();
                break;
            default:
                System.out.println("Falsche Eingabe!");
        }
    }

    private static String userInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }

    private void printCourses() {
        String sqlCommand = "SELECT course.id, course.name, course.max_seats, user.first_name, user.last_name FROM course\n" +
                "INNER JOIN user ON course.teacher = user.id;";
        getAndPrintAllCourses(sqlCommand);
    }

    public int getOccupiedSeats(int courseId) {
        String sqlCommand = "SELECT COUNT(*) AS occupied_seats FROM course_student WHERE course = " + courseId;
        return getOccupiedSeatsFromDb(sqlCommand);
    }

    private void insertSelectionInDb(String selectedCourseID) {
        String sqlCommand = "INSERT INTO `course_student`(`course`, `student`) " +
                "VALUES (" + Integer.valueOf(selectedCourseID) + "," + this.getId() + ")";
        executeUpdate(sqlCommand);
    }

    private void printAllAssignedCourses() {
        String sqlCommand = "SELECT course.id, course.name, course.teacher " +
                "FROM course_student INNER JOIN course ON course_student.course = course.id WHERE student = 4";
        printAssignedCourses(sqlCommand);
    }

    private void executeUpdate(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlCommand);
            System.out.println("Erfolgreich ausgeführt!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAndPrintAllCourses(String sqlCommand) {
        // Get all courses from DB and print them
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int maxSeats = rs.getInt("max_seats");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                System.out.println(id + ". " + name + " (" + getOccupiedSeats(id) + "/" + maxSeats + ") "
                        + firstName + " " + lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getOccupiedSeatsFromDb(String sqlCommand) {
        int occupiedSeats = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                occupiedSeats = rs.getInt("occupied_seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return occupiedSeats;
    }

    private void printAssignedCourses(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
               int id = rs.getInt("id");
               String name = rs.getString("name");
               int teacherID = rs.getInt("teacher");

                System.out.println(id + " " + name + " " + teacherID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
