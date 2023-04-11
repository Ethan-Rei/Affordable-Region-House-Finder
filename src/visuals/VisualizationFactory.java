package visuals;

public class VisualizationFactory {
	public static TimeSeriesLineVisualization createTimeSeriesLineVisualization(TimeSeriesData timeSeries) {
		return new TimeSeriesLineVisualization(timeSeries);
	}
	
	public static PlotGraphVisualization createPlotGraphVisualization(TimeSeriesData timeSeries) {
		return new PlotGraphVisualization(timeSeries);
	}
	
	public static StackedAreaVisualization createStackedAreaVisualization(TimeSeriesData timeSeries) {
		return new StackedAreaVisualization(timeSeries);
	}
	
	public static HistogramVisualization createHistogramVisualization(TimeSeriesData timeSeries) {
		return new HistogramVisualization(timeSeries);
	}
}
