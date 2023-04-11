package visuals;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;

public class TimeSeriesLineVisualization extends Visualization {
	
	public TimeSeriesLineVisualization(TimeSeriesData timeSeries) {
		super(timeSeries);
		setType(ChartType.LINE_CHART);

		// Fix data onto graph
		chart = ChartFactory.createTimeSeriesChart(timeSeries.getLocation(), "Date", "NHPI", dataCollection);
		
        DateAxis dateAxis = (DateAxis) this.chart.getXYPlot().getDomainAxis();
        setDateAxis(dateAxis);
		
        this.min = this.chart.getXYPlot().getRangeAxis().getRange().getLowerBound();
		this.max = this.chart.getXYPlot().getRangeAxis().getRange().getUpperBound();
		
		createPanel();
	}
}