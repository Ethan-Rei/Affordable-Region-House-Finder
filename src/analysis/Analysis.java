package analysis;

import database.Database;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

public class Analysis
{
	public static final int LINEAR_REGRESSION = 0;
	public static final int GAUSSIAN_PROCESS = 1;
	// Better to put monthConverter in a date converter class
	private TimeSeriesTTest ttest;
	private TimeSeriesPrediction prediction;
	private static Analysis singleton = null;
	
	private Analysis() {
		ttest = new ApacheTTest();
		prediction = new WekaPrediction();
	}
	
	public static Analysis getInstance() {
		if (singleton == null) { return new Analysis(); }
		return singleton;
	}
	

	public double[] predict(ArrayList<Double> values, ArrayList<Date> dates, int predictMonths, int algorithm) {	
		return prediction.predict(values, dates, predictMonths, algorithm);
	}
	
	public double tTest(double[] values1, double[] values2) {	
		return ttest.tTest(values1, values2);
	}
}
