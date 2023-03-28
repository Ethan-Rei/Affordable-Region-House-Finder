package graphs;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class TimeSeriesLineChart{

	public static JFreeChart getChart(String locationName, DefaultCategoryDataset data) {
		JFreeChart linechart = ChartFactory.createLineChart(locationName, "Year", "NHPI", data, PlotOrientation.VERTICAL, true, true, false);
		CategoryLabelPositions positions = CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 5.0);
		linechart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(positions);
		return linechart;
	}
}