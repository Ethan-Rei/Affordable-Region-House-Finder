package analysis;

import java.sql.Date;

import org.apache.commons.lang3.tuple.MutablePair;

interface TimeSeriesPrediction {
	MutablePair<Double, Date>[] predict(double[] values1, Date[] dates1, double[] values2, Date[] dates2);
}
