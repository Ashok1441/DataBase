package storeProcedureTesting;

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



public class SPTesting {
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rs;
	CallableStatement cStmt;
	ResultSet rs1;
	ResultSet rs2;
	
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
	
	@Test
	public void test_StoreProcedureExists() throws SQLException {
		stmt=con.createStatement();
		rs=stmt.executeQuery("show procedure status where name='SelectAllCustomers'");
		rs.next();
//		System.out.println(rs.next());
		
		Assert.assertEquals(rs.getString("Name"), "SelectAllCustomers");
	}
	
	@Test
	public void test_SelectAllCustomers() throws SQLException {
		cStmt=con.prepareCall("{call SelectAllCustomers}");
		rs1=cStmt.executeQuery();
		
		stmt=con.createStatement();
		rs2=stmt.executeQuery("Select * from customers");
		
		Assert.assertEquals(compareResultSets(rs1, rs2),true);
		
	}
 
	@Test
	public void test_SelectAllCustomersByCity() throws SQLException {
		cStmt=con.prepareCall("{call SelectAllCustomersByCity(?)}");
		cStmt.setString(1, "Singapore");
		rs1=cStmt.executeQuery();
		
		stmt=con.createStatement();
		rs2=stmt.executeQuery("Select * from customers where city='Singapore'");
		
		Assert.assertEquals(compareResultSets(rs1, rs2),true);
		
	}
	@Test
	public void test_SelectAllCustomersByCityAndPin() throws SQLException {
		cStmt=con.prepareCall("{call SelectAllCustomersByCityAndPin(?,?)}");
		cStmt.setString(1, "Singapore");
		cStmt.setString(2, "079903");
		rs1=cStmt.executeQuery();
		
		stmt=con.createStatement();
		rs2=stmt.executeQuery("Select * from customers where city='Singapore' and postalCode='079903'");
		
		Assert.assertEquals(compareResultSets(rs1, rs2),true);
		
	}
	@Test
	public void test_get_order_by_customer_id() throws SQLException {
		cStmt=con.prepareCall("{call  get_order_Status_by_customer_id(?,?,?,?,?)}");
		cStmt.setInt(1, 141);
		
		cStmt.registerOutParameter(2, Types.INTEGER);
		cStmt.registerOutParameter(3, Types.INTEGER);
		cStmt.registerOutParameter(4, Types.INTEGER);
		cStmt.registerOutParameter(5, Types.INTEGER);
		
		cStmt.executeQuery();
		
		int shipped = cStmt.getInt(2);
		int cancelled = cStmt.getInt(3);
		int resolved = cStmt.getInt(4);
		int disputed = cStmt.getInt(5);
		
		System.out.println(shipped+" "+cancelled+" "+resolved+" "+disputed);
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("select (SELECT count(*) FROM orders WHERE customerNumber = 141 AND status = 'Shipped') as Shipped,"
				+ "(SELECT count(*) FROM orders WHERE customerNumber = 141 AND status = 'Cancelled') as Cancelled,"
				+ "(SELECT count(*) FROM orders WHERE customerNumber = 141 AND status = 'Resolved') as Resolved,"
				+ "(SELECT count(*) FROM Orders WHERE customerNumber = 141 AND status = 'Disputed') as Disputed");
		
		rs.next();
		int exp_Shipped = rs.getInt("Shipped");
		int exp_Cancelled=rs.getInt("Cancelled");
		int exp_Resolved=rs.getInt("Resolved");
		int exp_Disputed=rs.getInt("Disputed");
				
		System.out.println(exp_Shipped+" "+exp_Cancelled+" "+exp_Resolved+" "+exp_Disputed);
				if(exp_Shipped==shipped && exp_Cancelled==cancelled && exp_Resolved==resolved && exp_Disputed==disputed) {
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
	}
	@Test
	public void test_Get_Customer_ShippingDays() throws SQLException {
		cStmt=con.prepareCall("{call Get_Customer_Shipping(?,?)}");
		cStmt.setInt(1, 112);
		
		cStmt.registerOutParameter(2, Types.VARCHAR);
		
		cStmt.executeQuery();
		
		String shippingTime = cStmt.getString(2);
		
		System.out.println(shippingTime);
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("SELECT country,\r\n"
				+ "CASE\r\n"
				+ "WHEN country='USA' THEN '2-days Shipping'\r\n"
				+ "WHEN country='Canada' THEN '3-days Shipping'\r\n"
				+ "ELSE '5-days Shipping'\r\n"
				+ "END as ShippingTime\r\n"
				+ " FROM customers WHERE customerNumber=112;\r\n"
				+ ""
				);
		
		rs.next();
		String exp_ShippingTime = rs.getString("ShippingTime");
		System.out.println(exp_ShippingTime);
		
		Assert.assertEquals(shippingTime,exp_ShippingTime);
	}
}
