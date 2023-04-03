package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class StatisticalTest {

	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("Statstical Test");
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
	private HashMap<String, HashMap<Date, Double>> loadedData;
	private Calendar calendar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatisticalTest window = new StatisticalTest();
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
	public StatisticalTest() {
		HashMap<String, HashMap<Date, Double>> data = null;
		initialize(data);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(HashMap<String, HashMap<Date, Double>> loadedData) {
		
		this.loadedData = loadedData;
		this.calendar = Calendar.getInstance();
		
		startBox1.addActionListener(locBox1);
		
		frame = new JFrame();
		frame.setSize(500, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
		
		startBox2.setBounds(180, 160, 101, 27);
		frame.getContentPane().add(startBox2);
		
		endlabel.setBounds(330, 70, 61, 16);
		frame.getContentPane().add(endlabel);
		
		endBox1.setBounds(330, 110, 101, 27);
		frame.getContentPane().add(endBox1);
		
		endBox2.setBounds(330, 160, 101, 27);
		frame.getContentPane().add(endBox2);

		btnCompare.setBounds(180, 224, 117, 29);
		frame.getContentPane().add(btnCompare);
	}
	
	@SuppressWarnings("unused")
	private ArrayList<Date> getLastViableDate(String location, Date startDate) {
		// Guaranteed that location is present within the loadedData hashmap
		ArrayList<Date> viableDates = new ArrayList<Date>();
		Date nextDate = startDate;
		calendar.setTime(startDate);
		do {
			viableDates.add(nextDate);
			calendar.add(Calendar.MONTH, 1);
			nextDate = calendar.getTime();
		} while(loadedData.get(location).containsKey(nextDate));
		
		return viableDates;
		
	}
	
	private void populateEndDate(JComboBox<String> locbox, JComboBox<String> startbox, JComboBox<String> endbox, ActionEvent e) {
		String pickedLocation = locbox.getSelectedItem().toString();
//		Date pickedDate = startbox.getSelectedItem().toString().
	}
}
