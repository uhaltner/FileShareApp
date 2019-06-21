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

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            //add email to query
            stmt.setString(1, email);

            //get the results
            rs = stmt.executeQuery();
            System.out.println("ResultSet: " + rs);

            //parse results
            boolean userExists = rs.getBoolean(1);
            System.out.println("userExists: " + userExists);

            if(userExists){
                return true;
            }else{
                return false;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return true;
    }

    public void createProfile(String firstName, String lastName, String email, String rawPassword){

        //hash encode the password
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String hashedPassword = passwordEncoder.hashPassword(rawPassword);

        //select the stored procedure
        String query = "{ call create_profile(?,?,?,?) }";

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, hashedPassword);

            stmt.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }




    }


}
