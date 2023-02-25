package analysis;

import database.Database;
import org.apache.commons.lang3.tuple.MutablePair;
import java.util.Date;
import java.sql.*;

public class Analysis
{
	// Better to put monthConverter in a date converter class
	private TimeSeriesTTest ttest;
	private TimeSeriesPrediction prediction;
	private static Analysis singleton = null;
	
	private Analysis() {
		ttest = new ApacheTTest();
		prediction = new WekaPrediction();
	}
	
	public static Analysis getInstance() {
		if (singleton != null) { return new Analysis(); }
		return singleton;
	}
	

	public MutablePair<Double, Date>[] predict(double[] values, Date[] dates) {	
		return prediction.predict(values, dates);
	}
	
	public double tTest(double[] values1, double[] values2) {	
		return ttest.tTest(values1, values2);
	}
	
	public static void main(String[] args) {
		Analysis analysis = new Analysis();
		Database database = Database.getInstance();
		ResultSet torontoSet = database.query("Toronto, Ontario", "2000-01", "2020-01");
		double[] torontoArr = ResultSetToArray.getDoubleArray(torontoSet);
		ResultSet hamiltonSet = database.query("Hamilton, Ontario", "2000-01", "2020-01");
		double[] hamiltonArr = ResultSetToArray.getDoubleArray(hamiltonSet);
		double ttestResult = analysis.tTest(torontoArr, hamiltonArr);
		
		System.out.println("Comparison between Toronto and Hamilton");
		if (ttestResult < 0.5) {
			System.out.println(String.format("Passed the ttest with p value of %f", ttestResult));
		}
		else { System.out.println(String.format("Failed the ttest with p value of %f", ttestResult)); }
	}
}
