package com.group2.FileShare.SignIn;

import com.group2.FileShare.ProfileManagement.IPasswordEncoder;
import com.group2.FileShare.User.User;
import com.group2.FileShare.database.DatabaseConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInDAO implements ISignInDAO {

    private static final Logger logger = LogManager.getLogger(SignInDAO.class);

	@Override
    public User getUserWith(SignInForm signInForm, IPasswordEncoder passwordEncoder) {

        String query = "{ call get_profile(?) }";
        
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {
        	
            stmt.setString(1, signInForm.getEmail());
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
            	String retrievedPassword = rs.getString("password");
            	if (passwordEncoder.matches(signInForm.getPassword(), retrievedPassword)) {
            		User user = new User(rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));

                    db.closeConnection();
          	    	return user;
            	}
  	        }

        } catch (SQLException ex) {
            logger.log(Level.ERROR, "Error while getting user with query:" +query +" of email: "+signInForm.getEmail() +" at getUserWith()" , ex);
        }
        finally
        {
            try
            {
                if ( null != db ) {
                    db.closeConnection();
                }
            }
            catch (Exception ex) {
                logger.log(Level.ERROR, "Failed to close database connection at getUserWith()", ex);
            }
        }

        return null;
    }

}