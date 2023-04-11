package visuals;

import org.jfree.chart.ChartFactory;

public class PlotGraphVisualization extends Visualization{
	
	public PlotGraphVisualization(TimeSeriesData timeSeries) {
		super(timeSeries);
		setType(ChartType.PLOT_CHART);

		// Create the chart with the dataset and set axes
		chart = ChartFactory.createScatterPlot(timeSeries.getLocation(), "Date", "NHPI", dataCollection);

		fixToDateAxis();
		createPanel();
	}
}
