package database;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
	private static Database singleton;
	
	public static void main(String[] args) {
		DatabaseConnection mysqlconnection = new MySQLConnection();
		DatabaseQuery mysqlquery = new MySQLQuery(mysqlconnection);
		ResultSet torontoSet = mysqlquery.query("Toronto, Ontario", "2000-01", "2020-01");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
		Date date;
		String locationName, currentDate;
        double propertyValue;
		try {
			while (torontoSet.next()) {
				locationName = torontoSet.getString("location_name");
				date = torontoSet.getDate("refdate");
				propertyValue = torontoSet.getDouble("property_value");
				currentDate = formatter.format(date);
				System.out.println(String.format("%s %s %s", locationName, propertyValue, currentDate));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
