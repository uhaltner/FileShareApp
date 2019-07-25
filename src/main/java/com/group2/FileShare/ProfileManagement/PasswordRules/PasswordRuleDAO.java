package com.group2.FileShare.ProfileManagement.PasswordRules;

import com.group2.FileShare.database.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordRuleDAO implements IPasswordRuleDAO{

    @Override
    public boolean checkRule(String query, String password){

        ResultSet rs;
        boolean rulePassed = false;

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            //add email to query
            stmt.setString(1, password);

            //get the results
            rs = stmt.executeQuery();

            //parse results
            if(rs.next()) {
                rulePassed = rs.getBoolean(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally {
            db.closeConnection();
        }

        return rulePassed;
    }
}
