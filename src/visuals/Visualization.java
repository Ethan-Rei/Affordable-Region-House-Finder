package visuals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;


public abstract class Visualization {
	public static final int SMALL = 12;
	public static final int SMALL_MEDIUM = 24;
	public static final int MEDIUM = 48;
	public static final int MEDIUM_LARGE = 96;
	public static final int LARGE = 192;
	private JPanel panel;
	public static final Calendar calendar = Calendar.getInstance();
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	protected JFreeChart chart;
	protected Date startDate;
	protected Date endDate;
	protected double min;
	protected double max;
	protected String locationName;
	protected ChartPanel chartPanel;
	protected JScrollPane scrollPaneRaw;
	protected JScrollPane scrollPaneSummary;
	
	public abstract JFreeChart getChart();
	
	protected Visualization (String locationName, Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationName = locationName;
	}
	
	protected static int getMonthCount(Date startDate, Date endDate) {
		calendar.setTime(endDate);
		int endMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(startDate);
		int startMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
		return endMonths - startMonths + 1;
	}
	
	protected static void setDateAxis(DateAxis dateAxis, int monthCount) {
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
        	dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 12));
        }
		dateAxis.setDateFormatOverride(dateFormat);
	}
	
	protected static TimeSeries createTimeSeries(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
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

	public JPanel getPanel() {
		return panel;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public String toString() {
		return locationName + " (" + dateFormat.format(startDate) + " to " + dateFormat.format(endDate) + ")";
	}
	
	public JScrollPane getScrollPaneRaw() {
		return scrollPaneRaw;
	}

	public JScrollPane getScrollPaneSummary() {
		return scrollPaneSummary;
	}

	protected void createPanel(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// Create chart panel
		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(0, 0, 320, 320);
		chartPanel.setVisible(true);
		
		JTable tableRaw = TabularViewFactory.getDataView(locationName, startDate, endDate, loadedData);
		JTable tableSummary = TabularViewFactory.getStatsView(locationName, startDate, endDate, loadedData);
		
		// Create scroll pane to scroll the tabular view
		scrollPaneRaw = new JScrollPane(tableRaw);
		scrollPaneRaw.setBounds(0, 320, 320, 230);
		scrollPaneRaw.setVisible(true);
		
		scrollPaneSummary = new JScrollPane(tableSummary);
		scrollPaneSummary.setBounds(0, 320, 320, 230);
		scrollPaneSummary.setVisible(false);
		
		this.panel = new JPanel();
		panel.add(scrollPaneRaw);
		panel.add(scrollPaneSummary);
		panel.add(chartPanel);
	}
}
