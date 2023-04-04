package visuals;

import java.awt.Dimension;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class HistogramVisualization extends Visualization {


	private JFreeChart chart;
	
	private HistogramVisualization(JFreeChart chart) {
		this.chart = chart;
	}
	
	public static HistogramVisualization newChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(data);
		
		JFreeChart plotChart = ChartFactory.createHistogram(locationName, "Date", "NHPI", dataset);
		DateAxis newDateAxis = new DateAxis("Date");
		newDateAxis.setRange(startDate, endDate);
		plotChart.getXYPlot().setDomainAxis(newDateAxis);
        setDateAxis(newDateAxis, getMonthCount(startDate, endDate));
		return new HistogramVisualization(plotChart);
	}

	@Override
	public JFreeChart getChart() {
		return chart;
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
		
		HistogramVisualization chart = newChart("canada", new Date(2000, 1, 1), new Date(2001, 8, 1), loadedData);
		
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
