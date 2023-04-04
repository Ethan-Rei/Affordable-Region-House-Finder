package visuals;

import org.jfree.chart.JFreeChart;

public abstract class Visualization {
	public static final int SMALL = 12;
	public static final int SMALL_MEDIUM = 24;
	public static final int MEDIUM = 48;
	public static final int MEDIUM_LARGE = 96;
	public static final int LARGE = 192;
	public abstract JFreeChart getChart();
}
