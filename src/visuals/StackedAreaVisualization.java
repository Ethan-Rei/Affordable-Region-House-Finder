package visuals;

import java.util.Date;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.TableXYDataset;

public class StackedAreaVisualization extends Visualization {
	
	public StackedAreaVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		super(locationName, startDate, endDate);
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataset = createCollection(data);
		
		TableXYDataset newData = convertToTableXYDataset(dataset);
		this.chart = ChartFactory.createStackedXYAreaChart(locationName, "Date", "NHPI", newData);

		fixToDateAxis();
		super.createPanel(locationName, startDate, endDate, loadedData);
	}

	@Override
	public JFreeChart getChart() {
		return chart;
	}
	
	private static TableXYDataset convertToTableXYDataset(TimeSeriesCollection seriesCollection) {
	    TimeTableXYDataset dataset = new TimeTableXYDataset();
	    for (int i = 0; i < seriesCollection.getSeriesCount(); i++) {
	        TimeSeries series = seriesCollection.getSeries(i);
	        for (int j = 0; j < series.getItemCount(); j++) {
	            TimePeriod period = series.getTimePeriod(j);
	            double value = series.getValue(j).doubleValue();
	            dataset.add(period, value, series.getKey());
	        }
	    }
	    return dataset;
	}
}
