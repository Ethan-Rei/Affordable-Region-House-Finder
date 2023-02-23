package ui;
import database.*;
import java.sql.*;
import javax.swing.*;
import org.jfree.chart.*;
import java.awt.event.*;

public class Ui {
	
	public static void main(String[] args) {
		JFrame userView = new JFrame();
		
		Database database = Database.getInstance();
		
		ResultSet values = database.query("Toronto, Ontario", "2000-01", "2020-01");
		JFreeChart lineChart = ResultSetTimeSeriesLineChart.getChart("Toronto, Ontario", values);
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		
		userView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { 
            	userView.dispose(); 
            	database.closeConnection();
            }
        });
		
		userView.add(lineChartPanel);
		userView.setSize(400, 400);
		userView.setVisible(true);
	}
}
