package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Administrator extends Person implements IPerson {


    public Administrator(String userName) {
        super(userName);
    }

    @Override
    public void mainMenu() {
        String menuMessage = "\n----Dashboard----" + "\n1. Neuen Kurs anlegen";

        switch (userInput(menuMessage)){
            case "1":
                String courseName = userInput("Name des Kurses eingeben: ");
                printAllTeachers();
                String teacher = userInput("Ordnen Sie dem Kurs einen Lehrer zu: (ID eingeben) ");
                String maxSeats = userInput("Weiviele Studenten können an diesem Kurs teilnehmen: ");
                insertCourseInDb(courseName, teacher, maxSeats);
                break;
            case "2":
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

    private void printAllTeachers() {
        String sqlCommand = "SELECT `id`, `first_name`, `last_name` FROM `user` WHERE role = 'TEACHER'";
        getAndPrintAllTeachers(sqlCommand);
    }

    private void insertCourseInDb(String courseName, String teacher, String maxSeats) {
        String sqlCommand = "INSERT INTO `course`(`name`, `max_seats`, `teacher`) " +
                "VALUES ('" + courseName + "', " + Integer.valueOf(maxSeats) + ", " + Integer.valueOf(teacher) + ")";
        executeUpdate(sqlCommand);
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

    private void getAndPrintAllTeachers(String sqlCommand) {
        // Get all teachers from DB and print them
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                System.out.println("ID: " + id + "\t" + firstName + " " + lastName);
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
