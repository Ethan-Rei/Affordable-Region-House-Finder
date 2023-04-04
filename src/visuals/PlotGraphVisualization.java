package visuals;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

public class PlotGraphVisualization extends Visualization{

	private JFreeChart chart;
	
	private PlotGraphVisualization(JFreeChart chart) {
		this.chart = chart;
	}
	
	public static PlotGraphVisualization newChart() {
		JFreeChart newChart = ChartFactory.createScatterPlot(null, null, null, null);
		return new PlotGraphVisualization(newChart);
	}

	@Override
	public JFreeChart getChart() {
		// TODO Auto-generated method stub
		
		return chart;
	}
	
}
