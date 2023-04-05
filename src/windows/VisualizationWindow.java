package windows;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import visuals.ChartType;
import visuals.HistogramVisualization;
import visuals.PlotGraphVisualization;
import visuals.StackedAreaVisualization;
import visuals.TimeSeriesLineVisualization;
import visuals.Visualization;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.Component;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;

public class VisualizationWindow extends InternalFrame {
	private final String title = "Visualization Options";
	private final JLabel lblVisuals = new JLabel("Select Visualizations:");
	private final JLabel lblSelect = new JLabel("Select a specific time series to edit its chart visualizations:");
	private final JCheckBox checkLine = new JCheckBox("Line Chart");
	private final JCheckBox checkPlot = new JCheckBox("Plot Graph");
	private final JCheckBox checkHisto = new JCheckBox("Histogram");
	private final JCheckBox checkStack = new JCheckBox("Stacked Area");
	private final JComboBox<String> boxTimeSeries = new JComboBox<>();
	private final JButton btnUpdate = new JButton("Update Visualization");
	private final JButton btnEdit = new JButton("Edit time series charts");
	private final ArrayList<Visualization> charts;
	private final HashMap<String, HashMap<Date, Double>> loadedData;
	
	private final String errorSelection = "Please select a time series chart to edit.";
	private final String errorCount = "You have more than 3 charts selected. Please unselect some.";

	/**
	 * Create the application.
	 */
	public VisualizationWindow(HashMap<String, HashMap<Date, Double>> loadedData, ArrayList<Visualization> charts) {
		this.loadedData = loadedData;
		this.charts = charts;
		getTimeSeries();
		createFrame();
	}

	private void createFrame() {
		frame.setSize(415, 280);
		frame.setTitle(title);
		
		lblVisuals.setBounds(16, 24, 149, 16);
		frame.getContentPane().add(lblVisuals);
		
		checkLine.setBounds(16, 53, 83, 23);
		frame.getContentPane().add(checkLine);
		
		checkPlot.setBounds(105, 53, 88, 23);
		frame.getContentPane().add(checkPlot);
		
		checkHisto.setBounds(195, 53, 88, 23);
		frame.getContentPane().add(checkHisto);
		
		checkStack.setBounds(285, 53, 110, 23);
		frame.getContentPane().add(checkStack);
		
		lblSelect.setBounds(16, 93, 350, 16);
		frame.getContentPane().add(lblSelect);
		
		boxTimeSeries.setBounds(16, 127, 370, 27);
		boxTimeSeries.addActionListener(e -> loadTimeSeriesSettings());
		frame.getContentPane().add(boxTimeSeries);
		
		btnUpdate.setBounds(16, 183, 177, 37);
		btnUpdate.addActionListener(e -> updateVisualizations());
		frame.getContentPane().add(btnUpdate);
		
		btnEdit.setBounds(215, 183, 170, 37);
		btnEdit.addActionListener(e -> openInternalWindow(new TimeSeriesEditorWindow(boxTimeSeries, btnEdit)));
		frame.getContentPane().add(btnEdit);
		frame.setVisible(true);
	}
	
	private void getTimeSeries() {
		ArrayList<TimeSeries> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for (TimeSeries ts: timeSeries) {
			boxTimeSeries.addItem(ts.toString());
		}
		
		boxTimeSeries.setSelectedItem(null);
	}
	
	private void loadTimeSeriesSettings() {
		ArrayList<TimeSeries> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for(TimeSeries ts: timeSeries) {
			if (ts.toString().equals(boxTimeSeries.getSelectedItem().toString())) {
				checkLine.setSelected(ts.getSetting(ChartType.LINE_CHART));
				checkPlot.setSelected(ts.getSetting(ChartType.PLOT_CHART));
				checkHisto.setSelected(ts.getSetting(ChartType.HISTOGRAM_CHART));
				checkStack.setSelected(ts.getSetting(ChartType.STACKED_AREA_CHART));
				break;
			}
		}
	}
	
	private void updateVisualizations() {
		if (boxTimeSeries.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, errorSelection, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// check through all settings, throw error if they already had 3 selected, can be own method
		int counter = 0;
		ArrayList<TimeSeries> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		// this can be optimized to O(1) or O(n) if every new visualization chart contained a TimeSeries and ChartType?
		// look for the same time series 
		for (Visualization vis: charts) {
			for (TimeSeries ts: timeSeries) {
				if(ts.toString().equals(boxTimeSeries.getSelectedItem().toString())) {
					// don't count the currently selected
					continue;
				}
					
				if(vis.toString().equals(ts.toString())) {
					counter += ts.getSettingsCount();
				}
			}
		}
		
		// count the boxes selected and see if greater than counter, throw error if so
		if (checkLine.isSelected())
			counter++;
		if (checkPlot.isSelected())
			counter++;
		if (checkHisto.isSelected())
			counter++;
		if (checkStack.isSelected())
			counter++;
		System.out.println(counter);
		if (counter > 3) {
			JOptionPane.showMessageDialog(null, errorCount, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		TimeSeries curSelect = null;
		for (TimeSeries ts: timeSeries) {
			if (ts.toString().equals(boxTimeSeries.getSelectedItem().toString()))
				curSelect = ts;
		}
		// now we add visuals and remove unselected
		removeUnselectedVisualization(curSelect);
		addSelectedVisualization(curSelect);
	}
	
	private void removeUnselectedVisualization(TimeSeries ts) {
		// Look for if the TimeSeries has a chart already
		for (int i = 0; i < charts.size(); i++) {
			if (charts.get(i).toString().equals(ts.toString())) {
				if (charts.get(i) instanceof TimeSeriesLineVisualization && !checkLine.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.LINE_CHART, false);
					i = 0;
				}
				else if (charts.get(i) instanceof PlotGraphVisualization && !checkPlot.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.PLOT_CHART, false);
					i = 0;
				}
				else if (charts.get(i) instanceof HistogramVisualization && !checkHisto.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.HISTOGRAM_CHART, false);
					i = 0;
				}
				else if (charts.get(i) instanceof StackedAreaVisualization && !checkStack.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.STACKED_AREA_CHART, false);
					i = 0;
				}
			}
		}
	}
	
	private void addSelectedVisualization(TimeSeries ts)  {
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = WindowHelper.dateFormat.parse(ts.getStartDate());
			endDate = WindowHelper.dateFormat.parse(ts.getEndDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (checkLine.isSelected() && !ts.getSetting(ChartType.LINE_CHART)) {
			MainWindow.getInstance().addVisualization(new TimeSeriesLineVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.LINE_CHART, true);
		}
		if (checkPlot.isSelected()) {
			MainWindow.getInstance().addVisualization(new PlotGraphVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.PLOT_CHART, true);
		}
		if (checkHisto.isSelected()) {
			MainWindow.getInstance().addVisualization(new HistogramVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.HISTOGRAM_CHART, true);
		}
		if (checkStack.isSelected()) {
			MainWindow.getInstance().addVisualization(new StackedAreaVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.STACKED_AREA_CHART, true);
		}
			
	}
	
	private void openInternalWindow(InternalFrame iFrame) {
		MainWindow.getInstance().frame.getLayeredPane().add(iFrame.frame);
	}
	
	public void close() {
		MainWindow.getInstance().getBtnVisualize().setEnabled(true);
	}
	
}
