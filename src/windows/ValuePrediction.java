package windows;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class ValuePrediction {

	private JFrame ValuePrediction;
	private final JLabel ValuePredictionLabel = new JLabel("Value Prediction");
	private final JLabel algorithmlbl = new JLabel("Choose an algorithm:");
	private final JRadioButton linearrd = new JRadioButton("Linear Regression");
	private final JRadioButton gaussianrd = new JRadioButton("Gaussian Process");
	private final ButtonGroup algoGrp = new ButtonGroup();
	private final JLabel lblAmountOfMonths = new JLabel("Amount of months");
	private final JComboBox<String> monthbx = new JComboBox<String>();
	private final JLabel lblIterations = new JLabel("Iterations ");
	private final JTextField iterationtxt = new JTextField();
	private final JLabel lblEpochs = new JLabel("Epochs");
	private final JTextField epochstxt = new JTextField();
	private final JLabel lblConvergence = new JLabel("Convergence");
	private final JTextField convergencetxt = new JTextField();
	private final JButton btnPredict = new JButton("Predict");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ValuePrediction window = new ValuePrediction();
					window.ValuePrediction.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ValuePrediction() {
		initialize();
		btnPredict.addActionListener (new ActionListener (){
			 public void actionPerformed (ActionEvent e) {
				 // To implement
				 System.exit(0);
			 }
			});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		iterationtxt.setBounds(17, 262, 130, 26);
		iterationtxt.setColumns(10);
		ValuePrediction = new JFrame();
		ValuePrediction.setSize(450, 400);
		ValuePrediction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ValuePrediction.getContentPane().setLayout(null);
		ValuePredictionLabel.setBounds(168, 21, 140, 16);
		ValuePrediction.getContentPane().add(ValuePredictionLabel);
		
		algorithmlbl.setBounds(160, 49, 180, 16);
		ValuePrediction.getContentPane().add(algorithmlbl);
		
		linearrd.setBounds(50, 92, 169, 23);
		ValuePrediction.getContentPane().add(linearrd);
		
		gaussianrd.setBounds(250, 92, 141, 23);
		ValuePrediction.getContentPane().add(gaussianrd);
		
		algoGrp.add(gaussianrd);
		algoGrp.add(linearrd);
		
		lblAmountOfMonths.setBounds(165, 152, 180, 16);
		ValuePrediction.getContentPane().add(lblAmountOfMonths);
		
		monthbx.setBounds(150, 180, 138, 27);
		ValuePrediction.getContentPane().add(monthbx);
		String[] cmbBoxValues = getCmbBoxValues();
		for (String month: cmbBoxValues) {
			monthbx.addItem(month);
		}
		monthbx.setSelectedItem("1");
		
		btnPredict.setBounds(160, 270, 117, 29);
		ValuePrediction.getContentPane().add(btnPredict);
		
		ValuePrediction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private String[] getCmbBoxValues() {
		String[] strValue = new String[12];
		
		for (int i = 1; i <= 12; i++) {
			strValue[i-1] = Integer.toString(i);
		}
		return strValue;
	}

}
