package PractiseDBTesting;

import java.sql.SQLException;

import org.testng.annotations.Test;

public class DataBaseTest extends CommonClass{

	@Test
	public void testDataBase() throws SQLException {
		
		CommonClass cc = new CommonClass();

		cc.customerName();
		cc.cityName();
		cc.countryName();
		cc.postalCode();

	}
	
}
