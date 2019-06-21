package com.group2.FileShare.SignIn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group2.FileShare.User.User;

public class SignInDAO implements ISignInDAO {

	// Would refactor once we have DB connection
	@Override
	public User getUserWith(String email, String password) {
		
	   	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    String url1 = "jdbc:mysql://localhost:3306/QA?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		    String username = "root";
		    String dbPassword = "";
		    try (Connection connection = DriverManager.getConnection(url1, username, dbPassword);

	        // Step 2:Create a statement using connection object
	        PreparedStatement preparedStatement = connection
	        .prepareStatement("SELECT user.user_id, user.email, user.first_name, user.last_name FROM user JOIN person ON user.user_id = person.user_id WHERE user.email = ? AND person.password = ? ")) {
	        preparedStatement.setString(1, email);
	        preparedStatement.setString(2, password);

	        System.out.println(preparedStatement);
	        ResultSet rs = preparedStatement.executeQuery();
	       
	        while(rs.next()) {
	    	  User user = new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
	    	  return user;
	        }
	        
	        connection.close();

	        } catch (SQLException e) {
	            // process sql exception
	            printSQLException(e);
	        }
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
	}
	

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}