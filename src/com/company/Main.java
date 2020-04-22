package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean isPasswordMatching = false;
        Person[] currentPerson = new Person[1];

        Person person = getUserInputAndCreatePerson();
        getAndCheckPassword(isPasswordMatching, person);

        Enum role = person.getRole();
        if(role.equals(Role.ADMINISTRATOR)) {
            Administrator administrator = new Administrator(person.getUsername());
            currentPerson[0] = administrator;
        } else if(role.equals(Role.TEACHER)) {
            Teacher teacher = new Teacher(person.getUsername());
            currentPerson[0] = teacher;
        } else if(role.equals(Role.STUDENT)) {
            Student student = new Student(person.getUsername());
            currentPerson[0] = student;
        } else {
            System.out.println("Etwas stimmt mit deiner berechtigung nicht! " +
                    "Bitte beim Support melden.");
        }

        while (true) {
            currentPerson[0].mainMenu();
        }
    }

    private static void getAndCheckPassword(boolean isPasswordMatching, Person person) {
        // Get input from user for login; If userInput is machting with DB data -> isPasswordMatching = true;
        while (!isPasswordMatching) {
            isPasswordMatching = person.isPasswordMatching();
            if (isPasswordMatching)
                System.out.println("Erfolgreich eingeloggt");
            else
                System.out.println("Da stimmt was nicht! Probier es nochmal.\n");
        }
    }

    private static Person getUserInputAndCreatePerson() {
        String username = userInput("Username: ");
        String password = userInput("Passwort: ");
        return new Person(username, password);
    }


    private static String userInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
