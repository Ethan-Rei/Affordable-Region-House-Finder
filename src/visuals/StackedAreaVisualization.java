package visuals;

import org.jfree.chart.ChartFactory;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeTableXYDataset;

public class StackedAreaVisualization extends Visualization {
	
	private TimeTableXYDataset newData;
	
	public StackedAreaVisualization(TimeSeriesData timeSeries) {
		super(timeSeries);
		setType(ChartType.STACKED_AREA_CHART);
		
		newData = new TimeTableXYDataset();
		addToTableXYDataset(dataCollection);
		chart = ChartFactory.createStackedXYAreaChart(timeSeries.getLocation(), "Date", "NHPI", newData);

		fixToDateAxis();
		createPanel();
	}
	
	@Override
	public void addTimeSeries(TimeSeriesData timeSeries) {
		// Load time series into chart
		data = createTimeSeries(timeSeries);
		dataCollection = createCollection(data);
		addToTableXYDataset(dataCollection);
		resizeAxes(timeSeries);
	}

	private void addToTableXYDataset(TimeSeriesCollection seriesCollection) {
	    for (int i = 0; i < seriesCollection.getSeriesCount(); i++) {
	        TimeSeries series = seriesCollection.getSeries(i);
	        for (int j = 0; j < series.getItemCount(); j++) {
	            TimePeriod period = series.getTimePeriod(j);
	            double value = series.getValue(j).doubleValue();
	            newData.add(period, value, series.getKey());
	        }
	    }
	}
}
