package analysis;

import java.sql.*;
import java.util.ArrayList;

public class ResultSetToArray {
	public static double[] getDoubleArray(ResultSet values) {		
		double[] doubleArray = null;
		
		try {
			values.last();
			doubleArray = new double[values.getRow()];
			values.beforeFirst();
			for(int i = 0; values.next(); i++) {
				doubleArray[i] = values.getDouble("property_value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return doubleArray;
	}
}
