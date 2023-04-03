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
	private final JLabel Time1label = new JLabel("Time 1");
	private final JLabel Time2label = new JLabel("Time 2");
	private final JComboBox comboBox = new JComboBox();
	private final JComboBox comboBox_1 = new JComboBox();
	private final JButton btnCancel = new JButton("Cancel");
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		lblNewLabel.setBounds(25, 43, 93, 23);
		
		frame.getContentPane().add(lblNewLabel);
		Time1label.setBounds(101, 90, 61, 16);
		
		frame.getContentPane().add(Time1label);
		Time2label.setBounds(101, 158, 61, 16);
		
		frame.getContentPane().add(Time2label);
		comboBox.setBounds(267, 86, 101, 27);
		
		frame.getContentPane().add(comboBox);
		comboBox_1.setBounds(267, 154, 101, 27);
		
		frame.getContentPane().add(comboBox_1);
		btnCancel.setBounds(267, 224, 117, 29);
		
		frame.getContentPane().add(btnCancel);
		btnCompare.setBounds(45, 224, 117, 29);
		
		frame.getContentPane().add(btnCompare);
	}
}
