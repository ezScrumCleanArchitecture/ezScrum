package ntut.csie.ezScrum.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlDatabaseHelper {
	
	private static SqlDatabaseHelper instance = null;
	private Properties properties;
	private String baseDirPath = getBaseDirPath();
	private String configFile = "mysqlDB.ini";
	private String configFilePath = baseDirPath + File.separator + configFile;
	private Connection connection;
	
	private SqlDatabaseHelper() {}
	
	public static synchronized SqlDatabaseHelper getInstance() {
		if(instance == null) {
			instance = new SqlDatabaseHelper();
		}
		return instance;
	}
	
	private String getBaseDirPath() {
		Properties properties = new Properties();
		String pomPropertiesPath = System.getProperty("wtp.deploy") + File.separator
				+ "ezScrum" + File.separator
				+ "META-INF" + File.separator
				+ "maven" + File.separator
				+ "ntut.csie" + File.separator
				+ "ezScrum" + File.separator
				+ "pom.properties";
		try {
			properties.load(new FileInputStream(pomPropertiesPath));
		}catch(FileNotFoundException e) {
			return System.getProperty("user.dir");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty("m2e.projectLocation");
	}
	
	public void connection() {
		properties = new Properties();
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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement getPreparedStatement(String sql) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	public ResultSet getResultSet(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
}