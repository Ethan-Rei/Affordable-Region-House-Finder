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
		
        this.chart = timeSeriesChart;
		this.dataCollection = dataCollection;
		super.createPanel(locationName, startDate, endDate, loadedData);
		
	}
	
	// TODO FIX
	public void addTimeSeries(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		double min1 = chart.getXYPlot().getRangeAxis().getRange().getLowerBound();
        double max1 = chart.getXYPlot().getRangeAxis().getRange().getUpperBound();
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		DateAxis newDateAxis = new DateAxis("Date");
		this.dataCollection.addSeries(data);
		this.startDate = this.startDate.compareTo(startDate) < 0 ? this.startDate : startDate;
		this.endDate = this.endDate.compareTo(endDate) > 0 ? this.endDate : endDate;
		newDateAxis.setRange(this.startDate, this.endDate);
		this.chart.getXYPlot().setDomainAxis(newDateAxis);
        setDateAxis(newDateAxis, getMonthCount(this.startDate, this.endDate));
        double min2 = chart.getXYPlot().getRangeAxis().getRange().getLowerBound();
        double max2 = chart.getXYPlot().getRangeAxis().getRange().getUpperBound();
        chart.getXYPlot().getRangeAxis().setRange(min1 < min2 ? min1 : min2, max1 > max2 ? max1 : max2);
	}
	
	public JFreeChart getChart() {
		return this.chart;
	}

}