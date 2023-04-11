package visuals;

import org.jfree.chart.ChartFactory;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.TableXYDataset;

public class StackedAreaVisualization extends Visualization {
	
	private TableXYDataset newData;
	
	public StackedAreaVisualization(TimeSeriesData timeSeries) {
		super(timeSeries);
		setType(ChartType.STACKED_AREA_CHART);
		
		// bug lies here with adding to stacked area. need a way to change the newdata reference since we can't use
		// dataCollection in Visualization. maybe this class needs to override addTimeSeries and add to newData.
		// or when we add to dataCollection in parent class, we reconvert newData again.
		newData = convertToTableXYDataset(dataCollection);
		chart = ChartFactory.createStackedXYAreaChart(timeSeries.getLocation(), "Date", "NHPI", newData);

		fixToDateAxis();
		createPanel();
	}

	private TableXYDataset convertToTableXYDataset(TimeSeriesCollection seriesCollection) {
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
