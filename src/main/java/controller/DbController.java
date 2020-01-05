package controller;

import org.apache.log4j.Logger;

import utils.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class DbController {
	
	static Logger logger = Logger.getLogger(DbController.class);
	 

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/income_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "rootroot1!";

	public static int insertRecordIntoTable(String tun, int bank, Date transactiondate, String transactiondescription, String transactioncomment, double amount, String accountnumber, String transactionnumber) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		int res= 0;
		String insertTableSQL = "INSERT INTO transactionshistory" 
								+ "(TUN, BANK, TRANSACTIONDATE, TRANSACTIONDESCRIPTION, TRANSACTIONCOMMENT, AMOUNT, ACCOUNTNUMBER ,TRANSACTIONNUMBER) VALUES" 
								+ "(?,?,?,?,?,?,?,?)";
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, tun);
			preparedStatement.setInt(2, bank);
			java.sql.Date mysqldate = utils.Utils.dateToSqlDate(transactiondate);
			preparedStatement.setDate(3, mysqldate);
			preparedStatement.setString(4, transactiondescription);
			preparedStatement.setString(5, transactioncomment);
			preparedStatement.setDouble(6, amount);
			preparedStatement.setString(7, accountnumber);
			preparedStatement.setString(8, transactionnumber);
			res = preparedStatement.executeUpdate();
			logger.debug("Record is inserted into transactionshistory table!");
		} catch (SQLException e) {
			logger.error("SQLException occured" + e.getMessage());
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
			return res;
		}
	}
	
	
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


