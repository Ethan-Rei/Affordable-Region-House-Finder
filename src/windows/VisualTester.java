package windows;

import javax.swing.JFrame;

public class VisualTester {

	public static void main(String[] args) {
		String locationName = "Toronto, Ontario";
		String startDate = "2019-01";
		String endDate = "2020-01";
		String type1 = "Area";
		String type2 = "Bar";
		JFrame chart1 = MainWindow.frameChart(locationName, startDate, endDate, type1);
		JFrame chart2 = MainWindow.frameChart(locationName, startDate, endDate, type2);
	}
}
