package windows;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.util.Date;
import java.util.HashMap;

public class StatisticalWindow extends InternalFrame {
	private final String title = "Statistical Test";
	private final JLabel lblNewLabel = new JLabel("Statistical Test");
	private final JLabel loclabel = new JLabel("Location");
	private final JLabel startlabel = new JLabel("Start Date");
	private final JLabel endlabel = new JLabel("End Date");
	private final JLabel plabel = new JLabel("P - Value");
	private final JComboBox<String> pBox = new JComboBox<String>();
	private final JComboBox<String> locBox1 = new JComboBox<String>();
	private final JComboBox<String> locBox2 = new JComboBox<String>();
	private final JComboBox<String> startBox1 = new JComboBox<String>();
	private final JComboBox<String> startBox2 = new JComboBox<String>();
	private final JComboBox<String> endBox1 = new JComboBox<String>();
	private final JComboBox<String> endBox2 = new JComboBox<String>();
	private final JButton btnCompare = new JButton("Compare");

	/**
	 * Create the application.
	 */
	public StatisticalWindow(HashMap<String, HashMap<Date, Double>> data) {
		createFrame(data);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void createFrame(HashMap<String, HashMap<Date, Double>> loadedData) {
		frame.setSize(500, 380);
		frame.setTitle(title);
		
		lblNewLabel.setBounds(200, 20, 93, 23);
		frame.getContentPane().add(lblNewLabel);
		
		loclabel.setBounds(30, 70, 61, 16);
		frame.getContentPane().add(loclabel);
		
		locBox1.setBounds(30, 110, 101, 27);
		frame.getContentPane().add(locBox1);
		WindowHelper.populateLocBox(locBox1, loadedData);
		locBox1.addActionListener(e -> WindowHelper.populateStartDate(locBox1, startBox1, e, loadedData));
		
		locBox2.setBounds(30, 160, 101, 27);
		frame.getContentPane().add(locBox2);
		WindowHelper.populateLocBox(locBox2, loadedData);
		locBox2.addActionListener(e -> WindowHelper.populateStartDate(locBox2, startBox2, e, loadedData));
		
		startlabel.setBounds(180, 70, 61, 16);
		frame.getContentPane().add(startlabel);
		
		startBox1.setBounds(180, 110, 101, 27);
		frame.getContentPane().add(startBox1);
		startBox1.addActionListener(e -> WindowHelper.populateEndDate(locBox1, startBox1, endBox1, e, loadedData));
		
		startBox2.setBounds(180, 160, 101, 27);
		frame.getContentPane().add(startBox2);
		startBox2.addActionListener(e -> WindowHelper.populateEndDate(locBox2, startBox2, endBox2, e, loadedData));
		
		endlabel.setBounds(330, 70, 61, 16);
		frame.getContentPane().add(endlabel);
		
		endBox1.setBounds(330, 110, 101, 27);
		frame.getContentPane().add(endBox1);
		
		endBox2.setBounds(330, 160, 101, 27);
		frame.getContentPane().add(endBox2);

		plabel.setBounds(180, 204, 117, 29);
		frame.getContentPane().add(plabel);
		
		pBox.setBounds(180, 244, 117, 29);
		frame.getContentPane().add(pBox);
		populatePValues();
		
		btnCompare.setBounds(180, 294, 117, 29);
		frame.getContentPane().add(btnCompare);
		
		frame.setVisible(true);
	}
	
	public void close() {
		MainWindow.getInstance().getBtnCompare().setEnabled(true);
	}
	
	
	private void populatePValues() {
		double val = 0.05;
		for (int i = 0; i <= 15; i++) {
			pBox.addItem(String.format("%.2f", val));
			val += 0.01;
		}
	}

	
}
