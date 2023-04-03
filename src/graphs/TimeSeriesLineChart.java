package graphs;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;

public class TimeSeriesLineChart{
	
	private static final Calendar calendar = Calendar.getInstance();
	
	public static JFreeChart getChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// locationName must be in loadedData as well as all values b/w its start and endDate
		
		// Fix data into a time series object
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
		
		
		// Fix data onto graph
		JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(locationName, "Date", "NHPI", (XYDataset) data);
		return timeSeriesChart;
	}
}