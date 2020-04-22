package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Teacher extends Person implements IPerson {

    public Teacher(String userName) {
        super(userName);
    }

    @Override
    public void mainMenu() {
        String menuMessage = "\n----Dashboard----" + "\n1. Liste meiner Kurse" + "\n2. Noten verteilen";

        switch (userInput(menuMessage)){
            case "1": // print all courses
                int courseId = printCourses();
                printStudents(courseId);
                break;
            case "2": // noten verteilen
                int courseId = printCourses();
                printStudents(courseId);
                String selectedCourseID = userInput("Wähle einen Kurs: (ID eingeben) ");

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

    private int printCourses() {
        String sqlCommand = "SELECT id, name, max_seats FROM course WHERE teacher = " + this.getId();
        return getAndPrintCourses(sqlCommand);
    }

    private void printStudents(int courseId) {
        String sqlCommand = "SELECT user.id, user.first_name, user.last_name FROM course_student " +
                "INNER JOIN user ON course_student.student = user.id WHERE user.role = 'STUDENT' AND course = " + courseId;
        getAndPrintStudents(sqlCommand);
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

    private int getAndPrintCourses(String sqlCommand) {
        int id = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                id = rs.getInt("id");
                String name = rs.getString("name");
                int maxSeats = rs.getInt("max_seats");

                System.out.println(id + ". " + name + " " + maxSeats);
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
        return id;
    }

    private void getAndPrintStudents(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                System.out.println(id + ". " + firstName + " " + lastName);
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
