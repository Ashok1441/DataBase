package practiseDBT;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


public class PractiseSQLQueries {
	
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static ResultSetMetaData rsmd ;

	@BeforeClass
	public void setup() throws SQLException, Throwable {
		
		
		 con=DriverManager.getConnection("jdbc:mysql://localhost/classicmodels","root","root");
		 		
		
	}
	@Ignore
	@Test(priority = 1)
	public void insertQuery() throws SQLException {
		
		stmt=con.createStatement();
		String query = "insert into demotable(empName,contactNumber)values('shyam',9121314151);";
		stmt.executeUpdate(query);
		
		System.out.println("Insert Query is Sucessfully executed");
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("Select * from demotable where empName='shyam'");
		rs.next();
		System.out.println("Employee Name :"+rs.getString("empName"));
		
		
		
		
	}
	@Ignore
	@Test(priority = 2)
	public void updateQuery() throws SQLException {
		
		stmt=con.createStatement();
		String query = "update demotable set empName='ashok kumar' where empName='shyam'";
		stmt.executeUpdate(query);
		
		System.out.println("Update Query is Sucessfully executed");
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("Select * from demotable where empName='shyam'");
		rs.next();
		System.out.println("Employee Name :"+rs.getString("empName"));
		
	}
	
	@Ignore
	@Test(priority = 3)
	public void deleteQuery() throws SQLException {
		
		stmt=con.createStatement();
		String query ="delete from demotable where empName='shyam'";
		stmt.executeUpdate(query);
		
		System.out.println("Delete Query is Sucessfully executed");
		
	}
	
	@Ignore
	@Test
	public void printallData() throws SQLException {
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("Select * from demotable");
		
		while (rs.next())
		{
			String id = rs.getString("id");
			String employeeName = rs.getString("empName");
			String contactNumber = rs.getString("contactNumber");
			
			System.out.println(id+" "+employeeName+" "+contactNumber);
		}
		
	}
	@Ignore
	@Test
	public void getColumnCount() throws SQLException {
		stmt=con.createStatement();
		rs=stmt.executeQuery("Select * from demotable");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnNumber = rsmd.getColumnCount();
		System.out.println(columnNumber);
		
		while (rs.next()) {
			
			for(int i=1; i<=columnNumber;i++) {
				System.out.print(rs.getString(i)+" ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void getColumnName() throws SQLException, IOException {
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("Select * from demotable");
		rsmd=rs.getMetaData();
		System.out.println("Table Name :"+rsmd.getTableName(1));
		int columnNumber = rsmd.getColumnCount();

			for(int  i=1; i<=columnNumber;i++) {
				System.out.print(rsmd.getColumnName(i)+" ");
				
			}
			System.out.println();
			
			FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\config.properties");
			Properties prop = new Properties();
			prop.load(fis);
			String cs = prop.getProperty("connectionString");
			System.out.println(cs);
//			System.out.println();
	}
	
	@AfterClass
	public void tearDown() throws SQLException {
		
		con.close();
	}
	
	
}
