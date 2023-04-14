package windows;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import database.Database;
import visuals.ChartType;
import visuals.TimeSeriesData;
import visuals.TimeSeriesLineVisualization;
import visuals.Visualization;
import javax.swing.JPanel;
import java.util.ArrayList;

public class MainWindow extends WindowFrame {
	private static MainWindow singleton;
	private final String title = "Affordable Region House Finder";
	private final JPanel panLeftMenuOptions = new JPanel();
	private final JLabel lblPickLoc = new JLabel("Pick Location");
	private final JComboBox<String> boxLocation = new JComboBox<>();

	private final JLabel lblTimes = new JLabel("Times");
	private final JLabel lblTo = new JLabel("to");
	private final JComboBox<String> boxStartTime = new JComboBox<>();
	private final JComboBox<String> boxEndTime = new JComboBox<>();
	private final JButton btnTimeSeries = new JButton("Add Time-Series");

	private final JPanel panRightMenuOptions = new JPanel();
	private final JLabel lblTabularViews = new JLabel("Tabular Views");
	private final ButtonGroup btngrpVisual = new ButtonGroup();
	private final JRadioButton radbtnRaw = new JRadioButton("Raw Data");
	private final JRadioButton radbtnSummary = new JRadioButton("Statistics");
	private final JButton btnVisualize = new JButton("Visualizations...");
	private final JButton btnCompare = new JButton("Statistical Test...");
	private final JButton btnPredict = new JButton("Predict...");

	private final JPanel panVisual = new JPanel();

	private final JSeparator sepVert = new JSeparator();
	private final JSeparator sepHori = new JSeparator();

	private final String errorDate = "Selected dates are invalid. If you have selected the same start and end date, please change it.";

	private final ArrayList<TimeSeriesData> loadedTimeSeries = new ArrayList<>();
	private final ArrayList<Visualization> charts = new ArrayList<>();

	private MainWindow() {
		setWindowSettings(title, 1000, 730);
		createWindow();
		frame.setVisible(true);
	}

	public static MainWindow getInstance() {
		if (singleton == null)
			singleton = new MainWindow();
		return singleton;
	}

	@Override
	public void createWindow() {
		setLocationBoxes();
		setTimeBoxes();
		addLeftMenuGUI();
		addRightMenuGUI();
		addVisualPanelGUI();
		addToWindowFrame();
	}

	private void addLeftMenuGUI() {
		configLeftMenu();
		addToLeftMenu();
	}

	private void configLeftMenu() {
		panLeftMenuOptions.setBounds(0, 0, 500, 130);
		panLeftMenuOptions.setLayout(null);
		sepVert.setOrientation(SwingConstants.VERTICAL);

		setLeftMenuBounds();
		setLeftMenuActionListeners();
	}

	private void setLeftMenuBounds() {
		lblPickLoc.setBounds(50, 17, 130, 16);
		boxLocation.setBounds(24, 41, 190, 27);
		lblTimes.setBounds(325, 17, 43, 16);
		boxStartTime.setBounds(240, 41, 90, 27);
		lblTo.setBounds(340, 46, 27, 18);
		boxEndTime.setBounds(360, 41, 90, 27);
		btnTimeSeries.setBounds(170, 80, 140, 27);
		sepVert.setBounds(485, 0, 18, 130);
	}

	private void setLeftMenuActionListeners() {
		btnTimeSeries.addActionListener(e -> addTimeSeries());
	}

	private void addToLeftMenu() {
		panLeftMenuOptions.add(lblPickLoc);
		panLeftMenuOptions.add(boxLocation);
		panLeftMenuOptions.add(boxStartTime);
		panLeftMenuOptions.add(lblTimes);
		panLeftMenuOptions.add(lblTo);
		panLeftMenuOptions.add(boxEndTime);
		panLeftMenuOptions.add(btnTimeSeries);
		panLeftMenuOptions.add(sepVert);
	}

	private void addRightMenuGUI() {
		configRightMenu();
		addToRightMenu();
	}

	private void configRightMenu() {
		panRightMenuOptions.setBounds(505, 0, 546, 130);
		panRightMenuOptions.setLayout(null);

		setRightMenuBounds();
		setRightMenuActionListeners();
		setRightMenuEnables();
	}

	private void setRightMenuBounds() {
		lblTabularViews.setBounds(185, 17, 87, 16);
		radbtnRaw.setBounds(100, 42, 128, 23);
		radbtnSummary.setBounds(270, 42, 131, 23);
		btnVisualize.setBounds(162, 82, 137, 29);
		btnCompare.setBounds(14, 82, 130, 29);
		btnPredict.setBounds(320, 82, 127, 29);
	}

	private void setRightMenuActionListeners() {
		radbtnRaw.addActionListener(e -> showRawTables());
		radbtnSummary.addActionListener(e -> showSummaryTables());
		btnVisualize.addActionListener(e -> openInternalWindow(new VisualizationWindow(), btnVisualize));
		btnCompare.addActionListener(e -> openInternalWindow(new StatisticalWindow(), btnCompare));
		btnPredict.addActionListener(e -> openInternalWindow(new PredictionWindow(), btnPredict));
	}

