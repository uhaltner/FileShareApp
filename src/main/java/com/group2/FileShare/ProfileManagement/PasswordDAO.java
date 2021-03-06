package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.database.DatabaseConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PasswordDAO implements IPasswordDAO {

    private static final Logger logger = LogManager.getLogger(PasswordDAO.class);

    @Override
    public void updatePassword(int userId, String rawNewPassword)
    {
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
            logger.log(Level.INFO, "[user:"+userId+"] successfully updated the password");


        }
        catch (SQLException ex) {
            logger.log(Level.ERROR, "Failed to update password for the [user:"+userId+"] at updatePassword(): ", ex);
        }finally {
            db.closeConnection();
        }

        return;
    }

    @Override
    public void updateRecoveryPassword(String userEmail, String rawNewPassword)
    {
        //hash encode the password
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String hashedPassword = passwordEncoder.hashPassword(rawNewPassword);

        //select the stored procedure
        String query = "{ call update_recovery_password(?,?) }";

        //establish database connection
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setString(1, userEmail);
            stmt.setString(2, hashedPassword);

            stmt.executeQuery();
        }
        catch (SQLException ex) {
            logger.log(Level.ERROR, "Failed to update password for the [email:"+userEmail+"] at updateRecoveryPassword(): ", ex);
        }finally {
            db.closeConnection();
        }

        return;
    }

}
