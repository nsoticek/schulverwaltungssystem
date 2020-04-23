package com.company.Controller;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;
import com.company.models.Course;
import com.company.models.IPerson;
import com.company.models.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentController implements IPerson {

    public static void mainMenu(DbConnector dbConnector, PersonDb personDb, CourseDb courseDb, Person currentPerson) {
        String menuMessage = "\n----Dashboard----" + "\n1. Alle Kurse anzeigen" +
                "\n2. In einen Kurs einschreiben " + "\n3. Meine Kurs anzeigen";

        switch (userInput(menuMessage)){
            case "1": // print all courses
                printCourses(courseDb, dbConnector);
                break;
            case "2": // enroll in course
                printCourses(courseDb, dbConnector);
                String selectedCourseID = userInput("Wähle einen Kurs: (ID eingeben) ");
                enrollInCourse(selectedCourseID, currentPerson, courseDb, dbConnector);
                break;
            case "3": // print my courses
                printAllAssignedCourses(currentPerson.getId(), courseDb, dbConnector);
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

    private static void printCourses(CourseDb courseDb, DbConnector dbConnector) {
        ArrayList<Course> courses = courseDb.getCourses(dbConnector);

        // Loop trough all courses and print them
        for (int i = 0; i < courses.size(); i++) {
            int occupiedSeats = getOccupiedSeats(courses.get(i).getId(), courseDb, dbConnector);

            System.out.println(courses.get(i).getId() + " " + courses.get(i).getName() +
                    " (" + occupiedSeats + "/" + courses.get(i).getMaxSeats() + ") " +
                    courses.get(i).getTeacher().getFirstName() + " " + courses.get(i).getTeacher().getLastName());
        }
    }

    public static int getOccupiedSeats(int courseId, CourseDb courseDb, DbConnector dbConnector) {
        return courseDb.fetchOccupiedSeats(courseId, dbConnector);
    }

    private static void enrollInCourse(String selectedCourseID, Person currentPerson, CourseDb courseDb, DbConnector dbConnector) {
        courseDb.insertCourseStudent(selectedCourseID, currentPerson.getId(), dbConnector);
    }

    private static void printAllAssignedCourses(int studentId, CourseDb courseDb, DbConnector dbConnector) {
        ArrayList<Course> studentCourses = courseDb.getStudentCourses(studentId, dbConnector);

        for (int i = 0; i < studentCourses.size(); i++) {
            int occupiedSeats = getOccupiedSeats(studentCourses.get(i).getId(), courseDb, dbConnector);

            System.out.println(studentCourses.get(i).getId() + " " + studentCourses.get(i).getName() + " (" +
                    occupiedSeats + "/" + studentCourses.get(i).getMaxSeats() + ") " +
                    studentCourses.get(i).getTeacher().getFirstName() + " " + studentCourses.get(i).getTeacher().getLastName());
        }
    }
}
