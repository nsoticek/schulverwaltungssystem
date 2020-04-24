package com.company;

import com.company.Controller.AdministratorController;
import com.company.Controller.LoginController;
import com.company.Controller.StudentController;
import com.company.Controller.TeacherController;
import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;
import com.company.models.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DbConnector dbConnector = new DbConnector();
        PersonDb personDb = new PersonDb();
        CourseDb courseDb = new CourseDb();
        Person person = null;
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            // Get input from user and create a loginPerson with it
            LoginPerson loginPerson = getUserInputAndCreateLoginPerson();
            // try to login the loginPerson
            isLoggedIn = LoginController.login(loginPerson, dbConnector);
            // Get current userData from Db and create person
            person = LoginController.getPerson(loginPerson, dbConnector);
        }

        Enum role = person.getRole();
        if(role.equals(Role.ADMINISTRATOR)) {
            Administrator administrator = new Administrator(person.getId(), person.getFirstName(),
                    person.getLastName(), person.getUsername(), person.getRole());
            while (true)
                AdministratorController.mainMenu(dbConnector, personDb, courseDb, administrator);
        } else if(role.equals(Role.TEACHER)) {
            Teacher teacher = new Teacher(person.getId(), person.getFirstName(),
                    person.getLastName(), person.getUsername(), person.getRole());
            while (true)
                TeacherController.mainMenu(dbConnector, personDb, courseDb, teacher);
        } else if(role.equals(Role.STUDENT)) {
            Student student = new Student(person.getId(), person.getFirstName(),
                    person.getLastName(), person.getUsername(), 0, person.getRole());
            while (true)
                StudentController.mainMenu(dbConnector, personDb, courseDb, student);
        } else {
            System.out.println("Etwas stimmt mit deiner Berechtigung nicht! " +
                    "Bitte beim Support melden.");
        }
    }

    private static LoginPerson getUserInputAndCreateLoginPerson() {
        String username = userInput("Username: ");
        String password = userInput("Passwort: ");
        return new LoginPerson(username, password);
    }

    private static String userInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
