package analysis;

import org.apache.commons.math3.stat.inference.TTest;

class ApacheTTest implements TimeSeriesTTest {
	TTest ttest;
	
	ApacheTTest() {
		ttest = new TTest();
	}
	
	@Override
	public double tTest(double[] timeSeries1, double[] timeSeries2) {
		return ttest.tTest(timeSeries1, timeSeries2);
	}
	
}
