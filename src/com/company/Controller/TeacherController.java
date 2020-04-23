package com.company.Controller;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;
import com.company.models.Course;
import com.company.models.IPerson;
import com.company.models.Person;
import com.company.models.Student;

import java.util.ArrayList;
import java.util.Scanner;

public class TeacherController implements IPerson {

    public static void mainMenu(DbConnector dbConnector, PersonDb personDb, CourseDb courseDb, Person currentPerson) {
        String menuMessage = "\n----Dashboard----" + "\n1. Liste meiner Kurse" + "\n2. Noten verteilen";

        switch (userInput(menuMessage)){
            case "1": // print all courses of current teacher
                printCourses(courseDb, dbConnector, currentPerson);
                break;
            case "2": // noten verteilen
                printCourses(courseDb, dbConnector, currentPerson);
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

    private static void printCourses(CourseDb courseDb, DbConnector dbConnector, Person currentPerson) {
        // Print all courses of the current teacher
        ArrayList<Course> courses = courseDb.getCourses(dbConnector);

        for (int i = 0; i < courses.size(); i++) {
            int occupiedSeats = getOccupiedSeats(courses.get(i).getId(), courseDb, dbConnector);
            if(courses.get(i).getTeacher().getId() == currentPerson.getId()) {
                // Get students of the current course
                ArrayList<Student> studentsOfCourse = courseDb.getStudentsOfCourse(courses.get(i).getId(), dbConnector);
                // print current course
                System.out.println(courses.get(i).getId() + " " + courses.get(i).getName() + " " +
                        occupiedSeats + "/" + courses.get(i).getMaxSeats());
                for (int j = 0; j < studentsOfCourse.size(); j++) {
                    // print all students of the current course
                    System.out.println("\t" + studentsOfCourse.get(j).getId() + " " + studentsOfCourse.get(j).getFirstName() +
                            " " + studentsOfCourse.get(j).getLastName());
                }
            }
        }
    }

    public static int getOccupiedSeats(int courseId, CourseDb courseDb, DbConnector dbConnector) {
        return courseDb.fetchOccupiedSeats(courseId, dbConnector);
    }
}
