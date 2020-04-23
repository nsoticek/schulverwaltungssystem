package com.company.models;

import com.company.dbHelper.CourseDb;

import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends Person implements IPerson {

    private CourseDb courseDb = new CourseDb();

    public Teacher(int id) {
        super(id);
    }

    public Teacher(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Teacher(int id, String firstName, String lastName, String username, String password, Enum role) {
        super(id, firstName, lastName, username, password, role);
    }

    @Override
    public void mainMenu() {
        String menuMessage = "\n----Dashboard----" + "\n1. Liste meiner Kurse" + "\n2. Noten verteilen";

        switch (userInput(menuMessage)){
            case "1": // print all courses of current teacher
                printCourses();
                break;
            case "2": // noten verteilen
                printCourses();
                String selectedCourseID = userInput("Wähle einen Kurs: (ID eingeben) ");
                String selectedStudentId = userInput("Wähle einen Studenten: (ID eingeben)");

                //TODO NOCH NICHT FERTIG !!
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
        // Print all courses of the current teacher
        ArrayList<Course> courses = courseDb.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getTeacher().getId() == this.getId()) {
                // Get students of the current course
                ArrayList<Student> studentsOfCourse = courseDb.getStudentsOfCourse(courses.get(i).getId());
                // print current course
                System.out.println(courses.get(i).getId() + " " + courses.get(i).getName() + " " +
                        getOccupiedSeats(courses.get(i).getId()) + "/" + courses.get(i).getMaxSeats());
                for (int j = 0; j < studentsOfCourse.size(); j++) {
                    // print all students of the current course
                    System.out.println("\t" + studentsOfCourse.get(j).getId() + " " + studentsOfCourse.get(j).getFirstName() +
                            " " + studentsOfCourse.get(j).getLastName());
                }
            }
        }
    }

    public int getOccupiedSeats(int courseId) {
        return courseDb.fetchOccupiedSeats(courseId);
    }
}
