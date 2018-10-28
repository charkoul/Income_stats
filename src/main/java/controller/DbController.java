package controller;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbController {
	
	static Logger logger = Logger.getLogger(DbController.class);
	 

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/income_db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

//	public static void insertRecordIntoTable() throws SQLException {
//		Connection dbConnection = null;
//		PreparedStatement preparedStatement = null;
//		String insertTableSQL = "INSERT INTO test" + "(id, name) VALUES" + "(?,?)";
//		try {
//			dbConnection = getDBConnection();
//			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
//			preparedStatement.setInt(1, 11);
//			preparedStatement.setString(2, "mkyong");
//			preparedStatement.executeUpdate();
//			logger.info("Record is inserted into DBUSER table!");
//		} catch (SQLException e) {
//			logger.info(e.getMessage());
//		} finally {
//			if (preparedStatement != null) {
//				preparedStatement.close();
//			}
//			if (dbConnection != null) {
//				dbConnection.close();
//			}
//		}
//	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.info(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return dbConnection;
	}

}


