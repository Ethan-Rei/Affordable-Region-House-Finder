package utilities;

import java.sql.ResultSet;

import model.Database;
import model.MySQLDatabase;

public class Utilities {

	public static void main(String[] args) {
		Database mySQL = new MySQLDatabase();
		
		String myQuery = "SELECT * FROM DATA";
		ResultSet query = mySQL.query(myQuery);
	}
	
	
	
	

}
