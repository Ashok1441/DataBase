package storedFunctions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StoredFunctions {

	Connection con=null;
	Statement stmt=null;
	ResultSet rs;
	ResultSet rs1;
	ResultSet rs2;
	CallableStatement cStmt;
	
	String URL_database="jdbc:mysql://localhost/classicmodels";
	String User_Name="root";
	String Db_Pwd="root";
	
	@BeforeClass
	public void setup() throws SQLException {
		con=DriverManager.getConnection(URL_database,User_Name,Db_Pwd);
	}
	
	@AfterClass
	public void tearDown() throws SQLException {
		con.close();
	}
	
	public boolean compareResultSets(ResultSet resultSetl, ResultSet resultSet2) throws SQLException{
		 while (resultSetl.next()) {
			 		resultSet2.next();
			 		int count = resultSet2.getMetaData().getColumnCount();
			 			for (int i = 1; i <= count; i++) {
			 				 if (!StringUtils.equals(resultSetl.getString(i), resultSet2.getString(i))){
			 					 	return false;
			 				 		}
			 			   }
		 	   }
		 		return true;
	  }
	
	@Test(enabled=false)
	public void test_StoredFUnctionExits() throws SQLException {
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("show function status where name='Customer_Level'");
		rs.next();
		
		System.out.println(rs.getString("name"));
		
		Assert.assertEquals(rs.getString("name"),"Customer_Level" );
		
	}
	
	@Test(enabled=false)
	public void test_Customer_Level_Stored_function() throws SQLException {
		stmt=con.createStatement();
		rs1=stmt.executeQuery("select customerName,Customer_Level(creditLimit) from customers");
		
		stmt=con.createStatement();
		rs2=stmt.executeQuery("select customerName,\r\n"
				+ "CASE\r\n"
				+ "    WHEN creditlimit>50000 THEN 'PLATINUM'\r\n"
				+ "    WHEN creditlimit >= 10000 AND creditlimit<50000 THEN 'GOLD'\r\n"
				+ "    WHEN creditlimit <10000 THEN 'SILVER'\r\n"
				+ "END as customerlevel FROM customers");
		
		Assert.assertEquals(compareResultSets(rs1, rs2), true);
		
	}
	
	@Test
	public void test_Customer_Level_Stored_function_Stored_Procedure() throws SQLException {
		
		cStmt=con.prepareCall("{call Get_Customer_Level(?,?)}");
		cStmt.setInt(1, 131);
		cStmt.registerOutParameter(2, Types.VARCHAR);
		
		cStmt.executeQuery();
		String custLevel = cStmt.getString(2);
		System.out.println(custLevel);
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("select case when creditLimit>50000 then 'PLATINUM' when creditLimit>10000 and creditLimit<50000 then 'GOLD' when creditLimit<10000 then 'SILVER' end as CustomerLevel from customers where customerNumber=131;");
		rs.next();
		String Exp_Cust_level = rs.getString("CustomerLevel");
		
		System.out.println(Exp_Cust_level);
		
		
		Assert.assertEquals(custLevel,Exp_Cust_level );
		
		
	}
	
}
