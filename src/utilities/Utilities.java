package utilities;

import model.Database;
import model.MySQLDatabase;

public class Utilities {

	public static void main(String[] args) {
		Database mySQL = new MySQLDatabase();
		
		String myQuery = "SELECT * FROM HOUSE";
		System.out.println(mySQL.query(myQuery));
	}
	
	
	
	

}
