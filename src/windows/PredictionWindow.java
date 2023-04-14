package windows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import visuals.TimeSeriesData;
import visuals.Visualization;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import analysis.Analysis;

import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class PredictionWindow extends InternalFrame {
	private final String title = "Prediction";
	private final JLabel lblValuePrediction = new JLabel("Timeseries Prediction");
	private final JLabel lblAlgorithm = new JLabel("Choose an algorithm:");
	private final JLabel lblChart = new JLabel("Charts");
	private final JLabel lblAmountOfMonths = new JLabel("Amount of months");
	private final JRadioButton radbtnLinearReg = new JRadioButton("Linear Regression");
	private final JRadioButton radbtnGaussianProc = new JRadioButton("Gaussian Process");
	private final ButtonGroup btngrpAlgo = new ButtonGroup();
	private final JComboBox<String> boxMonth = new JComboBox<String>();
	private final JComboBox<String> boxChart = new JComboBox<String>();
	private final JButton btnPredict = new JButton("Predict");
	private final String errorTSMonth = "Select a time series with at least 12 months loaded.\nPlease try again.";
	
	public PredictionWindow() {
		setInternalWindowSettings(title, 450, 400);
		createFrame();
		frame.setVisible(true);
	}

	public void createFrame() {
		setGUIBounds();
		populateLoadedChartsBox(boxChart);
		setMonthBoxValues();
		configAlgorithmGroupGUI();
		setGUIListeners();
		addToInternalFrame();
	}
	
	private void setGUIBounds() {
		lblValuePrediction.setBounds(158, 21, 140, 16);
		lblAlgorithm.setBounds(160, 49, 180, 16);
		radbtnLinearReg.setBounds(50, 92, 169, 23);
		radbtnGaussianProc.setBounds(250, 92, 141, 23);
		lblChart.setBounds(190, 150, 61, 16);
		boxChart.setBounds(70, 180, 300, 27);
		lblAmountOfMonths.setBounds(165, 235, 180, 16);
		boxMonth.setBounds(150, 265, 138, 27);
		btnPredict.setBounds(160, 310, 117, 29);
	}
	
	private void setGUIListeners() {
		btnPredict.addActionListener(e -> predict());
	}
	
	private void configAlgorithmGroupGUI() {
		radbtnLinearReg.setSelected(true);
		btngrpAlgo.add(radbtnGaussianProc);
		btngrpAlgo.add(radbtnLinearReg);
	}
	
	private void addToInternalFrame() {
		frame.getContentPane().add(lblValuePrediction);
		frame.getContentPane().add(lblAlgorithm);
		frame.getContentPane().add(radbtnLinearReg);
		frame.getContentPane().add(radbtnGaussianProc);
		frame.getContentPane().add(lblChart);
		frame.getContentPane().add(boxChart);
		frame.getContentPane().add(lblAmountOfMonths);
		frame.getContentPane().add(boxMonth);
		frame.getContentPane().add(btnPredict);
	}
	
	private void setMonthBoxValues() {
		for (int i = 2; i <= 12; i++) {
			boxMonth.addItem(Integer.toString(i));
		}
		boxMonth.setSelectedItem("1");
	}
	
	private void predict() {
		// get user's selection
		int numOfMonths = Integer.parseInt(boxMonth.getSelectedItem().toString());
		Visualization chart = MainWindow.getInstance().getCharts().get(boxChart.getSelectedIndex());
		
		// Check if there are at least 12 months
		ArrayList<Date> dates = getDatesInRange(chart.getTimeSeries());
		if (dates.size() < 12) {
			JOptionPane.showMessageDialog(null, errorTSMonth, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// get array of corresponding nhpi values
		ArrayList<Double> nhpis = getNHPIInRangeArrayList(chart.getTimeSeries().getStartDateAsDate(), chart.getTimeSeries().getEndDateAsDate(), chart.getTimeSeries().getLoadedData());
		
		// get the predicted values
		TimeSeriesData predictedSeries = new TimeSeriesData(chart.getTimeSeries());
		double[] predictions = getPredictedValues(nhpis, dates, numOfMonths);
		setPredictedDates(predictions, predictedSeries);
		predictedSeries.setLocation(predictedSeries.getLocation() + " (" + chart.getTimeSeries().getEndDate() + " to " + predictedSeries.getEndDate() + ")");
		
		chart.addTimeSeries(predictedSeries);
		MainWindow.getInstance().refresh();
	}
	
	private double[] getPredictedValues(ArrayList<Double> nhpis, ArrayList<Date> dates, int numOfMonths) {
		double[] predictions;
		Analysis analysis = Analysis.getInstance();
		if (radbtnGaussianProc.isSelected()) {
			predictions = analysis.predict(nhpis, dates, numOfMonths, Analysis.GAUSSIAN_PROCESS);
		}
		else {
			// linearrd is selected
			predictions = analysis.predict(nhpis, dates, numOfMonths, Analysis.LINEAR_REGRESSION);
		}
		
		return predictions;
	}
	
	private void setPredictedDates(double[] predictions, TimeSeriesData timeSeries) {
		// Add to data the predicted points
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timeSeries.getEndDateAsDate());
		
		for (int i = 0; i < predictions.length; i++) {
			calendar.add(Calendar.MONTH, 1);
			timeSeries.getLoadedData().put(calendar.getTime(), predictions[i]);
		}

		timeSeries.setEndDate(calendar.getTime());
	}
	
	public void close() {
		MainWindow.getInstance().getBtnPredict().setEnabled(true);
		MainWindow.getInstance().frame.remove(frame);
	}
}
