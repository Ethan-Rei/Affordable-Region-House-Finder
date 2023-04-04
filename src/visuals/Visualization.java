package visuals;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import windows.WindowHelper;

public abstract class Visualization {
	public static final int SMALL = 12;
	public static final int SMALL_MEDIUM = 24;
	public static final int MEDIUM = 48;
	public static final int MEDIUM_LARGE = 96;
	public static final int LARGE = 192;
	public static final Calendar calendar = Calendar.getInstance();
	public abstract JFreeChart getChart();
	
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
		dateAxis.setDateFormatOverride(WindowHelper.dateFormat);
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
}
