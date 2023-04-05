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
import visuals.TimeSeriesLineVisualization;
import visuals.Visualization;

import javax.swing.JPanel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainWindow extends WindowFrame {
	private static MainWindow singleton;
	private final String title = "Affordable Region House Finder";
	private final JPanel panLeftMenuOptions = new JPanel();
	private final JLabel lblCompareLoc = new JLabel("Pick Location");
	private final JLabel lblAnd = new JLabel("and");
	private final JComboBox<String> boxLocation = new JComboBox<>();
	private final JLabel lblTimes = new JLabel("Times");
	private final JLabel lblTo = new JLabel("to");
	private final JComboBox<String> boxStartTime = new JComboBox<>();
	private final JComboBox<String> boxEndTime = new JComboBox<>();
	private final JButton btnTimeSeries = new JButton("Add Time-Series");
	
	private final JPanel panRightMenuOptions = new JPanel();
	private final JLabel tabularViews = new JLabel("Tabular Views");
	private final ButtonGroup visualGrp = new ButtonGroup();
	private final JRadioButton radbtnRaw = new JRadioButton("Raw Data");
	private final JRadioButton radbtnSummary = new JRadioButton("Statistics");
	private final JButton btnVisualize = new JButton("Visualizations...");
	private final JButton btnCompare = new JButton("Statistical Test...");
	private final JButton btnPredict = new JButton("Predict...");
	
	private final JPanel panVisual = new JPanel();
	
	private final JSeparator sepVert = new JSeparator();
	private final JSeparator sepHori = new JSeparator();
	
	private final String errorDate = "Selected dates are invalid. If you have selected the same start and end date, please change it.";
	
	private final ArrayList<TimeSeries> loadedTimeSeries = new ArrayList<>();
	private final HashMap<String, HashMap<Date, Double>> loadedData = new HashMap<>();
	private final ArrayList<Visualization> charts = new ArrayList<Visualization>();

	/**
	 * Create the application.
	 */
	private MainWindow() {
		setLocationBoxes();
		setTimeBoxes();
	}
	
	public ArrayList<TimeSeries> getLoadedTimeSeries() {
		return loadedTimeSeries;
	}
	
	public static MainWindow getInstance() {
		if (singleton == null)
			singleton = new MainWindow();
		return singleton;
	}
	
	public static void main(String[] args) {
		MainWindow.getInstance().createWindow();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void createWindow() {
		frame.setSize(1000, 730);
		frame.setLocationRelativeTo(null);
		frame.setTitle(title);
		
		// left side menu options
		panLeftMenuOptions.setBounds(0, 0, 500, 130);
		panLeftMenuOptions.setLayout(null);
		
		lblCompareLoc.setBounds(50, 17, 130, 16);
		boxLocation.setBounds(24, 41, 190, 27);
		lblAnd.setBounds(100, 75, 27, 18);
		
		lblTimes.setBounds(325, 17, 43, 16);
		boxStartTime.setBounds(240, 41, 90, 27);
		lblTo.setBounds(340, 46, 27, 18);
		boxEndTime.setBounds(360, 41, 90, 27);
		btnTimeSeries.setBounds(170, 80, 140, 27);
		btnTimeSeries.addActionListener(e -> addTimeSeries());
		
		sepVert.setBounds(485, 0, 18, 130);
		sepVert.setOrientation(SwingConstants.VERTICAL);
		sepHori.setBounds(0, 0, 1000, 18);
		
		// right side menu options
		panRightMenuOptions.setBounds(505, 0, 546, 130);
		panRightMenuOptions.setLayout(null);
		
		tabularViews.setBounds(185, 17, 87, 16);
		radbtnRaw.setBounds(100, 42, 128, 23);
		radbtnRaw.addActionListener(e -> showRawTables());
		radbtnSummary.setBounds(270, 42, 131, 23);
		radbtnSummary.addActionListener(e -> showSummaryTables());
		btnVisualize.setBounds(162, 82, 137, 29);
		btnVisualize.addActionListener(e -> openInternalWindow(new VisualizationWindow(loadedData, charts), btnVisualize));
		btnVisualize.setEnabled(false);
		btnCompare.setBounds(14, 82, 130, 29);
		btnCompare.setEnabled(false);
		btnCompare.addActionListener(e -> openInternalWindow(new StatisticalWindow(loadedData), btnCompare));
		btnPredict.setBounds(320, 82, 127, 29);
		btnPredict.setEnabled(false);
		btnPredict.addActionListener(e -> openInternalWindow(new PredictionWindow(loadedData, charts), btnPredict));
		radbtnRaw.setSelected(true);
		radbtnRaw.setEnabled(false);
		radbtnSummary.setEnabled(false);
		visualGrp.add(radbtnSummary);
		visualGrp.add(radbtnRaw);
		
		// left side menu adds
		frame.getContentPane().add(panLeftMenuOptions);
		panLeftMenuOptions.add(lblCompareLoc);
		panLeftMenuOptions.add(boxLocation);
		//frame.getContentPane().add(lblAnd);
		//.getContentPane().add(boxSecond	Location);
		
		panLeftMenuOptions.add(boxStartTime);
		panLeftMenuOptions.add(lblTimes);
		panLeftMenuOptions.add(lblTo);
		panLeftMenuOptions.add(boxEndTime);
		panLeftMenuOptions.add(btnTimeSeries);
		
		panLeftMenuOptions.add(sepVert);
		panVisual.add(sepHori);
		
		// right side menu adds
		frame.getContentPane().add(panRightMenuOptions);
		panRightMenuOptions.add(tabularViews);
		panRightMenuOptions.add(radbtnRaw);
		panRightMenuOptions.add(radbtnSummary);
		panRightMenuOptions.add(btnVisualize);
		panRightMenuOptions.add(btnCompare);
		panRightMenuOptions.add(btnPredict);
		
		// visualizations options
		panVisual.setBounds(0, 130, 1000, 600);
		panVisual.setLayout(null);
		
		// visualizations adds
		frame.getContentPane().add(panVisual);
		
		// Panel for internal windows
		
		frame.setVisible(true);
	}
	
	private void setLocationBoxes() {
		ArrayList<String> locations = Database.getInstance().queryLocations();
		for (int i = 0; i < locations.size(); i++) {
			boxLocation.addItem(locations.get(i));
		}
	}
	
	private void setTimeBoxes() {
		ArrayList<String> times = Database.getInstance().queryTimes();
		for (int i = 0; i < times.size(); i++) {
			boxStartTime.addItem(times.get(i));
			boxEndTime.addItem(times.get(i));
		}
	}
	
	private void openInternalWindow(InternalFrame iFrame, JButton button) {
		frame.getLayeredPane().add(iFrame.frame);
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
		
		// Check if selected dates are valid could be its own method
		if (startTime.compareTo(endTime) >= 0) {
			JOptionPane.showMessageDialog(null, errorDate, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// store into timeseries array if its not in there already
		
		TimeSeries newSeries = new TimeSeries(location, startTime, endTime);
		if (inLoadedTimeSeries(newSeries)) {
			return;
		}
		loadedTimeSeries.add(newSeries);
		
		// create hashmap for the nhpi values
		if(loadedData.get(location) == null)
			loadedData.put(location, new HashMap<Date, Double>());
		
		HashMap<Date, Double> timeSeries = loadedData.get(location);

		try {
			// query database for nhpi values
			ResultSet NHPIQuery = Database.getInstance().queryNHPI(location, startTime, endTime);
			while (NHPIQuery.next()) {
				Date refdate = WindowHelper.dateFormat.parse(NHPIQuery.getString(1).substring(0, NHPIQuery.getString(1).length()-3));
				timeSeries.put(refdate, NHPIQuery.getDouble(2));
			}
			
			// Create a new panel with the chart based on the query
			Date startDate = WindowHelper.dateFormat.parse(startTime);
			Date endDate = WindowHelper.dateFormat.parse(endTime);
			
			// Check if there's a max # of panels
			if (!isFull()) {
				// Create new visualization
				Visualization newVisualization = new TimeSeriesLineVisualization(location, startDate, endDate, loadedData);
				addVisualization(newVisualization);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (loadedData.size() >= 2) {
			btnCompare.setEnabled(true);
		}
		if (loadedData.size() >= 1) {
			btnPredict.setEnabled(true);
			radbtnRaw.setEnabled(true);
			radbtnSummary.setEnabled(true);
			btnVisualize.setEnabled(true);
		}
	}

	private boolean inLoadedTimeSeries(TimeSeries newSeries) {
		for (TimeSeries series: loadedTimeSeries) {
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
		loadedTimeSeries.get(loadedTimeSeries.size()-1).setSetting(ChartType.LINE_CHART, true);
		panVisual.repaint();
	}
	
	public void removeVisualization(Visualization visualization) {
		if (charts.contains(visualization)) {
			panVisual.remove(visualization.getPanel());
			charts.remove(visualization);
			
			for (int i = 0; i < charts.size(); i++) {
				charts.get(i).getPanel().setBounds(i * 320 + 13, 7, 320, 550);
			}
			
			panVisual.repaint();
		}
	
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
	
	public void refresh() {
		panVisual.repaint();
	}
}
