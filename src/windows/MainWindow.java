package windows;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.Panel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import database.Database;

import javax.swing.JPanel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainWindow extends WindowFrame {
	public static final String DATE_FORMAT = "yyyy-MM";
	private final String title = "Affordable Region House Finder";
	private final JLabel lblCompareLoc = new JLabel("Pick Location");
	private final JLabel lblAnd = new JLabel("and");
	private final JComboBox<String> boxLocation = new JComboBox<>();
	
	private final JLabel lblTimes = new JLabel("Times");
	private final JLabel lblTo = new JLabel("to");
	private final JComboBox<String> boxBegTime = new JComboBox<>();
	private final JComboBox<String> boxEndTime = new JComboBox<>();
	private final JButton btnTimeSeries = new JButton("Add Time-Series");
	
	private final JPanel panRightMenuOptions = new JPanel();
	private final JLabel tabularViews = new JLabel("Tabular Views");
	private final JRadioButton radbtnTable = new JRadioButton("Table Summary");
	private final JRadioButton radbtnGraph = new JRadioButton("Graph Summary");
	private final JButton btnVisualize = new JButton("Visualizations...");
	private final JButton btnCompare = new JButton("Statistical Test...");
	private final JButton btnPredict = new JButton("Predict...");
	
	private final Panel panVisual = new Panel();
	private final ScrollPane scrollPane = new ScrollPane();
	private final JLabel lblSelectVis = new JLabel("Add your time series to visualize...");
	
	private final JSeparator sepVert = new JSeparator();
	private final JSeparator sepHori = new JSeparator();
	
	private final HashMap<String, HashMap<Date, Double>> loadedTimeSeries = new HashMap<>();

	/**
	 * Create the application.
	 */
	public MainWindow() {
		setLocationBoxes();
		setTimeBoxes();
		createWindow();
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void createWindow() {
		frame.setSize(1000, 750);
		frame.setTitle(title);
		
		// left side menu options
		lblCompareLoc.setBounds(50, 17, 130, 16);
		boxLocation.setBounds(24, 41, 190, 27);
		lblAnd.setBounds(100, 75, 27, 18);
		
		lblTimes.setBounds(325, 17, 43, 16);
		boxBegTime.setBounds(240, 41, 90, 27);
		lblTo.setBounds(340, 46, 27, 18);
		boxEndTime.setBounds(360, 41, 90, 27);
		btnTimeSeries.setBounds(170, 80, 140, 27);
		btnTimeSeries.addActionListener(e -> addTimeSeries());
		
		sepVert.setBounds(485, 0, 18, 130);
		sepVert.setOrientation(SwingConstants.VERTICAL);
		sepHori.setBounds(0, 130, 1000, 18);
		
		// right side menu options
		panRightMenuOptions.setBounds(505, 0, 546, 224);
		panRightMenuOptions.setLayout(null);
		
		tabularViews.setBounds(185, 17, 87, 16);
		radbtnTable.setBounds(71, 42, 128, 23);
		radbtnTable.addActionListener(e -> selectTableView(e));
		radbtnGraph.setBounds(248, 42, 131, 23);
		radbtnGraph.setSelected(true);
		radbtnGraph.addActionListener(e -> selectGraphView(e));
		btnVisualize.setBounds(162, 82, 137, 29);
		btnCompare.setBounds(16, 82, 127, 29);
		btnCompare.setEnabled(false);
		btnPredict.setBounds(320, 82, 127, 29);
		btnPredict.setEnabled(false);
		
		// left side menu adds
		frame.getContentPane().add(lblCompareLoc);
		frame.getContentPane().add(boxLocation);
		//frame.getContentPane().add(lblAnd);
		//.getContentPane().add(boxSecondLocation);
		
		frame.getContentPane().add(boxBegTime);
		frame.getContentPane().add(lblTimes);
		frame.getContentPane().add(lblTo);
		frame.getContentPane().add(boxEndTime);
		frame.getContentPane().add(btnTimeSeries);
		
		frame.getContentPane().add(sepVert);
		frame.getContentPane().add(sepHori);
		
		// right side menu adds
		frame.getContentPane().add(panRightMenuOptions);
		panRightMenuOptions.add(tabularViews);
		panRightMenuOptions.add(radbtnTable);
		panRightMenuOptions.add(radbtnGraph);
		panRightMenuOptions.add(btnVisualize);
		panRightMenuOptions.add(btnCompare);
		panRightMenuOptions.add(btnPredict);
		
		// visualizations options
		panVisual.setBounds(0, 130, frame.getWidth(), frame.getHeight()-200);
		panVisual.setLayout(null);
		
		Font visual = new Font("Dialog", 0, 24);
		lblSelectVis.setFont(visual);
		lblSelectVis.setBounds(315, 230, 450, 30);
		//scrollPane.setBounds(302, -32, 100, 100);
		
		// visualizations adds
		frame.getContentPane().add(panVisual);
		//panVisual.add(scrollPane);
		panVisual.add(lblSelectVis);

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
			boxBegTime.addItem(times.get(i));
			boxEndTime.addItem(times.get(i));
		}
	}
	
	private void selectGraphView(ActionEvent e) {
		if (radbtnTable.isSelected()) {
			radbtnTable.setSelected(false);
		}
	}
	
	private void selectTableView(ActionEvent e) {
		if (radbtnGraph.isSelected()) {
			radbtnGraph.setSelected(false);
		}
	}
	
	private void addTimeSeries() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		HashMap<Date, Double> timeSeries = null;
		Date beg = null;
		Date end = null;
		try {
			beg = format.parse(boxBegTime.getSelectedItem().toString());
			end = format.parse(boxEndTime.getSelectedItem().toString());
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		loadedTimeSeries.put(boxLocation.getSelectedItem().toString(), null);
	}
}
