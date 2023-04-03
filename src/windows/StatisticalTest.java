package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatisticalTest {

	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("Statstical Test");
	private final JLabel loclabel = new JLabel("Location");
	private final JLabel startlabel = new JLabel("Start Date");
	private final JLabel endlabel = new JLabel("End Date");
	private final JComboBox locBox1 = new JComboBox();
	private final JComboBox locBox2 = new JComboBox();
	private final JComboBox startBox1 = new JComboBox();
	private final JComboBox startBox2 = new JComboBox();
	private final JComboBox endBox1 = new JComboBox();
	private final JComboBox endBox2 = new JComboBox();
	private final JButton btnCompare = new JButton("Compare");

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
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
}
