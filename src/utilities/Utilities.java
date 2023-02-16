package utilities;

import java.sql.ResultSet;

import model.Database;
import model.MySQLDatabase;

public class Utilities {

	public static void main(String[] args) {
		Database mySQL = new MySQLDatabase();
		
		mySQL.removeData("data"); // test purposes, remove later
		mySQL.importData("18100205.csv", "data");
	}
	
	
	
	

}
