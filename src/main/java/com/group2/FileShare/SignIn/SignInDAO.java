package com.group2.FileShare.SignIn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group2.FileShare.ProfileManagement.IPasswordEncoder;
import com.group2.FileShare.User.User;
import com.group2.FileShare.database.DatabaseConnection;

public class SignInDAO implements ISignInDAO {
	
	@Override
    public User getUserWith(SignInForm signInForm, IPasswordEncoder passwordEncoder) {

        String query = "{ call get_profile(?) }";
        
        DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

        try (Connection conn = db.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {
        	
            stmt.setString(1, signInForm.getEmail());
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
            	String retrievedPassword = rs.getString("password");
            	if (passwordEncoder.matches(signInForm.getPassword(), retrievedPassword)) {
            		User user = new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
          	    	return user;
            	}
  	        }

            db.closeConnection();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}