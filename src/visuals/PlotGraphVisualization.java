package visuals;

import java.util.Date;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class PlotGraphVisualization extends Visualization{

	private JFreeChart chart;
	
	private PlotGraphVisualization(JFreeChart chart) {
		this.chart = chart;
	}
	
	public static PlotGraphVisualization newChart(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		TimeSeries data = createTimeSeries(locationName, startDate, endDate, loadedData);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(data);
		
		JFreeChart plotChart = ChartFactory.createScatterPlot(locationName, "Date", "NHPI", dataset);
		DateAxis dateAxis = (DateAxis) plotChart.getXYPlot().getDomainAxis();
		int monthCount = getMonthCount(startDate, endDate);
        setDateAxis(dateAxis, monthCount);
		return new PlotGraphVisualization(plotChart);
	}

	@Override
	public JFreeChart getChart() {
		return chart;
	}
	
	public static void main(String[] args) {
		
	}
	
}
