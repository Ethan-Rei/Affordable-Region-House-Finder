package visuals;

import java.util.Date;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class PlotGraphVisualization extends Visualization{
	
	public PlotGraphVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		super(locationName, startDate, endDate);
		// Create time series and add to a dataset
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataset = createCollection(data);

		// Create the chart with the dataset and set axes
		this.chart = ChartFactory.createScatterPlot(locationName, "Date", "NHPI", dataset);

		fixToDateAxis();
		super.createPanel(locationName, startDate, endDate, loadedData);
	}

	@Override
	public JFreeChart getChart() {
		return chart;
	}
	
}
