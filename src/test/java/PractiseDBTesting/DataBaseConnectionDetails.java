package PractiseDBTesting;

public class DataBaseConnectionDetails {

	public enum data {
		
		DATABASEURL("jdbc:mysql://localhost/classicmodels"),
		USERNAME("root"),
		PASSWORD("root");
		
		public String dataBaseDetails;
		
		public String getdataBaseDetails() {
			return dataBaseDetails;
		}
		
		data(String dataBaseDetails){
			this.dataBaseDetails=dataBaseDetails;
		}
	}
}
