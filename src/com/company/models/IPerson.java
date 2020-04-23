package com.company.models;

import com.company.dbHelper.CourseDb;
import com.company.dbHelper.DbConnector;
import com.company.dbHelper.PersonDb;

public interface IPerson {

    public static void mainMenu(DbConnector dbConnector, PersonDb personDb, CourseDb courseDb, Person currentPerson){};
}
