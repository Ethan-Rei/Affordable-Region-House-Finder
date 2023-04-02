package analysis;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import weka.core.*;
import java.util.Calendar;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.timeseries.WekaForecaster;


class WekaPrediction implements TimeSeriesPrediction{
	
	public static final int LINEAR_REGRESSION = 0;
	public static final int GAUSSIAN_PROCESS = 1;
	
	@Override
	public double[] predict(double[] values, Date[] dates, int predictMonths, int algorithm) {
		
		// Setup the data set into attributes and instances
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dates[dates.length - 1]);
		Attribute dateVals = new Attribute("Date");
		Attribute nhpiVals = new Attribute("NHPI");
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(dateVals);
		attributes.add(nhpiVals);
		Instances data = new Instances("time series data", attributes, values.length);
		
		for (int i = 0; i < values.length; i++) {
			Instance dataPoint = new DenseInstance(2);
			calendar.setTime(dates[i]);
			int numOfMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
			dataPoint.setValue(0, numOfMonths);
			dataPoint.setValue(1, values[i]);
			data.add(dataPoint);
		}
		
		data.setClassIndex(1);
		
		// Setup the forecaster on given data
		
		WekaForecaster forecastModel = new WekaForecaster();
		
		switch (algorithm) {
		case LINEAR_REGRESSION:
			forecastModel.setBaseForecaster(new LinearRegression());
			break;
		case GAUSSIAN_PROCESS:
			forecastModel.setBaseForecaster(new GaussianProcesses());
			break;
		}
		
		
		forecastModel.getTSLagMaker().setTimeStampField("Date");
		forecastModel.getTSLagMaker().setMinLag(12);
		forecastModel.getTSLagMaker().setMaxLag(24);
		try {
			forecastModel.setFieldsToForecast("NHPI");
			forecastModel.buildForecaster(data);
			forecastModel.primeForecaster(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Get predicted values and return
		
		double[] predictionArray = new double[predictMonths];
		List<List<NumericPrediction>> predictedValues = null;
		try {
			predictedValues = forecastModel.forecast(predictMonths, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < predictedValues.size(); i++) {
			predictionArray[i] = predictedValues.get(i).get(0).predicted();
		}

		return predictionArray;
	}

}