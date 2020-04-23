package com.company.models;

import com.company.dbHelper.PersonDb;

public class Person implements IPerson {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Enum role;
    private boolean isLoggedIn;
    private PersonDb personDb = new PersonDb();

    public Person(int id) {
        this.id = id;
    }

    public Person(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String username, String password, boolean isLoggedIn) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }

    public Person(int id, String firstName, String lastName, String username, String password, Enum role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isLoggedIn = isLoggedIn;
    }

    public void isPasswordMatching() {
        // Check if user input == password from DB (get the right password from PersonDb.class (ArrayList))
        // select * from person where username = this.username AND password = this.password
        // if resutl.count > 0 ==> passwordmatching true
        // createPerson(resultset)
        personDb.getUser();
        for (int i = 0; i < personDb.getUser().size(); i++) {
            if (personDb.getUser().get(i).getUsername().equals(this.username)) {
                if (personDb.getUser().get(i).getPassword().equals(this.password)) {
                    // Logged in successfully
                    this.isLoggedIn = true;
                    this.id = personDb.getUser().get(i).getId();
                    this.firstName = personDb.getUser().get(i).getFirstName();
                    this.lastName = personDb.getUser().get(i).getLastName();
                    this.role = personDb.getUser().get(i).getRole();
                }
            }
        }
    }

    @Override
    public void mainMenu() {

    }

    public Enum getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
