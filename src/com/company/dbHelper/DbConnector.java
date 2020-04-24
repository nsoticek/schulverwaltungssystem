package com.company.dbHelper;

import java.sql.*;

public class DbConnector {

    private Connection connection = null;
    private Statement statement = null;
    private String url = "jdbc:mysql://localhost:3306/school_management_system?user=root";

    private void buildConnection() {
        try {
            String databaseUrl = url;
            connection = DriverManager.getConnection(databaseUrl);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("could not build connection");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("could not close connection");
            e.printStackTrace();
        }
    }

    public ResultSet fetchData(String sql) {
        buildConnection();
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Konnte Daten nicht abrufen");
            e.printStackTrace();
            closeConnection();
        }
        return null;
    }

    public boolean delete(String sql) {
        buildConnection();
        try {
            int result = statement.executeUpdate(sql);
            if (result == 0) {
                System.out.println("Keinen passenden Datensatz gefunden");
                return false;
            } else {
                System.out.println("Delete erfolgreich!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Konnte Daten nicht löschen");
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean insertData(String sql) {
        buildConnection();
        try {
            int result = statement.executeUpdate(sql);
            if (result == 0) {
                System.out.println("Error beim inserten!");
                return false;
            } else {
                System.out.println("Daten erfolgreich übermittelt!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Konnte Daten nicht inserten");
            e.printStackTrace();
            closeConnection();
            return false;
        } finally {
            closeConnection();
        }
    }
}
