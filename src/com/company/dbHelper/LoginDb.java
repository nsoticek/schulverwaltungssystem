package com.company.dbHelper;

import com.company.models.LoginPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDb {

    public static boolean isExisting(LoginPerson loginPerson, DbConnector dbConnector) {
        // Check if username and password are matching with DB data, if yes return true;
        boolean isPersonExisting = false;
        ResultSet rs = dbConnector.fetchData("SELECT id FROM user " +
                "WHERE user_name = '" + loginPerson.getUsername() + "' AND password = " + loginPerson.getPassword() + "");
        if (rs == null) {
            System.out.println("Error bei loginAbfrage! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                if(id != null)
                    isPersonExisting = true;
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            dbConnector.closeConnection();
        }
        return isPersonExisting;
    }
}
