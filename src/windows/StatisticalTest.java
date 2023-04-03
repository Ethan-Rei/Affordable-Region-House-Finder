package windows;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.util.Date;
import java.util.HashMap;

public class StatisticalTest extends InternalFrame {
	private final String title = "Statistical Test";
	private final JLabel lblNewLabel = new JLabel("Statistical Test");
	private final JLabel loclabel = new JLabel("Location");
	private final JLabel startlabel = new JLabel("Start Date");
	private final JLabel endlabel = new JLabel("End Date");
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
	public StatisticalTest(HashMap<String, HashMap<Date, Double>> data) {
		createFrame(data);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void createFrame(HashMap<String, HashMap<Date, Double>> loadedData) {
		frame.setSize(500, 320);
		frame.setTitle(title);
		
		lblNewLabel.setBounds(200, 20, 93, 23);
		frame.getContentPane().add(lblNewLabel);
		
		loclabel.setBounds(30, 70, 61, 16);
		frame.getContentPane().add(loclabel);
		
		locBox1.setBounds(30, 110, 101, 27);
		frame.getContentPane().add(locBox1);
		
		locBox2.setBounds(30, 160, 101, 27);
		frame.getContentPane().add(locBox2);
		
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

		btnCompare.setBounds(180, 224, 117, 29);
		frame.getContentPane().add(btnCompare);
		
		frame.setVisible(true);
	}
	
}
