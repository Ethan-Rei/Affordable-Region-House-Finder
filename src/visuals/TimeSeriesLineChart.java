package visuals;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFrame;

import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesLineChart extends Visualization{
	
	private JFreeChart chart;
	private TimeSeriesCollection dataCollection;
	
	private TimeSeriesLineChart(JFreeChart chart, TimeSeriesCollection dataCollection) {
		this.chart = chart;
		this.dataCollection = dataCollection;
	}
	
	public static TimeSeriesLineChart newChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
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
        
		return new TimeSeriesLineChart(timeSeriesChart, dataCollection);
	}
	
	// could be bugged
	public void addTimeSeries(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		this.dataCollection.addSeries(data);
	}
	
	public JFreeChart getChart() {
		return this.chart;
	}

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		HashMap<String, HashMap<Date, Double>> loadedData = new HashMap<String, HashMap<Date, Double>>();
		loadedData.put("canada", new HashMap<Date, Double>());
		loadedData.get("canada").put(new Date(2000, 1, 1), 0.0);
		loadedData.get("canada").put(new Date(2000, 2, 1), 1.0);
		loadedData.get("canada").put(new Date(2000, 3, 1), 2.0);
		loadedData.get("canada").put(new Date(2000, 4, 1), 3.0);
		loadedData.get("canada").put(new Date(2000, 5, 1), 4.0);
		loadedData.get("canada").put(new Date(2000, 6, 1), 0.0);
		loadedData.get("canada").put(new Date(2000, 7, 1), 1.0);
		loadedData.get("canada").put(new Date(2000, 8, 1), 2.0);
		loadedData.get("canada").put(new Date(2000, 9, 1), 3.0);
		loadedData.get("canada").put(new Date(2000, 10, 1), 4.0);
		loadedData.get("canada").put(new Date(2000, 11, 1), 0.0);
		loadedData.get("canada").put(new Date(2001, 0, 1), 1.0);
		loadedData.get("canada").put(new Date(2001, 1, 1), 2.0);
		loadedData.get("canada").put(new Date(2001, 2, 1), 3.0);
		loadedData.get("canada").put(new Date(2001, 3, 1), 4.0);
		loadedData.get("canada").put(new Date(2001, 4, 1), 0.0);
		loadedData.get("canada").put(new Date(2001, 5, 1), 1.0);
		loadedData.get("canada").put(new Date(2001, 6, 1), 2.0);
		loadedData.get("canada").put(new Date(2001, 7, 1), 3.0);
		loadedData.get("canada").put(new Date(2001, 8, 1), 4.0);
		
		TimeSeriesLineChart chart = newChart("canada", new Date(2000, 1, 1), new Date(2001, 8, 1), loadedData);
		
		ChartPanel chartPanel = new ChartPanel(chart.getChart());
        chartPanel.setPreferredSize(new Dimension(600, 500));

        // create a frame and add the chart panel to it
        JFrame frame = new JFrame("Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
		
	}

}