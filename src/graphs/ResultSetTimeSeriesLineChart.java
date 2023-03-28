package graphs;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.*;
import java.sql.*;

public class ResultSetTimeSeriesLineChart{

	public static JFreeChart getChart(String locationName, ResultSet values) {
		DefaultCategoryDataset data = createDatasetFromResultSet(values);
		JFreeChart linechart = TimeSeriesLineChart.getChart(locationName, data);
		return linechart;
	}

	private static DefaultCategoryDataset createDatasetFromResultSet(ResultSet values) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			while (values.next()) {
				dataset.addValue(values.getDouble("property_value"), values.getString("location_name") , values.getString("refdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataset;
	}

}
