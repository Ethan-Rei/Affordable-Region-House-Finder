package analysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import weka.classifiers.timeseries.HoltWinters;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class WekaTSFPrediction implements TimeSeriesPrediction {

	@Override
	public double[] predict(double[] values, Date[] dates, int predictMonths) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dates[dates.length - 1]);
		int firstMonth = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 2;
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
			System.out.println(numOfMonths);
			System.out.println(values[i]);
			data.add(dataPoint);
		}
		
		data.setClassIndex(1);
		
		WekaForecaster hwModel = new WekaForecaster();
		hwModel.setBaseForecaster(new HoltWinters());
		hwModel.setFieldsToForecast("NHPI");
		hwModel.getTSLagMaker().setTimeStampField("Date"); // date time stamp
		hwModel.getTSLagMaker().setMinLag(12);
		hwModel.getTSLagMaker().setMaxLag(24); // monthly data
		try {
			hwModel.buildForecaster(data);
			hwModel.primeForecaster(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		double[] predictionArray = new double[predictMonths];
		for (int i = 0; i < predictMonths; i++) {
			Instance newPoint = new DenseInstance(2);
			int numOfMonths = firstMonth + i;
			newPoint.setValue(0, numOfMonths);
			newPoint.setMissing(1);
			try {
				predictionArray[i] = hwModel.forecast();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return predictionArray;
	}

}
