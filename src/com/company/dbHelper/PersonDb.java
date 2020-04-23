package com.company.dbHelper;

import com.company.models.Person;
import com.company.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDb {

    private DbConnector dbConnector = new DbConnector();
    private ArrayList<Person> user = new ArrayList<>();

    public ArrayList<Person> getUser() {
        if (user.isEmpty()) {
            fetchUser();
        }
        return user;
    }

    private void fetchUser() {
        // delete all old entries
        user.clear();

        ResultSet rs = dbConnector.fetchData("SELECT * FROM user");
        if (rs == null) {
            System.out.println("Error bei fetchUser! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("user_name");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String password = rs.getString("password");
                Role role = Role.valueOf(rs.getString("role"));

                user.add(new Person(id, firstName, lastName, username, password, role));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }
}
