package analysis;

import java.util.Date;

interface TimeSeriesPrediction {
	double[] predict(double[] values, Date[] dates, int predictMonths, int algorithm);
}