	private void setRightMenuEnables() {
		btnVisualize.setEnabled(false);
		btnCompare.setEnabled(false);
		btnPredict.setEnabled(false);
		radbtnRaw.setSelected(true);
		radbtnRaw.setEnabled(false);
		radbtnSummary.setEnabled(false);
	}

	private void addToRightMenu() {
		panRightMenuOptions.add(lblTabularViews);
		panRightMenuOptions.add(radbtnRaw);
		panRightMenuOptions.add(radbtnSummary);
		panRightMenuOptions.add(btnVisualize);
		panRightMenuOptions.add(btnCompare);
		panRightMenuOptions.add(btnPredict);
		btngrpVisual.add(radbtnSummary);
		btngrpVisual.add(radbtnRaw);
	}

	private void addVisualPanelGUI() {
		configVisualPanel();
		addToVisualPanel();
	}

	private void configVisualPanel() {
		panVisual.setBounds(0, 130, 1000, 600);
		panVisual.setLayout(null);
		sepHori.setBounds(0, 0, 1000, 18);
	}

	private void addToVisualPanel() {
		panVisual.add(sepHori);
	}

	private void addToWindowFrame() {
		frame.getContentPane().add(panLeftMenuOptions);
		frame.getContentPane().add(panRightMenuOptions);
		frame.getContentPane().add(panVisual);
	}

	private void setLocationBoxes() {
		ArrayList<String> locations = Database.getInstance().getQuery().getAllLocations();
		for (int i = 0; i < locations.size(); i++) {
			boxLocation.addItem(locations.get(i));
		}
	}

	private void setTimeBoxes() {
		ArrayList<String> times = Database.getInstance().getQuery().getAllTimes();
		for (int i = 0; i < times.size(); i++) {
			boxStartTime.addItem(times.get(i));
			boxEndTime.addItem(times.get(i));
		}
	}

	private void openInternalWindow(InternalFrame iFrame, JButton button) {
		frame.getLayeredPane().add(iFrame.getFrame());
		button.setEnabled(false);
	}

	private void showRawTables() {
		for(Visualization chart: charts) {
			chart.getScrollPaneRaw().setVisible(true);
			chart.getScrollPaneSummary().setVisible(false);
		}
	}

	private void showSummaryTables() {
		for(Visualization chart: charts) {
			chart.getScrollPaneRaw().setVisible(false);
			chart.getScrollPaneSummary().setVisible(true);
		}
	}

	private void addTimeSeries() {
		String location = boxLocation.getSelectedItem().toString();
		String startTime = boxStartTime.getSelectedItem().toString();
		String endTime = boxEndTime.getSelectedItem().toString();
		TimeSeriesData newSeries = new TimeSeriesData(location, startTime, endTime);

		if (!addTimeSeriesValid(newSeries, startTime, endTime))
			return;
		loadedTimeSeries.add(newSeries);

		if (!isFull()) {
			addVisualization(new TimeSeriesLineVisualization(newSeries));
			newSeries.setSetting(ChartType.LINE_CHART, true);
		}

		updateButtonStates();
	}

	private void updateButtonStates() {
		if (loadedTimeSeries.size() >= 2) {
			btnCompare.setEnabled(true);
		}
		if (loadedTimeSeries.size() >= 1) {
			btnPredict.setEnabled(true);
			radbtnRaw.setEnabled(true);
			radbtnSummary.setEnabled(true);
			btnVisualize.setEnabled(true);
		}
	}

	private boolean addTimeSeriesValid(TimeSeriesData series, String startTime, String endTime) {
		if (inLoadedTimeSeries(series)) {
			return false;
		}
		if (startTime.compareTo(endTime) >= 0) {
			JOptionPane.showMessageDialog(null, errorDate, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean inLoadedTimeSeries(TimeSeriesData newSeries) {
		for (TimeSeriesData series: loadedTimeSeries) {
			if (series.equals(newSeries))
				return true;
		}
		return false;
	}

	public void addVisualization(Visualization visualization) {
		// Add visualization's panel
		JPanel visualizationPanel = visualization.getPanel();
		visualizationPanel.setLayout(null);
		visualizationPanel.setBounds(charts.size() * 320 + 13, 7, 320, 550);
		panVisual.add(visualizationPanel);
		visualizationPanel.setVisible(true);
		charts.add(visualization);
		panVisual.repaint();
	}

	public void removeVisualization(Visualization visualization) {
		if (charts.contains(visualization)) {
			panVisual.remove(visualization.getPanel());
			charts.remove(visualization);

			for (int i = 0; i < charts.size(); i++) {
				charts.get(i).getPanel().setBounds(i * 320 + 13, 7, 320, 550);
			}

			refresh();
		}
	}

	public void refresh() {
		panVisual.repaint();
	}

	private boolean isFull() {
		return charts.size() == 3;
	}

	public JButton getBtnVisualize() {
		return btnVisualize;
	}

	public JButton getBtnCompare() {
		return btnCompare;
	}

	public JButton getBtnPredict() {
		return btnPredict;
	}

	public ArrayList<TimeSeriesData> getLoadedTimeSeries() {
		return loadedTimeSeries;
	}

	public ArrayList<Visualization> getCharts() {
		return charts;
	}
}