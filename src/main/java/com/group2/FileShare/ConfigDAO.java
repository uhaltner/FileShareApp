package com.group2.FileShare;

import com.group2.FileShare.database.DatabaseConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class ConfigDAO implements IConfigDAO {

	private ResultSet resultSet = null;
	private String query;
	private DatabaseConnection databaseConnection;
	private static final Logger logger = LogManager.getLogger(ConfigDAO.class);

	public ConfigDAO()
	{}

	@Override
	public boolean getConfig(String parameter) throws Exception {
		Boolean config_value = null;
		try
		{
			query = "{ call get_config(?) }";
			databaseConnection = DatabaseConnection.getdbConnectionInstance();
			CallableStatement statement = databaseConnection.getConnection().prepareCall(query);
			statement.setString(1, parameter);
			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				config_value = resultSet.getBoolean("config_value");
			}
			if (null != config_value) {
				return config_value;
			}
		}
		catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to retrieve the config value with query:" + query +" for parameter: "+ parameter +" at getConfig(String parameter)" , e);
		}
		finally
		{
			try
			{
				if ( null != databaseConnection ) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at getTotalFileSize()", ex);
			}
		}
		throw new Exception("Failed to retrieve the config value with query:" + query +" for parameter: "+ parameter +" at getConfig(String parameter)");
	}


}
