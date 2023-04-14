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
import java.util.HashMap;

import javax.swing.JButton;

public class VisualizationWindow extends InternalFrame {
	private final String title = "Visualization Options";
	private final JLabel lblVisuals = new JLabel("Select Visualizations:");
	private final JLabel lblSelect = new JLabel("Select a specific time series to edit its chart visualizations:");
	// Here
	private final JCheckBox checkLine = new JCheckBox("Line Chart");
	private final JCheckBox checkPlot = new JCheckBox("Plot Graph");
	private final JCheckBox checkHisto = new JCheckBox("Histogram");
	private final JCheckBox checkStack = new JCheckBox("Stacked Area");
	
	private final HashMap<ChartType, JCheckBox> boxes = new HashMap<>();
	
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
		populateBoxes();
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
		frame.getContentPane().add(checkLine);
		frame.getContentPane().add(checkPlot);
		frame.getContentPane().add(checkHisto);
		frame.getContentPane().add(checkStack);
		frame.getContentPane().add(lblVisuals);
		frame.getContentPane().add(lblSelect);
		frame.getContentPane().add(boxTimeSeries);
		frame.getContentPane().add(btnUpdate);
		frame.getContentPane().add(btnEdit);
	}
	
	private void populateBoxes() {
		boxes.put(ChartType.LINE_CHART, checkLine);
		boxes.put(ChartType.HISTOGRAM_CHART, checkHisto);
		boxes.put(ChartType.PLOT_CHART, checkPlot);
		boxes.put(ChartType.STACKED_AREA_CHART, checkStack);
	}
	
	private void loadTimeSeriesSettings() {
		ArrayList<TimeSeriesData> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for(TimeSeriesData ts: timeSeries) {
			if (!ts.toString().equals(boxTimeSeries.getSelectedItem().toString())) {
				continue;
			}
			for (ChartType type: boxes.keySet()) {
				boxes.get(type).setSelected(ts.getSetting(type));
			}
			break;
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
		for (ChartType type: boxes.keySet()) {
			if (boxes.get(type).isSelected())
				selected.add(type);
		}
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
		for (ChartType type: boxes.keySet()) {
			if (boxes.get(type).isSelected() && !ts.getSetting(type))
				MainWindow.getInstance().addVisualization(VisualizationFactory.createVisualization(type, ts));
		}
		setSettings(ts);
	}
	
	private void setSettings(TimeSeriesData ts) {
		for (ChartType type: boxes.keySet()) {
			ts.setSetting(type, boxes.get(type).isSelected());
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
