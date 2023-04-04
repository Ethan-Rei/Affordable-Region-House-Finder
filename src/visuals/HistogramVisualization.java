package visuals;

import java.util.Date;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class HistogramVisualization extends Visualization {
	
	public HistogramVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		super(locationName, startDate, endDate);
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(data);
		
		JFreeChart plotChart = ChartFactory.createHistogram(locationName, "Date", "NHPI", dataset);
		DateAxis newDateAxis = new DateAxis("Date");
		newDateAxis.setRange(startDate, endDate);
		plotChart.getXYPlot().setDomainAxis(newDateAxis);
        setDateAxis(newDateAxis, getMonthCount(startDate, endDate));
		this.chart = plotChart;
		super.createPanel(locationName, startDate, endDate, loadedData);
	}
	
	@Override
	public JFreeChart getChart() {
		return chart;
	}
	
}
