package PractiseDBTesting;

public class DataBaseData {
	
	public enum Data{
		
		
		CUSTOMERNAME("Atelier graphique"),
		CITYNAME("Nantes"),
		COUNTRYNAME("France"),
		POSTALCODE("44000");
		
		public String call;
		
		public String getcall() {
			return call;
		}
		Data(String call)
		{
			this.call = call;
		}
			
		
	}

}
