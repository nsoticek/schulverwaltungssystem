package com.company.models;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;

import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends Person {

    public Teacher(int id, String firstName, String lastName, String username, Enum role) {
        super(id, firstName, lastName, username, role);
    }
}
