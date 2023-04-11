package windows;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import visuals.TimeSeriesLineVisualization;
import visuals.Visualization;

public class TimeSeriesEditorWindow extends InternalFrame {
	
	private final String title = "Chart Editor";
	private final JButton btnAdd = new JButton("Add time series to chart");
	private final JComboBox<String> boxChart1 = new JComboBox<>();
	private final JComboBox<String> boxChart2 = new JComboBox<>();
	private final JLabel lblModify = new JLabel("Select time series line chart to change:");
	private final JLabel lblFrom = new JLabel("Select time series to add to the chart:");
	
	private final JInternalFrame refPanel;
	private final JButton refButton;
	private final ArrayList<Visualization> charts;
	private final HashMap<String, HashMap<Date, Double>> loadedData;
	
	private final String errorSelection = "Please select both time series.";
	private final String errorDateMismatch = "The time series you are adding to the chart must have the same start and end date.";
	
	public TimeSeriesEditorWindow(JInternalFrame refPanel, JButton refButton, ArrayList<Visualization> charts, HashMap<String, HashMap<Date, Double>> loadedData) {
		this.refPanel = refPanel;
		this.refButton = refButton;
		this.charts = charts;
		this.loadedData = loadedData;
		setInternalWindowSettings(title, 280, 300);
		createFrame();
		frame.setVisible(true);
	}
	
	public void createFrame() {
		getCharts();
		getTimeSeries();
		setGUIBounds();
		setGUIListeners();
		addToInternalFrame();
	}
	
	private void setGUIBounds() {
		lblModify.setBounds(20, 20, 240, 13);
		boxChart1.setBounds(20, 50, 220, 27);
		lblFrom.setBounds(20, 107, 250, 13);
		boxChart2.setBounds(20, 137, 220, 27);
		btnAdd.setBounds(45, 200, 170, 27);
	}
	
	private void setGUIListeners() {
		btnAdd.addActionListener(e -> addTimeSeries());
	}
	
	private void addToInternalFrame() {
		frame.getContentPane().add(lblModify);
		frame.getContentPane().add(boxChart1);
		frame.getContentPane().add(lblFrom);
		frame.getContentPane().add(boxChart2);
		frame.getContentPane().add(btnAdd);
	}

	private TimeSeries getTimeSeriesToAdd() {
		ArrayList<TimeSeries> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		for (TimeSeries ts: timeSeries) {
			if (boxChart2.getSelectedItem().equals(ts.toString())) {
				return ts;
			}
		}
		return null;
	}

	private Visualization getVisualizationToAdd() {
		for (Visualization vs: charts) {
			if (boxChart1.getSelectedItem().equals(vs.toString())) {
				return vs;
			}
		}
		return null;
	}

	// can only add to line charts
	private void addTimeSeries() {
		if (boxesNull())
			return;
		TimeSeries tsToAdd = getTimeSeriesToAdd();
		Visualization chartToEdit = getVisualizationToAdd();
		Date startDate = getTSStartDate(tsToAdd);
		Date endDate = getTSEndDate(tsToAdd);
		if (choicesMismatch(chartToEdit, startDate, endDate))
			return;
		TimeSeriesLineVisualization editedChart = (TimeSeriesLineVisualization) chartToEdit;
		editedChart.addTimeSeries(tsToAdd.getLocation(), startDate, endDate, loadedData);
	}

	private boolean choicesMismatch(Visualization chartToEdit, Date startDate, Date endDate) {
		if (!chartToEdit.getStartDate().equals(startDate) || !chartToEdit.getEndDate().equals(endDate)) {
			JOptionPane.showMessageDialog(null, errorDateMismatch, "Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

	private boolean boxesNull() {
		if (boxChart1.getSelectedItem() == null || boxChart2.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, errorSelection, "Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

	private static Date getTSStartDate(TimeSeries tsToAdd) {
		try {
			return dateFormat.parse(tsToAdd.getStartDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Date getTSEndDate(TimeSeries tsToAdd) {
		try {
			return dateFormat.parse(tsToAdd.getEndDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void getCharts() {
		for (Visualization vs : charts) {
			if (!(vs instanceof TimeSeriesLineVisualization))
				continue;
			boxChart1.addItem(vs.toString());
		}
		
		boxChart1.setSelectedItem(null);
	}
	
	private void getTimeSeries() {
		ArrayList<TimeSeries> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for (TimeSeries ts: timeSeries) {
			boxChart2.addItem(ts.toString());
		}
		
		boxChart2.setSelectedItem(null);
	}
	
	public void close() {
		refPanel.setClosable(true);
		refButton.setEnabled(true);
		MainWindow.getInstance().frame.remove(frame);
		frame.dispose();
	}
	
}
