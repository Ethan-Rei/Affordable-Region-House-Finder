package analysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import weka.core.DenseInstance;
import weka.core.Attribute;
import weka.core.*;
import weka.classifiers.functions.LinearRegression;
import org.apache.commons.lang3.tuple.MutablePair;

class WekaPrediction implements TimeSeriesPrediction{
	
	
	
	public MutablePair<Double, Date>[] predict(double[] values, Date[] dates) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Attribute dateVals = new Attribute("Date", true);
		Attribute nhpiVals = new Attribute("NHPI");
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(dateVals);
		attributes.add(nhpiVals);
		Instances data = new Instances("time series data", attributes, values.length);
		
		System.out.println(data);
		System.out.println(dateVals.isString());
		
		for (int i = 0; i < values.length; i++) {
			String dateString = dateFormat.format(dates[i]);
			System.out.println(dateString);
			Instance dataPoint = new DenseInstance(2);
			
			
			dataPoint.setValue(dateVals, dateString);
			dataPoint.setValue(nhpiVals, values[i]);
			data.add(dataPoint);
		}
		data.setClassIndex(0);
		
		LinearRegression regressionModel = new LinearRegression();
		
		try {
			regressionModel.buildClassifier(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Instance predicationDataSet = data.get(0);
		try {
			double value = regressionModel.classifyInstance(predicationDataSet);
			System.out.println(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null; // To implement
	}
}
