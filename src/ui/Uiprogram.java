package ui;
import database.*;
import java.sql.*;
import javax.swing.*;
import org.jfree.chart.*;

public class Uiprogram {
	
	public static void main(String[] args) {
		JFrame userView = new JFrame();
		
		DatabaseConnection mysqlconnection = new MySQLDatabase();
		DatabaseQuery mysqlquery = new MySQLQuery(mysqlconnection);
		
		ResultSet values = mysqlquery.query("select refdate, location_name, property_value from data where location_name='Toronto, Ontario'");
		JFreeChart lineChart = ResultSetTimeSeriesLineChart.getChart("Toronto, Ontario", values);
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		
		
		userView.add(lineChartPanel);
		userView.setSize(400, 400);
		userView.setVisible(true);
	}
}
