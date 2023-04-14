package visuals;

public class VisualizationFactory {
	
	public static Visualization createVisualization(ChartType type, TimeSeriesData timeSeries) {
		if (type == ChartType.LINE_CHART) 
			return new TimeSeriesLineVisualization(timeSeries);
		if (type == ChartType.PLOT_CHART) 
			return new PlotGraphVisualization(timeSeries);
		if (type == ChartType.STACKED_AREA_CHART)
			return new StackedAreaVisualization(timeSeries);
		if (type == ChartType.HISTOGRAM_CHART)
			return new HistogramVisualization(timeSeries);
		return null;
	}
}
