package DBTesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
	
	static File file = new File(".\\src\\test\\resources\\config.properties");
	static FileInputStream fis;
	static Properties prop = new Properties();;
	
	
	
	public static String getConfigData(String key) throws IOException {
		
	
		 fis = new FileInputStream(file);
		 prop.load(fis);
		String value = prop.getProperty(key);
		fis.close();
		return value;
	}
	
	public static String getConnectionString() throws Throwable {
		
		fis = new FileInputStream(file);
		prop.load(fis);
		return prop.getProperty("connectionString");
	}
	
	public static String getUserName() throws Throwable {
		
		 fis = new FileInputStream(file);
		 prop.load(fis);
		return prop.getProperty("userName");
	}
	
	public static String getPassword() throws Throwable {
		
		fis = new FileInputStream(file);
		 prop.load(fis);
		 return prop.getProperty("password");
		 
	}
	
	
	
	

}
