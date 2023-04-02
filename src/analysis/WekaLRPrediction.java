package analysis;

import java.util.Date;
import java.util.ArrayList;
import weka.core.DenseInstance;
import weka.core.Attribute;
import weka.core.*;
import java.util.Calendar;
import weka.classifiers.functions.LinearRegression;


class WekaLRPrediction implements TimeSeriesPrediction{
	
	
	@Override
	public double[] predict(double[] values, Date[] dates, int predictMonths) {
		
		// Setup attributes, instances object, and calendar (to get integer values for dates)
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dates[dates.length - 1]);
		int firstMonth = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 2;
		Attribute dateVals = new Attribute("Date");
		Attribute nhpiVals = new Attribute("NHPI");
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(dateVals);
		attributes.add(nhpiVals);
		Instances data = new Instances("time series data", attributes, values.length);
		
		// Turn input data into an instance object and put them into the instances object
		for (int i = 0; i < values.length; i++) {
			Instance dataPoint = new DenseInstance(2);
			calendar.setTime(dates[i]);
			int numOfMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
			
			dataPoint.setValue(0, numOfMonths);
			dataPoint.setValue(1, values[i]);
			System.out.println(numOfMonths);
			System.out.println(values[i]);
			data.add(dataPoint);
		}
		
		// Set the object to predicted to the nhpi
		data.setClassIndex(1);
		
		// Create a regression model and train it on the new data
		LinearRegression regressionModel = new LinearRegression();	
		try {
			regressionModel.buildClassifier(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Predict the desired amount of months
		double[] predictionArray = new double[predictMonths];
		for (int i = 0; i < predictMonths; i++) {
			Instance newPoint = new DenseInstance(2);
			int numOfMonths = firstMonth + i;
			newPoint.setValue(0, numOfMonths);
			newPoint.setMissing(1);
			try {
				predictionArray[i] = regressionModel.classifyInstance(newPoint);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return predictionArray;
	}

}
