package windows;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import visuals.TimeSeriesData;
import visuals.Visualization;

public class TimeSeriesEditorWindow extends InternalFrame {
	
	private final String title = "Chart Editor";
	private final JButton btnAdd = new JButton("Add time series to chart");
	private final JComboBox<String> boxChart1 = new JComboBox<>();
	private final JComboBox<String> boxChart2 = new JComboBox<>();
	private final JLabel lblModify = new JLabel("Select time series chart to change:");
	private final JLabel lblFrom = new JLabel("Select time series to add to the chart:");
	
	private final JInternalFrame refPanel;
	private final JButton refButton;
	
	private final String errorSelection = "Please select both time series.";
	private final String errorDateMismatch = "The time series you are adding to the chart must have the same start and end date.";
	
	public TimeSeriesEditorWindow(JInternalFrame refPanel, JButton refButton) {
		this.refPanel = refPanel;
		this.refButton = refButton;
		setInternalWindowSettings(title, 280, 300);
		createFrame();
		frame.setVisible(true);
	}
	
	public void createFrame() {
		populateLoadedChartsBox(boxChart1);
		populateLoadedTSBox(boxChart2);
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

	private TimeSeriesData getTimeSeriesToAdd() {
		ArrayList<TimeSeriesData> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		for (TimeSeriesData ts: timeSeries) {
			if (boxChart2.getSelectedItem().equals(ts.toString())) {
				return ts;
			}
		}
		return null;
	}

	private Visualization getVisualizationToAdd() {
		return MainWindow.getInstance().getCharts().get(boxChart1.getSelectedIndex());
	}

	private void addTimeSeries() {
		if (boxesNull())
			return;
		TimeSeriesData tsToAdd = getTimeSeriesToAdd();
		Visualization chartToEdit = getVisualizationToAdd();
		if (choicesMismatch(tsToAdd, chartToEdit.getTimeSeries())) {
			JOptionPane.showMessageDialog(null, errorDateMismatch, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		chartToEdit.addTimeSeries(tsToAdd);
	}

	private boolean choicesMismatch(TimeSeriesData loc1, TimeSeriesData loc2) {
		if (!loc1.getStartDateAsDate().equals(loc2.getStartDateAsDate()) && !loc1.getEndDateAsDate().equals(loc2.getEndDateAsDate())) {
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
	
	public void close() {
		refPanel.setClosable(true);
		refButton.setEnabled(true);
		MainWindow.getInstance().frame.remove(frame);
		frame.dispose();
	}
	
}
