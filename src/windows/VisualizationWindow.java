package windows;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.Component;
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

	/**
	 * Create the application.
	 */
	public VisualizationWindow() {
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
		frame.getContentPane().add(btnUpdate);
		
		btnEdit.setBounds(215, 183, 170, 37);
		btnEdit.addActionListener(e -> openInternalWindow(new TimeSeriesEditorWindow(boxTimeSeries, btnEdit)));
		frame.getContentPane().add(btnEdit);
		frame.setVisible(true);
	}
	
	private void getTimeSeries() {
		ArrayList<TimeSeries> timeSeries = MainWindow.getLoadedTimeSeries();
		
		for (TimeSeries ts: timeSeries) {
			boxTimeSeries.addItem(ts.toString());
		}
		
		boxTimeSeries.setSelectedItem(null);
	}
	
	private void loadTimeSeriesSettings() {
		ArrayList<TimeSeries> timeSeries = MainWindow.getLoadedTimeSeries();
		
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
	
	private void openInternalWindow(InternalFrame iFrame) {
		MainWindow.getInstance().frame.getLayeredPane().add(iFrame.frame);
	}
	
	public void close() {
		MainWindow.getInstance().getBtnVisualize().setEnabled(true);
	}
	
}
