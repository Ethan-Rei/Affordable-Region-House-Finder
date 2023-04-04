package visuals;

import java.awt.Dimension;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.TableXYDataset;

public class StackedAreaVisualization extends Visualization {
	private JFreeChart chart;
	
	public StackedAreaVisualization(JFreeChart chart) {
		this.chart = chart;
	}
	
	public static Visualization newChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(data);
		
		TableXYDataset newData = convertToTableXYDataset(dataset);
		
		JFreeChart stackedAreaChart = ChartFactory.createStackedXYAreaChart(locationName, "Date", "NHPI", newData);
		return new StackedAreaVisualization(stackedAreaChart);
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
		
		Visualization chart = newChart("canada", new Date(2000, 1, 1), new Date(2001, 8, 1), loadedData);
		
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
