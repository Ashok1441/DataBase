package PractiseDBTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class CommonClass {

	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	
	@BeforeClass
	public void DBConnection() throws SQLException {
		con=DriverManager.getConnection(DataBaseConnectionDetails.data.DATABASEURL.dataBaseDetails,
				DataBaseConnectionDetails.data.USERNAME.dataBaseDetails,
				DataBaseConnectionDetails.data.PASSWORD.dataBaseDetails);
		
		stmt=con.createStatement();
		System.out.println("Enter Customer Number");
		try (Scanner scanner = new Scanner(System.in)) {
			String Id = scanner.next();
			
			rs=stmt.executeQuery(SQLQueries.Queries.SELECT.callQueries+Id);
		}
		rs.next();
		
		
	}
	
	public  void customerName() throws SQLException {
		Assert.assertEquals(rs.getString("customerName"), DataBaseData.Data.CUSTOMERNAME.call );
	}
	
	public  void cityName() throws SQLException {
		Assert.assertEquals(rs.getString("city"), DataBaseData.Data.CITYNAME.call );
	}
	
	public  void countryName() throws SQLException {
		Assert.assertEquals(rs.getString("country"), DataBaseData.Data.COUNTRYNAME.call );
	}
	
	public  void postalCode() throws SQLException {
		Assert.assertEquals(rs.getString("postalCode"), DataBaseData.Data.POSTALCODE.call );
	}
	
	
	@AfterClass
	public void DBconnection() throws SQLException {
		
		con.close();
	}
	
	
}
