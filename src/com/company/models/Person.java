package com.company.models;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private Enum role;

    public Person(int id, String firstName, String lastName, String username, Enum role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
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
}
