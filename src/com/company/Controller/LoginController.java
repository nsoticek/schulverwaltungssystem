package com.company.Controller;

import com.company.dbHelper.DbConnector;
import com.company.dbHelper.LoginDb;
import com.company.dbHelper.PersonDb;
import com.company.models.LoginPerson;
import com.company.models.Person;

public class LoginController {

    public static boolean login(LoginPerson loginPerson, DbConnector dbConnector) {
        // Check in Db if user exists; if yes - return true in 'isPersonExisting'
        boolean isPersonExisting = LoginDb.isExisting(loginPerson, dbConnector);
        if (isPersonExisting)
            System.out.println("Erfolgreich eingeloggt");
        else
            System.out.println("Da stimmt was nicht! Probier es nochmal.\n");
        return isPersonExisting;
    }

    public static Person getPerson(LoginPerson loginPerson, DbConnector dbConnector) {
        // Get person with all properties
        return PersonDb.getPersonFromDb(loginPerson, dbConnector);
    }
}
