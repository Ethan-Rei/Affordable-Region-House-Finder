package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import database.DatabaseQuery;
import database.MySQLDatabase;
import database.MySQLQuery;

public class Utilities {

	public static void main(String[] args) throws SQLException {
		DatabaseConnection db = new MySQLDatabase();
		DatabaseQuery query = new MySQLQuery(db.getConnection());
		
		ResultSet result = query.query("SELECT * FROM data;");
		result.next();
		System.out.println(result.getString("location_name"));
	}

}
