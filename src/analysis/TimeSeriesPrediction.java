package analysis;

import java.util.Date;
import org.apache.commons.lang3.tuple.MutablePair;

interface TimeSeriesPrediction {
	MutablePair<Double, Date>[] predict(double[] values, Date[] dates);
}
