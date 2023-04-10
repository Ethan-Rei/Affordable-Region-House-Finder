package windows;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import visuals.ChartType;
import visuals.HistogramVisualization;
import visuals.PlotGraphVisualization;
import visuals.StackedAreaVisualization;
import visuals.TimeSeriesLineVisualization;
import visuals.Visualization;
import visuals.VisualizationFactory;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

	public VisualizationWindow(HashMap<String, HashMap<Date, Double>> loadedData, ArrayList<Visualization> charts) {
		this.loadedData = loadedData;
		this.charts = charts;
		setInternalWindowSettings(title, 415, 280);
		createFrame();
		frame.setVisible(true);
	}

	public void createFrame() {
		getTimeSeries();
		setGUIBounds();
		setGUIListeners();
		addToInternalFrame();
	}
	
	private void setGUIBounds() {
		lblVisuals.setBounds(16, 24, 149, 16);
		checkLine.setBounds(16, 53, 83, 23);
		checkPlot.setBounds(105, 53, 88, 23);
		checkHisto.setBounds(195, 53, 88, 23);
		checkStack.setBounds(285, 53, 110, 23);
		lblSelect.setBounds(16, 93, 350, 16);
		boxTimeSeries.setBounds(16, 127, 370, 27);
		btnUpdate.setBounds(16, 183, 177, 37);
		btnEdit.setBounds(215, 183, 170, 37);
	}
	
	private void setGUIListeners() {
		boxTimeSeries.addActionListener(e -> loadTimeSeriesSettings());
		btnUpdate.addActionListener(e -> updateVisualizations());
		btnEdit.addActionListener(e -> openInternalWindow(new TimeSeriesEditorWindow(frame, btnEdit, charts, loadedData)));
	}
	
	private void addToInternalFrame() {
		frame.getContentPane().add(lblVisuals);
		frame.getContentPane().add(checkLine);
		frame.getContentPane().add(checkPlot);
		frame.getContentPane().add(checkHisto);
		frame.getContentPane().add(checkStack);
		frame.getContentPane().add(lblSelect);
		frame.getContentPane().add(boxTimeSeries);
		frame.getContentPane().add(btnUpdate);
		frame.getContentPane().add(btnEdit);
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
		TimeSeries curSelect = null;
		for (TimeSeries ts: timeSeries) {
			if(ts.toString().equals(boxTimeSeries.getSelectedItem().toString())) {
				// don't count the currently selected
				curSelect = ts;
				continue;
			}
			counter += ts.getSettingsCount();
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

		if (counter > 3) {
			JOptionPane.showMessageDialog(null, errorCount, "Error", JOptionPane.ERROR_MESSAGE);
			return;
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
					i--;
				}
				else if (charts.get(i) instanceof PlotGraphVisualization && !checkPlot.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.PLOT_CHART, false);
					i--;
				}
				else if (charts.get(i) instanceof HistogramVisualization && !checkHisto.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.HISTOGRAM_CHART, false);
					i--;
				}
				else if (charts.get(i) instanceof StackedAreaVisualization && !checkStack.isSelected()) {
					MainWindow.getInstance().removeVisualization(charts.get(i));
					ts.setSetting(ChartType.STACKED_AREA_CHART, false);
					i--;
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
			MainWindow.getInstance().addVisualization(VisualizationFactory.createTimeSeriesLineVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.LINE_CHART, true);
		}
		if (checkPlot.isSelected() && !ts.getSetting(ChartType.PLOT_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createPlotGraphVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.PLOT_CHART, true);
		}
		if (checkHisto.isSelected() && !ts.getSetting(ChartType.HISTOGRAM_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createHistogramVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.HISTOGRAM_CHART, true);
		}
		if (checkStack.isSelected() && !ts.getSetting(ChartType.STACKED_AREA_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createStackedAreaVisualization(ts.getLocation(), startDate, endDate, loadedData));
			ts.setSetting(ChartType.STACKED_AREA_CHART, true);
		}
			
	}
	
	private void openInternalWindow(InternalFrame iFrame) {
		MainWindow.getInstance().frame.getLayeredPane().add(iFrame.frame);
		frame.setClosable(false);
		btnEdit.setEnabled(false);
	}
	
	public void close() {
		MainWindow.getInstance().getBtnVisualize().setEnabled(true);
	}
	
}
