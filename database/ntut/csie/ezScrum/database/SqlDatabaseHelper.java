package ntut.csie.ezScrum.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import ntut.csie.ezScrum.useCase.SqlSpecification;

public class SqlDatabaseHelper {
	private Properties properties;
	private String baseDirPath = System.getProperty("user.dir");
	private String configFile = "mysqlDB.ini";
	private String configFilePath = baseDirPath + File.separator + configFile;
	private Connection connection;
	private Statement statement;
	
	public void connection() {
		properties = new Properties();System.out.println(configFilePath);
		try {
			properties.load(new FileInputStream(configFilePath));
		}catch(FileNotFoundException e) {
			System.out.println("mysqlDB.ini file not found");
			e.printStackTrace();
			return;
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		String serverUrl = properties.getProperty("ServerUrl", "127.0.0.1");
		String databaseName = properties.getProperty("DatabaseName");
		String account = properties.getProperty("Account");
		String password = properties.getProperty("Password");
		if(databaseName == null || databaseName.isEmpty()) {
			System.out.println("Please input database name.");
			return;
		}
		if(account == null || account.isEmpty()) {
			System.out.println("Please input account.");
			return;
		}
		if(password == null || password.isEmpty()) {
			System.out.println("Please input password.");
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+serverUrl+":3306/"+databaseName, account, password);
			statement = connection.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(SqlSpecification sqlSpecification) {
		String query = sqlSpecification.toSqlQuery();
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
}