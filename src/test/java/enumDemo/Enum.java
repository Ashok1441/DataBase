package enumDemo;

public class Enum {

	enum data{
		ASHOK,VINITH,SAMPATH,SATYA,SRINU;
	}
	
	public static void main(String[] args) {
		
		System.out.println(data.ASHOK);
		
		for(data myVar :data.values()) {
			System.out.println(myVar);
		}
	}
}
