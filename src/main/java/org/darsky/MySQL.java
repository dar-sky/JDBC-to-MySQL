package org.darsky;

import java.sql.*;

public class MySQL {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static String DATABASE_NAME = "";

    private static String TABLE_NAME = "";

    private static Connection conn = null;


    public MySQL(String db, String table) {
        this.DATABASE_NAME = db;
        this.TABLE_NAME = table;
        try {
            this.conn = getConnection();
        } catch (SQLException e) {
            System.out.println("Connection creation failed");
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Connected to Server!");
        } catch (SQLException e) {
            System.err.println("Failed to connect to Server");
            throw e;
        }
        return conn;
    }

    public static ResultSet showDB() throws SQLException {
        Statement stm = conn.createStatement();

        String sql = "SHOW DATABASES";
        ResultSet res = stm.executeQuery(sql);
        return res;
    }

    public static ResultSet showTable() throws SQLException {
        Statement stm = conn.createStatement();

        String sql = "SHOW TABLES";
        ResultSet res = stm.executeQuery(sql);
        return res;
    }

    public static void createDatabase() throws SQLException {
        Statement stm = conn.createStatement();

        String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
        stm.executeUpdate(sql);
        System.out.println("Database " + DATABASE_NAME + " created successfully !!");
//        stm.close();
    }

    public static void deleteDatabase() throws SQLException {
        Statement stm = conn.createStatement();

        String sql = "DROP DATABASE IF EXISTS " + DATABASE_NAME;
        stm.executeUpdate(sql);
        System.out.println("Database " + DATABASE_NAME + " deleted successfully !!");
//        stm.close();
    }

    public static void useDB() throws SQLException {
        Statement stm = conn.createStatement();

        String sql = "USE " + DATABASE_NAME;
        stm.executeUpdate(sql);
        System.out.println("Database " + DATABASE_NAME + " changed successfully !!");
//        stm.close();
    }

    public static void createTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(255) NOT NULL,"
                + "age INT,"
                + "PRIMARY KEY (id)"
                + ")";
        stmt.executeUpdate(sql);
        System.out.println("Table " + TABLE_NAME + " created successfully");

//        stmt.close();
    }

    public void init() {
        try {
            System.out.print("Checking all available databases >> ");
            ResultSet res = showDB();
            Boolean flag = false;

            while (res.next()) {
                String db = res.getString("Database");
                if (db.equals(DATABASE_NAME.toLowerCase())) {
                    flag = true;
                }
                System.out.print(db + " ");
            }
            res.close();
            if (flag) {
                System.out.println("\nDatabase " + DATABASE_NAME + " already there");
            } else {
                System.out.println("\nOur database is not there.\nTrying to create database " + DATABASE_NAME);
                createDatabase();
            }
            useDB();
            flag = false;


            res = showTable();

            System.out.print("Checking all available Tables  >> ");
            while (res.next()) {
                String table = res.getString(1);
                if (table.equals(TABLE_NAME.toLowerCase())) {
                    flag = true;
                }
                System.out.print(table + " ");
            }

            if (flag) {
                System.out.println("\nTable " + TABLE_NAME + " already there");
            } else {
                System.out.println("\nOur table is not there.\nTrying to create Table " + TABLE_NAME);
                createTable();
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed with " + e.getMessage());
        }
    }

    public int addItem() throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO " + TABLE_NAME + "(name, age) VALUES ('mansi', 10)";

        int rowAffected = stm.executeUpdate(sql);
//        stm.close();
        return rowAffected;
    }


    public int addItem(String name, int age) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(name,age) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, age);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    public ResultSet getItems() throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM " + TABLE_NAME;

        ResultSet res = stm.executeQuery(sql);
//        stm.close();
//        res.close();
        return res;
    }

    public void destroy() {
        try {
            deleteDatabase();
        } catch (SQLException e) {
            System.out.println("Cleaning database failed");
            System.out.println(e.getMessage());
        }
    }
}