package com.company.Controller;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;
import com.company.models.*;

import java.util.ArrayList;
import java.util.Scanner;

public class AdministratorController implements IPerson {

    public static void mainMenu(DbConnector dbConnector, PersonDb personDb, CourseDb courseDb, Person currentPerson) {
        String menuMessage = "\n----Dashboard----" + "\n1. Neuen Kurs anlegen";

        switch (userInput(menuMessage)){
            case "1":
                String courseName = userInput("Name des Kurses eingeben: ");
                printAllTeachers(personDb, dbConnector);
                String teacherId = userInput("Ordnen Sie dem Kurs einen Lehrer zu: (ID eingeben) ");
                String maxSeats = userInput("Weiviele Studenten können an diesem Kurs teilnehmen: ");
                // Use the input of the user and insert the course to DB
                courseDb.insertCourse(courseName, Integer.parseInt(maxSeats), Integer.parseInt(teacherId), dbConnector);
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

    private static void printAllTeachers(PersonDb personDb, DbConnector dbConnector) {
        ArrayList<Person> persons = personDb.getUser(dbConnector);

        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getRole().equals(Role.TEACHER)) {
                System.out.println(persons.get(i).getId() + " " + persons.get(i).getFirstName() + " "
                        + persons.get(i).getLastName() + " " + persons.get(i).getRole());
            }
        }
    }
}
