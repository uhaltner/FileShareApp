package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.database.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PasswordModel {

    public void updatePassword(int userId, String rawNewPassword){

        //hash encode the password
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String hashedPassword = passwordEncoder.hashPassword(rawNewPassword);

        //select the stored procedure
        String query = "{ call update_password(?,?) }";

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, hashedPassword);

            stmt.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
