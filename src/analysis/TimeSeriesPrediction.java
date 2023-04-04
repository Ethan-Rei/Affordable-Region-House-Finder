package analysis;

import java.util.ArrayList;
import java.util.Date;

interface TimeSeriesPrediction {
	double[] predict(ArrayList<Double> values, ArrayList<Date> dates, int predictMonths, int algorithm);
}
