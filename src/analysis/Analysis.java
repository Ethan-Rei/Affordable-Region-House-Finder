package analysis;

import database.DatabaseConnection;
import database.DatabaseQuery;
import database.MySQLConnection;
import database.MySQLQuery;

import java.sql.*;
import org.apache.commons.math3.stat.inference.TTest;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;


public class Analysis
{
	// Better to put monthConverter in a date converter class
	private TTest ttest;
	private static Analysis singleton = null;
	
	private Analysis() {
		ttest = new TTest();
	}
	
	public static Analysis getAnalysisRequests() {
		if (singleton != null) { return new Analysis(); }
		return singleton;
	}
	

	public double tTest(double[] timeSeries1, double[] timeSeries2) {	
		// Returns the p value of the TTest
		return ttest.tTest(timeSeries1, timeSeries2);
	}
	
	public static void main(String[] args) {
		new Analysis();
		DatabaseConnection mysqlconnection = new MySQLConnection();
		DatabaseQuery mysqlquery = new MySQLQuery(mysqlconnection);
		ResultSet torontoSet = mysqlquery.query("Toronto, Ontario", "2000-01", "2020-01");
		ResultSet hamiltonSet = mysqlquery.query("Hamilton, Ontario", "2000-01", "2020-01");
		double ttestResult
		
		System.out.println();
		if ()
	}
}
