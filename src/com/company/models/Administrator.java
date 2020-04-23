package com.company.models;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.PersonDb;

import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends Person implements IPerson {

    private PersonDb personDb = new PersonDb();
    private CourseDb courseDb = new CourseDb();

    public Administrator(int id, String firstName, String lastName, String username, String password, Enum role) {
        super(id, firstName, lastName, username, password, role);
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
        ArrayList<Person> persons = personDb.getUser();

        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getRole().equals(Role.TEACHER)) {
                System.out.println(persons.get(i).getId() + " " + persons.get(i).getFirstName() + " "
                        + persons.get(i).getLastName() + " " + persons.get(i).getRole());
            }
        }
    }

    private void insertCourseInDb(String courseName, String teacherId, String maxSeats) {
        Course course = new Course(courseName, Integer.parseInt(maxSeats), new Teacher(Integer.parseInt(teacherId)));
        courseDb.insertCourse(course);
    }
}
