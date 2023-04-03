package graphs;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.util.Calendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesLineChart{
	
	public static final int SMALL = 12;
	public static final int SMALL_MEDIUM = 24;
	public static final int MEDIUM = 48;
	public static final int MEDIUM_LARGE = 96;
	public static final int LARGE = 192;
	
	private static final Calendar calendar = Calendar.getInstance();
	
	private JFreeChart chart;
	private TimeSeriesCollection dataCollection;
	
	private TimeSeriesLineChart(JFreeChart chart, TimeSeriesCollection dataCollection) {
		this.chart = chart;
		this.dataCollection = dataCollection;
	}
	
	public static TimeSeriesLineChart getChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// locationName must be in loadedData as well as all values b/w its start and endDate
		
		// Fix data into a time series object
		int monthCount = getMonthCount(startDate, endDate);
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataCollection = new TimeSeriesCollection();
		
		// Fix data onto graph
		JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(locationName, "Date", "NHPI", dataCollection);
		dataCollection.addSeries(data);
        DateAxis dateAxis = (DateAxis) timeSeriesChart.getXYPlot().getDomainAxis();
        timeSeriesChart.getXYPlot();
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
	
	private static int getMonthCount(Date startDate, Date endDate) {
		calendar.setTime(endDate);
		int endMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(startDate);
		int startMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
		return endMonths - startMonths + 1;
	}
	
	private static TimeSeries createTimeSeries(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		TimeSeries data = new TimeSeries(locationName, "Date", "NHPI");
		int month, year;
		double currentNHPI;
		Date currentDate = startDate;
		
		while (currentDate.compareTo(endDate) <= 0) {
			calendar.setTime(currentDate);
			currentNHPI = loadedData.get(locationName).get(currentDate);
			month = calendar.get(Calendar.MONTH) + 1;
			year = calendar.get(Calendar.YEAR);
			data.add(new Day(1, month, year), currentNHPI);
			
			// Next Date
			calendar.add(Calendar.MONTH, 1);
			currentDate = calendar.getTime();
		}
		return data;
	}
	
	private static void setDateAxis(DateAxis dateAxis, int monthCount) {
		if (monthCount < SMALL) {
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 2));
        }
        else if (monthCount < SMALL_MEDIUM) {
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 4));
        }
        else if (monthCount < MEDIUM) {
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 8));
        }
        else if (monthCount < MEDIUM_LARGE) {
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 2));
        }
        else if (monthCount < LARGE) {
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 4));
        }
        else {
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 6));
        }
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
		
		TimeSeriesLineChart chart = getChart("canada", new Date(2000, 1, 1), new Date(2001, 8, 1), loadedData);
		
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