package windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import database.Database;

public class MainWindow implements WindowFrame, Visualization {

	@Override
	public void createWindow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyWindow() {
		// TODO Auto-generated method stub
		
	}

	
	private static JFreeChart createChart(String locationName, ResultSet values, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			while (values.next()) {
				dataset.addValue(values.getDouble("property_value"), values.getString("location_name") , values.getString("refdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFreeChart chart;
		switch (type) {
			case "Area":
				chart = ChartFactory.createAreaChart(locationName, "Year", "NHPI", dataset, PlotOrientation.VERTICAL, true, true, false);
				break;
			case "Bar":
				chart = ChartFactory.createBarChart(locationName, "Year", "NHPI", dataset, PlotOrientation.VERTICAL, true, true, false);
				break;
			case "Line":
				chart = ChartFactory.createLineChart(locationName, "Year", "NHPI", dataset, PlotOrientation.VERTICAL, true, true, false);
				break;
			case "Waterfall":
				chart = ChartFactory.createWaterfallChart(locationName, "Year", "NHPI", dataset, PlotOrientation.VERTICAL, true, true, false);
				break;
			default:
				chart = ChartFactory.createLineChart("Empty", null, null, null);
				
		}
		
		CategoryLabelPositions positions = CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 5.0);
		chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(positions);
		return chart;
	}

	
	public static JFrame frameChart(String locationName, String startDate, String endDate, String type) {
		// TODO Auto-generated method stub
		JFrame userView = new JFrame();
		Database database = Database.getInstance();
		ResultSet values = database.query(locationName, startDate, endDate);
		userView.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				userView.dispose();
				database.closeConnection();
			}
		});
		
		JFreeChart chart = createChart(locationName + " from " + startDate + " to " + endDate, values, type);
		ChartPanel chartPanel = new ChartPanel(chart);
		userView.add(chartPanel);
			
		userView.setSize(400, 400);
		userView.setVisible(true);
		return userView;
	}
	
	/*
	//If using the netbeans buttons then maybe this
	private void visualization(java.awt.event.ActionEvent evt) { 
		String locationName1 = txt_locationName1.getText(); 
		String locationName2 = txt_locationName2.getText(); 
		String startDate = txt_startDate.getText();
		String endDate = txt_endDate.getText(); 
		String type1 = txt_type1.getText();
		String type2 = txt_type2.getText();
	  
	  JFrame chart1 = frameChart(locationName1, startDate, endDate, type1); 
	  JFrame chart2 = frameChart(locationName1, startDate, endDate, type2);
	  JFrame chart3 = frameChart(locationName2, startDate, endDate, type1);
	  JFrame chart4 = frameChart(locationName2, startDate, endDate, type2);
	}
	*/
	

}
