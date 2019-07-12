package com.group2.FileShare.SignUp;

import com.group2.FileShare.ProfileManagement.PasswordEncoder;
import com.group2.FileShare.database.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpModel {

    public boolean userExist(String email){


        //select the stored procedure
        String query = "{ call user_exists(?) }";

        ResultSet rs;
        boolean userExists = true;

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            //add email to query
            stmt.setString(1, email);

            //get the results
            rs = stmt.executeQuery();

            //parse results
            if(rs.next()) {
                 userExists = rs.getBoolean(1);
            }

            db.closeConnection();

            return userExists;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (db != null) {
                    db.closeConnection();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return true;
    }

    public int createProfile(String firstName, String lastName, String email, String rawPassword){

        //hash encode the password
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String hashedPassword = passwordEncoder.hashPassword(rawPassword);

        //select the stored procedure
        String query = "{ call create_profile(?,?,?,?) }";

        ResultSet rs;
        int userId = 0;

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, hashedPassword);

            rs = stmt.executeQuery();

            //parse results
            if(rs.next()) {
                userId = rs.getInt(1);
            }

            db.closeConnection();

            return userId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (db != null) {
                    db.closeConnection();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return 0;
    }


}
