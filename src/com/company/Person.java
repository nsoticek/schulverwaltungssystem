package com.company;

import java.sql.*;

public class Person implements IPerson {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Enum role;

    public Person(String username) {
        this.username = username;
        initData();
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    final boolean isPasswordMatching() {
        // Check if user input == password from DB
        boolean isMatching = false;
        String sqlCommand = "SELECT password, role FROM `user` WHERE user_name = '" + username + "'";
        String password = getPassword(sqlCommand);
        if (this.password.equals(password))
            isMatching = true;
        return isMatching;
    }

    private String getPassword(String sqlCommand) {
        // Get and set password from DB to this.Person
        String password = "";
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                password = rs.getString("password");
                Role role = Role.valueOf(rs.getString("role"));
                this.role = role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;
    }

    private void initData() {
        // get missing data of this.Person from DB
        String sqlCommand = "SELECT id, first_name, last_name FROM user WHERE user_name = '" + this.username + "'";
        setUserData(sqlCommand);
    }

    public void setUserData(String sqlCommand) {
        // Get user data from DB and set it
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                this.id = rs.getInt("id");
                this.firstName = rs.getString("first_name");
                this.lastName = rs.getString("last_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
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
}
