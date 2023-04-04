package windows;

import java.text.ParseException;
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
	private final JLabel loclbl = new JLabel("Location");
	private final JLabel startlbl = new JLabel("Start Date");
	private final JLabel endlbl = new JLabel("End Date");
	private final JLabel lblAmountOfMonths = new JLabel("Amount of months");
	private final JRadioButton linearrd = new JRadioButton("Linear Regression");
	private final JRadioButton gaussianrd = new JRadioButton("Gaussian Process");
	private final ButtonGroup algoGrp = new ButtonGroup();
	private final JComboBox<String> monthbx = new JComboBox<String>();
	private final JComboBox<String> locbx = new JComboBox<String>();
	private final JComboBox<String> startbx = new JComboBox<String>();
	private final JComboBox<String> endbx = new JComboBox<String>();
	private final JButton btnPredict = new JButton("Predict");
	private static final Analysis analysis = Analysis.getInstance();

	private final String errorMsg = "The selected dates are invalid or you haven't picked a algorithm. \nPlease try again.";
	
	/**
	 * Create the application.
	 */
	public PredictionWindow(HashMap<String, HashMap<Date, Double>> data) {
		createFrame(data);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void createFrame(HashMap<String, HashMap<Date, Double>> loadedData) {
		frame.setSize(450, 400);
		frame.setTitle(title);
		
		ValuePredictionLabel.setBounds(158, 21, 140, 16);
		frame.getContentPane().add(ValuePredictionLabel);
		
		algorithmlbl.setBounds(160, 49, 180, 16);
		frame.getContentPane().add(algorithmlbl);
		
		linearrd.setBounds(50, 92, 169, 23);
		frame.getContentPane().add(linearrd);
		
		gaussianrd.setBounds(250, 92, 141, 23);
		frame.getContentPane().add(gaussianrd);
		
		algoGrp.add(gaussianrd);
		algoGrp.add(linearrd);
		
		loclbl.setBounds(40, 150, 61, 16);
		frame.getContentPane().add(loclbl);
		
		locbx.setBounds(40, 180, 101, 27);
		frame.getContentPane().add(locbx);
		WindowHelper.populateLocBox(locbx, loadedData);
		locbx.addActionListener(e -> WindowHelper.populateDateBoxes(locbx, startbx, endbx, loadedData));
		
		startlbl.setBounds(160, 150, 61, 16);
		frame.getContentPane().add(startlbl);
		
		startbx.setBounds(160, 180, 101, 27);
		frame.getContentPane().add(startbx);
		startbx.addActionListener(e -> WindowHelper.populateEndDate(locbx, startbx, endbx, loadedData));
		
		endlbl.setBounds(280, 150, 61, 16);
		frame.getContentPane().add(endlbl);
		
		endbx.setBounds(280, 180, 101, 27);
		frame.getContentPane().add(endbx);
		
		lblAmountOfMonths.setBounds(165, 235, 180, 16);
		frame.getContentPane().add(lblAmountOfMonths);
		
		monthbx.setBounds(150, 265, 138, 27);
		frame.getContentPane().add(monthbx);
		setMnthBoxValues();
		
		btnPredict.setBounds(160, 310, 117, 29);
		btnPredict.addActionListener(e -> predict(loadedData));
		frame.getContentPane().add(btnPredict);
		
		frame.setVisible(true);
	}
	
	
	private void setMnthBoxValues() {
		for (int i = 2; i <= 12; i++) {
			monthbx.addItem(Integer.toString(i));
		}
		monthbx.setSelectedItem("1");
	}
	
	public void close() {
		MainWindow.getInstance().getBtnPredict().setEnabled(true);
	}
	
	private void predict(HashMap<String, HashMap<Date, Double>> loadedData) {
		if (!checkValidDates()) {
			JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// weka prediction code
		
		// get user's selection
		int numOfMonths = Integer.parseInt(monthbx.getSelectedItem().toString());
		String locationName = locbx.getSelectedItem().toString();
		String startDateStr = startbx.getSelectedItem().toString();
		String endDateStr = endbx.getSelectedItem().toString();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = WindowHelper.dateFormat.parse(startDateStr);
			endDate = WindowHelper.dateFormat.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		// get desired array of dates and array of corresponding nhpi values
		ArrayList<Date> dates = WindowHelper.getDatesInRange(startDate, endDate);
		ArrayList<Double> nhpis = WindowHelper.getNHPIInRangeArrayList(locationName, startDate, endDate, loadedData);	
		
		// get the predicted values
		double[] predictions;
		if (gaussianrd.isSelected()) {
			predictions = analysis.predict(nhpis, dates, numOfMonths, Analysis.GAUSSIAN_PROCESS);
		}
		else {
			// linearrd is selected
			predictions = analysis.predict(nhpis, dates, numOfMonths, Analysis.LINEAR_REGRESSION);
		}
		
		// Create a new visualization graph with the predictions (create a hashmap with our desired values)
		HashMap<String, HashMap<Date, Double>> newValues = new HashMap<>();
		newValues.put(locationName, new HashMap<Date, Double>());
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(endDate);
		
		
		for (int i = 0; i < numOfMonths; i++) {
			calendar.add(Calendar.MONTH, 1);
			newValues.get(locationName).put(calendar.getTime(), predictions[i]);
			
		}
		calendar.setTime(endDate);
		calendar.add(Calendar.MONTH, 1);
		Date newStartDate = calendar.getTime();
		calendar.add(Calendar.MONTH, numOfMonths - 1);
		Date newEndDate = calendar.getTime();
		
		Visualization visualization = new TimeSeriesLineVisualization(locationName, newStartDate, newEndDate, newValues);
		
		
		// Add the graph to the mainwindow
		MainWindow.getInstance().addVisualization(visualization);
		
		
		
	}
	
	private boolean checkValidDates() {
		if (startbx.getSelectedItem() == null || endbx.getSelectedItem() == null)
			return false;
		if (startbx.getSelectedItem().toString().compareTo(endbx.getSelectedItem().toString()) >= 0)
			return false;
		if (!linearrd.isSelected() && !gaussianrd.isSelected())
			return false;
		return true;
	}

	
	
}
