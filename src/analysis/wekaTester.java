package analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.tuple.MutablePair;

public class wekaTester {

	public static void main(String args[]) throws ParseException {
		Date[] testDates = new Date[4];
		double[] testNHPIs = new double[4];
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		for (int i = 0; i < 4; i++) {
			testDates[i] = dateFormat.parse("2000-0" + Integer.toString(i+1));
			testNHPIs[i] = i;
		}
		
	
		MutablePair<Double, Date>[] prediction;
		WekaPrediction predictor = new WekaPrediction();
		prediction = predictor.predict(testNHPIs, testDates);
		
		
	}

}
