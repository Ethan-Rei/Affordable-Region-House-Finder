package database;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
	private static Database singleton;
	private DatabaseConnection connection;
	private DatabaseQuery query;
	
	private Database() {
		connection = new MySQLConnection();
		query = new MySQLQuery(connection);
	}
	
	public static Database getInstance() {
		if (singleton == null) { new Database(); }
		return singleton;
	}
	
	public ResultSet query(String locationName, String fromDate, String toDate) {
		return query.query(locationName, fromDate, toDate);
	}
	
	public void closeConnection() {
		try {
			connection.getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Database database = Database.getInstance();
		ResultSet torontoSet = database.query("Toronto, Ontario", "2000-01", "2020-01");
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
