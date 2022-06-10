package PractiseDBTesting;

public class SQLQueries {

	public enum Queries{
		
		SELECT("SELECT * FROM customers where customerNumber=");
		
public String callQueries;
		
		public String getcallQueries() {
			return callQueries;
		}
		Queries(String callQueries)
		{
			this.callQueries = callQueries;
		}
	}
}
