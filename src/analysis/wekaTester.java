package analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.tuple.MutablePair;

public class wekaTester {

	public static void main(String args[]) throws ParseException {
		int TEST_SIZE = 40;
		Date[] testDates = new Date[TEST_SIZE];
		double[] testNHPIs = new double[TEST_SIZE];
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		for (int i = 0; i < TEST_SIZE; i++) {
			testDates[i] = dateFormat.parse("2000-0" + Integer.toString(i+1));
			testNHPIs[i] = i;
		}
	
		double[] prediction;
		WekaPrediction predictor = new WekaPrediction();
		prediction = predictor.predict(testNHPIs, testDates, 4, WekaPrediction.LINEAR_REGRESSION);
		
		for (int i = 0; i < prediction.length; i++) {
			System.out.println(prediction[i]);
		}
	}

}
