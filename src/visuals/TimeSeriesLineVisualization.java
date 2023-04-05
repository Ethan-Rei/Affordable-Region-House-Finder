package visuals;

import java.util.Date;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesLineVisualization extends Visualization{
	
	private TimeSeriesCollection dataCollection;
	
	public TimeSeriesLineVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		super(locationName, startDate, endDate);
		// locationName must be in loadedData as well as all values b/w its start and endDate
		
		// Fix data into a time series object
		int monthCount = getMonthCount(startDate, endDate);
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataCollection = new TimeSeriesCollection();
				
		// Fix data onto graph
		JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(locationName, "Date", "NHPI", dataCollection);
		dataCollection.addSeries(data);
        DateAxis dateAxis = (DateAxis) timeSeriesChart.getXYPlot().getDomainAxis();
        setDateAxis(dateAxis, monthCount);
		
        this.min = timeSeriesChart.getXYPlot().getRangeAxis().getRange().getLowerBound();
		this.max = timeSeriesChart.getXYPlot().getRangeAxis().getRange().getUpperBound();
        this.chart = timeSeriesChart;
		this.dataCollection = dataCollection;
		super.createPanel(locationName, startDate, endDate, loadedData);
		
	}
	
	public void addTimeSeries(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// Load time series into chart
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		DateAxis newDateAxis = new DateAxis("Date");
		this.dataCollection.addSeries(data);
		
		// Find the range of the chart
		double minAdded = chart.getXYPlot().getRangeAxis().getRange().getLowerBound();
        double maxAdded = chart.getXYPlot().getRangeAxis().getRange().getUpperBound();
        this.min = Math.min(minAdded, this.min);
        this.max = Math.max(maxAdded, this.max);
        
        // Find the domain of the chart
		this.startDate = this.startDate.compareTo(startDate) < 0 ? this.startDate : startDate;
		this.endDate = this.endDate.compareTo(endDate) > 0 ? this.endDate : endDate;
		
        // Set the range for both axes
		newDateAxis.setRange(this.startDate, this.endDate);
		this.chart.getXYPlot().setDomainAxis(newDateAxis);
        setDateAxis(newDateAxis, getMonthCount(this.startDate, this.endDate));
        chart.getXYPlot().getRangeAxis().setRange(this.min, this.max);
	}
	
	public JFreeChart getChart() {
		return this.chart;
	}

}