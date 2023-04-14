package visuals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import org.jfree.data.time.TimeSeriesCollection;

public abstract class Visualization {
	public static final int SMALL = 12;
	public static final int SMALL_MEDIUM = 24;
	public static final int MEDIUM = 48;
	public static final int MEDIUM_LARGE = 96;
	public static final int LARGE = 192;
	public static final Calendar calendar = Calendar.getInstance();
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	protected JPanel panel;
	protected JFreeChart chart;
	protected double min;
	protected double max;
	protected ChartPanel chartPanel;
	protected JScrollPane scrollPaneRaw;
	protected JScrollPane scrollPaneSummary;
	protected TimeSeriesData timeSeries;
	protected TimeSeries data;
	protected TimeSeriesCollection dataCollection;
	protected ChartType type;
	
	protected Visualization (TimeSeriesData timeSeries) {
		this.timeSeries = timeSeries;
		this.panel = new JPanel();
		
		// Fix data into a time series object
		data = createTimeSeries(timeSeries);
		dataCollection = createCollection(data);
	}
	
	public void addTimeSeries(TimeSeriesData timeSeries) {
		// Load time series into chart
		TimeSeries data = createTimeSeries(timeSeries);
		this.dataCollection.addSeries(data);
		
		resizeAxes(timeSeries);
	}
	
	protected void setDateAxis(DateAxis dateAxis) {
		int monthCount = getMonthCount();
		
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

	protected TimeSeriesCollection createCollection(TimeSeries series) {
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(series);
		return collection;
	}
	
	protected TimeSeries createTimeSeries(TimeSeriesData timeSeries) {
		TimeSeries data = new TimeSeries(timeSeries.getLocation(), "Date", "NHPI");
		int month, year;
		double currentNHPI;
		Date currentDate = timeSeries.getStartDateAsDate();
		Date endDate = timeSeries.getEndDateAsDate();
		
		while (currentDate.compareTo(endDate) <= 0) {
			calendar.setTime(currentDate);
			currentNHPI = timeSeries.getLoadedData().get(currentDate);
			month = calendar.get(Calendar.MONTH) + 1;
			year = calendar.get(Calendar.YEAR);
			data.add(new Day(1, month, year), currentNHPI);
			
			// Next Date
			calendar.add(Calendar.MONTH, 1);
			currentDate = calendar.getTime();
		}
		return data;
	}
	
	protected void resizeAxes(TimeSeriesData timeSeries) {
		DateAxis newDateAxis = new DateAxis("Date");
		
		// Find the range of the chart
		double minAdded = chart.getXYPlot().getRangeAxis().getRange().getLowerBound();
        double maxAdded = chart.getXYPlot().getRangeAxis().getRange().getUpperBound();
        this.min = Math.min(minAdded, this.min);
        this.max = Math.max(maxAdded, this.max);
		
        // Set the range for both axes
		newDateAxis.setRange(timeSeries.getStartDateAsDate(), timeSeries.getEndDateAsDate());
		this.chart.getXYPlot().setDomainAxis(newDateAxis);
        setDateAxis(newDateAxis);
        chart.getXYPlot().getRangeAxis().setRange(this.min, this.max);
        
	}

	protected void fixToDateAxis() {
		DateAxis newDateAxis = new DateAxis("Date");
		newDateAxis.setRange(timeSeries.getStartDateAsDate(), timeSeries.getEndDateAsDate());
		chart.getXYPlot().setDomainAxis(newDateAxis);
		setDateAxis(newDateAxis);
	}
	
	protected void createPanel() {
		// Create chart panel
		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(0, 0, 320, 320);
		chartPanel.setVisible(true);
		
		JTable tableRaw = TabularViewFactory.getDataView(timeSeries);
		JTable tableSummary = TabularViewFactory.getStatsView(timeSeries);
		
		// Create scroll pane to scroll the tabular view
		scrollPaneRaw = new JScrollPane(tableRaw);
		scrollPaneRaw.setBounds(0, 320, 320, 230);
		scrollPaneRaw.setVisible(true);
		
		scrollPaneSummary = new JScrollPane(tableSummary);
		scrollPaneSummary.setBounds(0, 320, 320, 230);
		scrollPaneSummary.setVisible(false);
		
		panel.add(scrollPaneRaw);
		panel.add(scrollPaneSummary);
		panel.add(chartPanel);
	}
	
	protected int getMonthCount() {
		calendar.setTime(timeSeries.getEndDateAsDate());
		int endMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(timeSeries.getStartDateAsDate());
		int startMonths = (calendar.get(Calendar.YEAR) * 12) + calendar.get(Calendar.MONTH) + 1;
		return endMonths - startMonths + 1;
	}
	
	public ChartType getType() {
		return type;
	}

	public void setType(ChartType type) {
		this.type = type;
	}
	
	public JFreeChart getChart() {
		return chart;
	}

	public JPanel getPanel() {
		return panel;
	}
	
	public TimeSeriesData getTimeSeries() {
		return timeSeries;
	}

	public void setTimeSeries(TimeSeriesData timeSeries) {
		this.timeSeries = timeSeries;
	}

	public JScrollPane getScrollPaneRaw() {
		return scrollPaneRaw;
	}

	public JScrollPane getScrollPaneSummary() {
		return scrollPaneSummary;
	}
}
