package analysis;

import java.sql.*;
import java.util.ArrayList;

public class ResultSetToArray {
	public static double[] getDoubleArray(ResultSet values) {
		ArrayList<Double> doubleArrayList = new ArrayList<Double>();
		double[] doubleArray;
		try {
			while (values.next()) {
				doubleArrayList.add(values.getDouble("property_value"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		doubleArray = new double[doubleArrayList.size()];
		for (int i = 0; i < doubleArrayList.size(); i++) {
			doubleArray[i] = doubleArrayList.get(i);
		}
		return doubleArray;
	}
}
