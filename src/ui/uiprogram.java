package ui;
import javax.swing.*;
import org.jfree.chart.*;
public class uiprogram {

	public static void main(String[] args) {
		JFrame userView = new JFrame();
		JFreeChart lineChart = TimeSeriesLineChart.getChart("test");
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		userView.add(lineChartPanel);
		userView.setSize(400, 400);
		userView.setVisible(true);
		System.out.println("Here");
	}

}
