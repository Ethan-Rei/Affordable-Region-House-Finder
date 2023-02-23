package ui;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;



// THIS IS CODE FROM THE INTERNET, USE FOR REFERENCE


public class TimeSeriesLineChart{

	public static JFreeChart getChart(String locationName) {
		JFreeChart linechart = ChartFactory.createLineChart(locationName, "Years", "NHPI", createDataset(), PlotOrientation.VERTICAL, true, true, false);
		return linechart;
	}

	private static DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(15, "schools", "1970");
		dataset.addValue(30, "schools", "1980");
		dataset.addValue(60, "schools", "1990");
		dataset.addValue(120, "schools", "2000");
		dataset.addValue(240, "schools", "2010");
		dataset.addValue(300, "schools", "2014");
		return dataset;
	}
}