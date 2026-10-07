package com.careerconnect;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("SUCCESS: Database Connected!");
        } else {
            System.out.println("FAILED: Database Connection Failed!");
        }
    }
}