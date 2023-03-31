package windows;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ValuePrediction {

	private JFrame ValuePrediction;
	private final JLabel ValuePredictionLabel = new JLabel("Value Prediction");
	private final JLabel algorithmlbl = new JLabel("Choose an algorithm:");
	private final JRadioButton linearrd = new JRadioButton("Linear Regression");
	private final JRadioButton arimard = new JRadioButton("ARIMA");
	private final JRadioButton holtWinterrd = new JRadioButton("Holt-Winters");
	private final JLabel lblAmountOfMonths = new JLabel("Amount of months");
	private final JComboBox monthbx = new JComboBox();
	private final JLabel lblIterations = new JLabel("Iterations ");
	private final JTextField iterationtxt = new JTextField();
	private final JLabel lblEpochs = new JLabel("Epochs");
	private final JTextField epochstxt = new JTextField();
	private final JLabel lblConvergence = new JLabel("Convergence");
	private final JTextField convergencetxt = new JTextField();
	private final JButton btnPredict = new JButton("Predict");
	private final JButton btnCancel = new JButton("Cancel");

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
		iterationtxt.setBounds(17, 262, 130, 26);
		iterationtxt.setColumns(10);
		ValuePrediction = new JFrame();
		ValuePrediction.setBounds(100, 100, 550, 400);
		ValuePrediction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ValuePrediction.getContentPane().setLayout(null);
		ValuePredictionLabel.setBounds(192, 21, 140, 16);
		
		ValuePrediction.getContentPane().add(ValuePredictionLabel);
		algorithmlbl.setBounds(172, 49, 180, 16);
		
		ValuePrediction.getContentPane().add(algorithmlbl);
		linearrd.setBounds(19, 92, 169, 23);
		
		ValuePrediction.getContentPane().add(linearrd);
		arimard.setBounds(211, 92, 141, 23);
		
		ValuePrediction.getContentPane().add(arimard);
		holtWinterrd.setBounds(335, 92, 141, 23);
		
		ValuePrediction.getContentPane().add(holtWinterrd);
		lblAmountOfMonths.setBounds(172, 152, 180, 16);
		
		ValuePrediction.getContentPane().add(lblAmountOfMonths);
		monthbx.setBounds(172, 180, 138, 27);
		
		ValuePrediction.getContentPane().add(monthbx);
		lblIterations.setBounds(23, 235, 140, 16);
		
		ValuePrediction.getContentPane().add(lblIterations);
		
		ValuePrediction.getContentPane().add(iterationtxt);
		lblEpochs.setBounds(172, 235, 140, 16);
		
		ValuePrediction.getContentPane().add(lblEpochs);
		epochstxt.setColumns(10);
		epochstxt.setBounds(180, 262, 130, 26);
		
		ValuePrediction.getContentPane().add(epochstxt);
		lblConvergence.setBounds(367, 235, 140, 16);
		
		ValuePrediction.getContentPane().add(lblConvergence);
		convergencetxt.setColumns(10);
		convergencetxt.setBounds(361, 262, 130, 26);
		
		ValuePrediction.getContentPane().add(convergencetxt);
		btnPredict.setBounds(190, 300, 117, 29);
		
		ValuePrediction.getContentPane().add(btnPredict);
		btnCancel.setBounds(192, 337, 117, 29);
		
		ValuePrediction.getContentPane().add(btnCancel);
	}

}
