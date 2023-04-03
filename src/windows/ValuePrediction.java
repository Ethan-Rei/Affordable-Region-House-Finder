package windows;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class ValuePrediction extends InternalFrame {
	private final String title = "Prediction";
	private final JLabel ValuePredictionLabel = new JLabel("Timeseries Prediction");
	private final JLabel algorithmlbl = new JLabel("Choose an algorithm:");
	private final JLabel loclbl = new JLabel("Location");
	private final JLabel startlbl = new JLabel("Start Date");
	private final JLabel endlbl = new JLabel("End Date");
	private final JLabel lblAmountOfMonths = new JLabel("Amount of months");
	private final JRadioButton linearrd = new JRadioButton("Linear Regression");
	private final JRadioButton gaussianrd = new JRadioButton("Gaussian Process");
	private final ButtonGroup algoGrp = new ButtonGroup();
	private final JComboBox<String> monthbx = new JComboBox<String>();
	private final JComboBox<String> locbx = new JComboBox<String>();
	private final JComboBox<String> startbx = new JComboBox<String>();
	private final JComboBox<String> endbx = new JComboBox<String>();
	private final JButton btnPredict = new JButton("Predict");

	/**
	 * Create the application.
	 */
	public ValuePrediction(HashMap<String, HashMap<Date, Double>> data) {
		createFrame(data);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void createFrame(HashMap<String, HashMap<Date, Double>> loadedData) {
		frame.setSize(450, 400);
		frame.setTitle(title);
		
		ValuePredictionLabel.setBounds(158, 21, 140, 16);
		frame.getContentPane().add(ValuePredictionLabel);
		
		algorithmlbl.setBounds(160, 49, 180, 16);
		frame.getContentPane().add(algorithmlbl);
		
		linearrd.setBounds(50, 92, 169, 23);
		frame.getContentPane().add(linearrd);
		
		gaussianrd.setBounds(250, 92, 141, 23);
		frame.getContentPane().add(gaussianrd);
		
		algoGrp.add(gaussianrd);
		algoGrp.add(linearrd);
		
		loclbl.setBounds(40, 150, 61, 16);
		frame.getContentPane().add(loclbl);
		
		locbx.setBounds(40, 180, 101, 27);
		frame.getContentPane().add(locbx);
		
		startlbl.setBounds(160, 150, 61, 16);
		frame.getContentPane().add(startlbl);
		
		startbx.setBounds(160, 180, 101, 27);
		frame.getContentPane().add(startbx);
		startbx.addActionListener(e -> WindowHelper.populateEndDate(locbx, startbx, endbx, e, loadedData));
		
		endlbl.setBounds(280, 150, 61, 16);
		frame.getContentPane().add(endlbl);
		
		endbx.setBounds(280, 180, 101, 27);
		frame.getContentPane().add(endbx);
		
		lblAmountOfMonths.setBounds(165, 235, 180, 16);
		frame.getContentPane().add(lblAmountOfMonths);
		
		monthbx.setBounds(150, 265, 138, 27);
		frame.getContentPane().add(monthbx);
		setMnthBoxValues();
		
		btnPredict.setBounds(160, 310, 117, 29);
		frame.getContentPane().add(btnPredict);
		
		frame.setVisible(true);
	}
	
	private void setMnthBoxValues() {
		for (int i = 1; i <= 12; i++) {
			monthbx.addItem(Integer.toString(i));
		}
		monthbx.setSelectedItem("1");
	}
	
	public void close() {
		MainWindow.getInstance().getBtnPredict().setEnabled(true);
	}

}
