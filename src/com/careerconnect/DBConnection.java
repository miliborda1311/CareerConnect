package com.careerconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/careerconnect";

    private static final String USER = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );

        System.out.println("Database Connected Successfully!");

        return con;
    }
}