package windows;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import analysis.*;
import visuals.TimeSeriesLineVisualization;
import visuals.Visualization;

import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class PredictionWindow extends InternalFrame {
	private final String title = "Prediction";
	private final JLabel ValuePredictionLabel = new JLabel("Timeseries Prediction");
	private final JLabel algorithmlbl = new JLabel("Choose an algorithm:");
	private final JLabel chartlbl = new JLabel("Charts");
	private final JLabel lblAmountOfMonths = new JLabel("Amount of months");
	private final JRadioButton linearrd = new JRadioButton("Linear Regression");
	private final JRadioButton gaussianrd = new JRadioButton("Gaussian Process");
	private final ButtonGroup algoGrp = new ButtonGroup();
	private final JComboBox<String> monthbx = new JComboBox<String>();
	private final JComboBox<String> chartbx = new JComboBox<String>();
	private final JButton btnPredict = new JButton("Predict");
	private static final Analysis analysis = Analysis.getInstance();
	private final HashMap<String, HashMap<Date, Double>> loadedData;
	private final ArrayList<Visualization> charts;
	private final String errorMsg = "Select a timeseries with atleast 12 months loaded. \nPlease try again.";
	
	public PredictionWindow(HashMap<String, HashMap<Date, Double>> data, ArrayList<Visualization> charts) {
		this.loadedData = data;
		this.charts = charts;
		setInternalWindowSettings(title, 450, 400);
		createFrame();
		frame.setVisible(true);
	}

	public void createFrame() {
		setGUIBounds();
		setChartBoxValues(charts);
		setMonthBoxValues();
		configAlgorithmGroupGUI();
		setGUIListeners();
		addToInternalFrame();
	}
	
	private void setGUIBounds() {
		ValuePredictionLabel.setBounds(158, 21, 140, 16);
		algorithmlbl.setBounds(160, 49, 180, 16);
		linearrd.setBounds(50, 92, 169, 23);
		gaussianrd.setBounds(250, 92, 141, 23);
		chartlbl.setBounds(190, 150, 61, 16);
		chartbx.setBounds(70, 180, 300, 27);
		lblAmountOfMonths.setBounds(165, 235, 180, 16);
		monthbx.setBounds(150, 265, 138, 27);
		btnPredict.setBounds(160, 310, 117, 29);
	}
	
	private void setGUIListeners() {
		btnPredict.addActionListener(e -> predict(loadedData, charts));
	}
	
	private void configAlgorithmGroupGUI() {
		linearrd.setSelected(true);
		algoGrp.add(gaussianrd);
		algoGrp.add(linearrd);
	}
	
	private void addToInternalFrame() {
		frame.getContentPane().add(ValuePredictionLabel);
		frame.getContentPane().add(algorithmlbl);
		frame.getContentPane().add(linearrd);
		frame.getContentPane().add(gaussianrd);
		frame.getContentPane().add(chartlbl);
		frame.getContentPane().add(chartbx);
		frame.getContentPane().add(lblAmountOfMonths);
		frame.getContentPane().add(monthbx);
		frame.getContentPane().add(btnPredict);
	}
	
	private void setMonthBoxValues() {
		for (int i = 2; i <= 12; i++) {
			monthbx.addItem(Integer.toString(i));
		}
		monthbx.setSelectedItem("1");
	}
	
	private void setChartBoxValues(ArrayList<Visualization> charts) {
		int count = 1;
		for (Visualization chart: charts) {
			chartbx.addItem("(" + count + ") " + chart.toString());
			count ++;
		}
	}
	
	public void close() {
		MainWindow.getInstance().getBtnPredict().setEnabled(true);
		MainWindow.getInstance().frame.remove(frame);
	}
	
	private void predict(HashMap<String, HashMap<Date, Double>> loadedData, ArrayList<Visualization> charts) {
		// get user's selection
		int numOfMonths = Integer.parseInt(monthbx.getSelectedItem().toString());
		TimeSeriesLineVisualization chart = (TimeSeriesLineVisualization) charts.get(chartbx.getSelectedIndex());
		Date startDate = chart.getStartDate();
		Date endDate = chart.getEndDate();
		
		// Check if there are atleast 12 months 
		if (WindowHelper.getDatesInRange(startDate, endDate).size() < 12) {
			JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// get desired array of dates and array of corresponding nhpi values
		ArrayList<Date> dates = WindowHelper.getDatesInRange(startDate, endDate);
		ArrayList<Double> nhpis = WindowHelper.getNHPIInRangeArrayList(chart.getLocationName(), startDate, endDate, loadedData);	
		
		// get the predicted values
		double[] predictions;
		if (gaussianrd.isSelected()) {
			predictions = analysis.predict(nhpis, dates, numOfMonths, Analysis.GAUSSIAN_PROCESS);
		}
		else {
			// linearrd is selected
			predictions = analysis.predict(nhpis, dates, numOfMonths, Analysis.LINEAR_REGRESSION);
		}
		
		// Add to chart the predicted points (create a hashmap with our desired values)
		HashMap<String, HashMap<Date, Double>> newValues = new HashMap<>();
		newValues.put(chart.getLocationName(), new HashMap<Date, Double>());
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(endDate);
		
		
		for (int i = 0; i < numOfMonths; i++) {
			calendar.add(Calendar.MONTH, 1);
			newValues.get(chart.getLocationName()).put(calendar.getTime(), predictions[i]);
			
		}
		calendar.setTime(endDate);
		calendar.add(Calendar.MONTH, 1);
		Date newStartDate = calendar.getTime();
		calendar.add(Calendar.MONTH, numOfMonths - 1);
		Date newEndDate = calendar.getTime();
		chart.addTimeSeries(chart.getLocationName(), newStartDate, newEndDate, newValues);
		
		MainWindow.getInstance().refresh();
		try {
			frame.setClosed(true);
			close();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
	}
}
