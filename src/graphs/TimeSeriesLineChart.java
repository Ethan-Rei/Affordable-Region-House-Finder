package graphs;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;

public class TimeSeriesLineChart{

	public static JFreeChart getChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// locationName must be in loadedData as well as all values b/w its start and endDate
		
		// Fix data into a time series object
		TimeSeries data = new TimeSeries(locationName, "Date", "NHPI");
		
		
		
		// Fix data onto graph
		JFreeChart linechart = ChartFactory.createLineChart(locationName, "Year", "NHPI", data, PlotOrientation.VERTICAL, true, true, false);
		CategoryLabelPositions positions = CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 5.0);
		linechart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(positions);
		return linechart;
	}
}