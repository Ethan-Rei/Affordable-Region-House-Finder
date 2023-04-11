package windows;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import visuals.ChartType;
import visuals.TimeSeriesData;
import visuals.Visualization;
import visuals.VisualizationFactory;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.util.ArrayList;

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
	
	private final String errorSelection = "Please select a time series chart to edit.";
	private final String errorCount = "You have more than 3 charts selected. Please unselect some.";

	public VisualizationWindow() {
		setInternalWindowSettings(title, 415, 280);
		createFrame();
		frame.setVisible(true);
	}

	public void createFrame() {
		populateLoadedTSBox(boxTimeSeries);
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
		btnEdit.addActionListener(e -> openInternalWindow(new TimeSeriesEditorWindow(frame, btnEdit)));
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
	
	private void loadTimeSeriesSettings() {
		ArrayList<TimeSeriesData> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for(TimeSeriesData ts: timeSeries) {
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
		
		// check through all settings, throw error if they already had 3 selected
		if (countSelectedVisualizations() > 3) {
			JOptionPane.showMessageDialog(null, errorCount, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		TimeSeriesData curSelect = getSelectedTimeSeries(boxTimeSeries.getSelectedItem().toString());
		
		// now we add visuals and remove unselected
		removeUnselectedVisualization(curSelect);
		addSelectedVisualization(curSelect);
	}
	
	private int countSelectedVisualizations() {
		int counter = 0;
		for (TimeSeriesData ts: MainWindow.getInstance().getLoadedTimeSeries()) {
			if(ts.toString().equals(boxTimeSeries.getSelectedItem().toString()))
				continue;
			counter += ts.getSettingsCount();
		}
		
		// count the boxes selected now
		counter += getSelectedBoxes().size();
		return counter;
	}
	
	private ArrayList<ChartType> getSelectedBoxes() {
		ArrayList<ChartType> selected = new ArrayList<>();
		if (checkLine.isSelected())
			selected.add(ChartType.LINE_CHART);
		if (checkPlot.isSelected())
			selected.add(ChartType.PLOT_CHART);
		if (checkHisto.isSelected())
			selected.add(ChartType.HISTOGRAM_CHART);
		if (checkStack.isSelected())
			selected.add(ChartType.STACKED_AREA_CHART);
		return selected;
	}
	
	private void removeUnselectedVisualization(TimeSeriesData ts) {
		// Look for if the TimeSeries has a chart already
		ArrayList<Visualization> charts = MainWindow.getInstance().getCharts();
		for (int i = 0; i < charts.size(); i++) {
			if (charts.get(i).getTimeSeries().equals(ts) && !getSelectedBoxes().contains(charts.get(i).getType())) {
				MainWindow.getInstance().removeVisualization(charts.get(i));
				i--;
			}
		}
	}
	
	private void addSelectedVisualization(TimeSeriesData ts)  {
		if (checkLine.isSelected() && !ts.getSetting(ChartType.LINE_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createTimeSeriesLineVisualization(ts));
		}
		if (checkPlot.isSelected() && !ts.getSetting(ChartType.PLOT_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createPlotGraphVisualization(ts));
		}
		if (checkHisto.isSelected() && !ts.getSetting(ChartType.HISTOGRAM_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createHistogramVisualization(ts));
		}
		if (checkStack.isSelected() && !ts.getSetting(ChartType.STACKED_AREA_CHART)) {
			MainWindow.getInstance().addVisualization(VisualizationFactory.createStackedAreaVisualization(ts));
		}
		
		setSettings(ts);
	}
	
	private void setSettings(TimeSeriesData ts) {
		ts.setSetting(ChartType.LINE_CHART, checkLine.isSelected());
		ts.setSetting(ChartType.PLOT_CHART, checkPlot.isSelected());
		ts.setSetting(ChartType.HISTOGRAM_CHART, checkHisto.isSelected());
		ts.setSetting(ChartType.STACKED_AREA_CHART, checkStack.isSelected());
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
