package visuals;

import java.util.Date;
import java.util.HashMap;

public class VisualizationFactory {
	public static TimeSeriesLineVisualization createTimeSeriesLineVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		return new TimeSeriesLineVisualization(locationName, startDate, endDate, loadedData);
	}
	
	public static PlotGraphVisualization createPlotGraphVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		return new PlotGraphVisualization(locationName, startDate, endDate, loadedData);
	}
	
	public static StackedAreaVisualization createStackedAreaVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		return new StackedAreaVisualization(locationName, startDate, endDate, loadedData);
	}
	
	public static HistogramVisualization createHistogramVisualization(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		return new HistogramVisualization(locationName, startDate, endDate, loadedData);
	}
}
