package visuals;

import org.jfree.chart.ChartFactory;

public class HistogramVisualization extends Visualization {
	
	public HistogramVisualization(TimeSeriesData timeSeries) {
		super(timeSeries);
		setType(ChartType.HISTOGRAM_CHART);

		// Create the chart with the dataset and set axes
		chart = ChartFactory.createHistogram(timeSeries.getLocation(), "Date", "NHPI", dataCollection);

		fixToDateAxis();
		createPanel();
	}
}
