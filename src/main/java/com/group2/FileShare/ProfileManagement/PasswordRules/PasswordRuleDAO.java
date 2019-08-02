package com.group2.FileShare.ProfileManagement.PasswordRules;

import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.PasswordRulesObject;
import com.group2.FileShare.database.DatabaseConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordRuleDAO implements IPasswordRuleDAO{

    private static final Logger logger = LogManager.getLogger(PasswordRuleDAO.class);

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
            logger.log(Level.ERROR, "Error while checking passwordRule user in checkRule()" , ex);
        }finally {
            db.closeConnection();
        }

        return rulePassed;
    }

    @Override
    public PasswordRulesObject getPasswordRules(){

        //select the stored procedure
        String query = "{ call password_rules() }";
        ResultSet rs;
        PasswordRulesObject passwordRules = new PasswordRulesObject();

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            rs = stmt.executeQuery();

            while( rs.next() )
            {
                passwordRules.addParameter(rs.getString("rule_parameter"));
                passwordRules.addValue(rs.getInt("rule_value"));
            }

        }
        catch (SQLException ex) {
            logger.log(Level.ERROR, "Failed to retrieve the password rules at getPasswordRules(): ", ex);
        }finally {
            db.closeConnection();
        }

        return passwordRules;
    }
}
