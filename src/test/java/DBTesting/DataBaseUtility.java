package DBTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataBaseUtility {

	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static ResultSetMetaData rsmd ;
	static PreparedStatement ps;
	static SimpleDateFormat df= new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
	
	public static Connection connectDB() throws SQLException, Throwable {
		
		System.out.println("----------------------"+"Connecting to DataBase"+"\n"+df.format(new Date())+"\n"+"-------------------");
		con=DriverManager.getConnection(DBConfig.getConnectionString(), DBConfig.getUserName(), DBConfig.getPassword());
		System.out.println("Connected to MYSQl DataBase");
		return con;
		
	}
	
	public static void closeDB() throws SQLException {
		con.close();
		System.out.println("----------------------"+"Disconnected to DataBase"+"\n"+df.format(new Date())+"\n"+"-------------------");
		
	}
	
	public static Statement createStatement() throws SQLException {
		
		return con.createStatement();
		
	}
	
	public static void getSelectQuery(String sql_Query) throws SQLException, Throwable {
		System.out.println("---------------------------------showing the Given Query data---------------------------------");
		connectDB();
		rs=createStatement().executeQuery(sql_Query);
		rsmd=rs.getMetaData();
	}
	
	
	
	
}
