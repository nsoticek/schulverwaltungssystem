package com.company.models;

import com.company.dbHelper.CourseDb;

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Person implements IPerson {

    private CourseDb courseDb = new CourseDb();

    public Student(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Student(int id, String firstName, String lastName, String username, String password, Enum role) {
        super(id, firstName, lastName, username, password, role);
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
                enrollInCourse(selectedCourseID);
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
        ArrayList<Course> courses = courseDb.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i).getId() + " " + courses.get(i).getName() + " (" + getOccupiedSeats(courses.get(i).getId()) + "/" + courses.get(i).getMaxSeats() + ") " +
                    courses.get(i).getTeacher().getFirstName() + " " + courses.get(i).getTeacher().getLastName());
        }
    }

    public int getOccupiedSeats(int courseId) {
        return courseDb.fetchOccupiedSeats(courseId);
    }

    private void enrollInCourse(String selectedCourseID) {
        courseDb.insertCourseStudent(selectedCourseID, this.getId());
    }

    private void printAllAssignedCourses() {
        courseDb.getStudentCourses(this.getId());
    }
}
