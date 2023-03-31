package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Panel;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;

public class MainWindow {

	private JFrame frame;
	private final JLabel locationLabel = new JLabel("Location");
	private final JComboBox btnLocation1 = new JComboBox();
	private final JComboBox btnLocation2 = new JComboBox();

	private final JButton loadData = new JButton("loadData");
	private final JLabel timesLabel = new JLabel("Times");
	private final JComboBox btnLocation1_1 = new JComboBox();
	private final JLabel toLabel = new JLabel("to");
	private final JLabel lblAnd = new JLabel("and");
	private final JLabel tabularViews = new JLabel("Tabular Views");
	private final Panel panelVisual = new Panel();
	private final JRadioButton btmTable = new JRadioButton("Table Summary");
	private final JRadioButton btnGraph = new JRadioButton("Graph Summary");
	private final JButton btnTimeseries = new JButton("Add Time-Series");
	private final JPanel panel_1 = new JPanel();
	private final JButton btnVisualize = new JButton("Visualize");
	private final JButton btnCompare = new JButton("Compare");
	private final JButton btnPredict = new JButton("Predict");
	private final ScrollPane scrollPane = new ScrollPane();
	private final JButton btnCancel = new JButton("Cancel");
	private final JComboBox comboBox = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		btnCancel.addActionListener (new ActionListener (){
			 public void actionPerformed (ActionEvent e) {
			  System.exit(0);
			 }
			});
		
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 941, 957);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("Affordable Housing APP");
		frame.getContentPane().setLayout(null);
		locationLabel.setBounds(45, 17, 61, 16);
		
		frame.getContentPane().add(locationLabel);
		btnLocation1.setToolTipText("");
		btnLocation1.setBounds(24, 41, 126, 27);
		
		frame.getContentPane().add(btnLocation1);
		btnLocation2.setBounds(24, 102, 126, 27);
		
		frame.getContentPane().add(btnLocation2);
		loadData.setBounds(99, 172, 117, 29);
		
		frame.getContentPane().add(loadData);
		timesLabel.setBounds(292, 17, 43, 16);
		
		frame.getContentPane().add(timesLabel);
		btnLocation1_1.setBounds(322, 43, 86, 26);
		
		frame.getContentPane().add(btnLocation1_1);
		toLabel.setBounds(303, 46, 27, 18);
		
		frame.getContentPane().add(toLabel);
		lblAnd.setBounds(71, 73, 27, 18);
		
		frame.getContentPane().add(lblAnd);
		panelVisual.setBounds(0, 226, 942, 398);
		
		frame.getContentPane().add(panelVisual);
		panelVisual.setLayout(null);
		scrollPane.setBounds(302, -32, 100, 100);
		
		panelVisual.add(scrollPane);
		btnTimeseries.setBounds(233, 86, 159, 29);
		
		frame.getContentPane().add(btnTimeseries);
		panel_1.setBounds(420, 0, 546, 224);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		tabularViews.setBounds(185, 17, 87, 16);
		panel_1.add(tabularViews);
		btmTable.setBounds(71, 42, 128, 23);
		panel_1.add(btmTable);
		btmTable.setToolTipText("");
		btnGraph.setBounds(248, 42, 131, 23);
		panel_1.add(btnGraph);
		btnGraph.setToolTipText("");
		btnVisualize.setBounds(162, 82, 137, 29);
		
		panel_1.add(btnVisualize);
		btnCompare.setBounds(16, 82, 127, 29);
		
		panel_1.add(btnCompare);
		btnPredict.setBounds(323, 82, 127, 29);
		
		panel_1.add(btnPredict);
		btnCancel.setBounds(218, 172, 117, 29);
		
		frame.getContentPane().add(btnCancel);
		comboBox.setBounds(186, 41, 105, 27);
		
		frame.getContentPane().add(comboBox);
		

		frame.setVisible(true);
	}
}
