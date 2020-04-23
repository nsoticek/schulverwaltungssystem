package com.company;

import com.company.models.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Person[] currentPerson = new Person[1];
        Person person = new Person(false);
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            person = getUserInputAndCreatePerson();
            checkIfPasswordIsCorrect(person);
        }

        Enum role = person.getRole();
        if(role.equals(Role.ADMINISTRATOR)) {
            Administrator administrator = new Administrator(person.getId(), person.getFirstName(),
                    person.getLastName(), person.getUsername(), person.getPassword(), person.getRole());
            currentPerson[0] = administrator;
        } else if(role.equals(Role.TEACHER)) {
            Teacher teacher = new Teacher(person.getId(), person.getFirstName(),
                    person.getLastName(), person.getUsername(), person.getPassword(), person.getRole());
            currentPerson[0] = teacher;
        } else if(role.equals(Role.STUDENT)) {
            Student student = new Student(person.getId(), person.getFirstName(),
                    person.getLastName(), person.getUsername(), person.getPassword(), person.getRole());
            currentPerson[0] = student;
        } else {
            System.out.println("Etwas stimmt mit deiner Berechtigung nicht! " +
                    "Bitte beim Support melden.");
        }

        while (true) {
            currentPerson[0].mainMenu();
        }
    }

    private static void checkIfPasswordIsCorrect(Person person) {
        // Check if userInput is machting with DB data
        // If yes set all data from DB to this.person
            person.isPasswordMatching();
            if (person.isLoggedIn())
                System.out.println("Erfolgreich eingeloggt");
            else
                System.out.println("Da stimmt was nicht! Probier es nochmal.\n");
    }

    private static Person getUserInputAndCreatePerson() {
        String username = userInput("Username: ");
        String password = userInput("Passwort: ");
        return new Person(username, password, false);
    }

    private static String userInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
