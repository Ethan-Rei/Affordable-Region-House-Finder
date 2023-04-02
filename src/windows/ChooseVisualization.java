package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseVisualization {

	private JFrame frame;
	private final JLabel chooselabel = new JLabel("Choose Visualization");
	private final JCheckBox Graohchkbx = new JCheckBox("Graph ");
	private final JCheckBox Tablechkbx = new JCheckBox("Table");
	private final JCheckBox chckbxPieChart = new JCheckBox("Pie Chart");
	private final JCheckBox chckbxPlotGraph = new JCheckBox("Plot Graph");
	private final JButton btnUpdate = new JButton("Update Visualization ");
	private final JButton btnCancel = new JButton("Cancel");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseVisualization window = new ChooseVisualization();
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
	public ChooseVisualization() {
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		chooselabel.setBounds(16, 24, 149, 16);
		
		frame.getContentPane().add(chooselabel);
		Graohchkbx.setBounds(16, 63, 128, 23);
		
		frame.getContentPane().add(Graohchkbx);
		Tablechkbx.setBounds(222, 63, 128, 23);
		
		frame.getContentPane().add(Tablechkbx);
		chckbxPieChart.setBounds(16, 146, 128, 23);
		
		frame.getContentPane().add(chckbxPieChart);
		chckbxPlotGraph.setActionCommand("");
		chckbxPlotGraph.setBounds(222, 146, 128, 23);
		
		frame.getContentPane().add(chckbxPlotGraph);
		btnUpdate.setBounds(16, 213, 177, 37);
		
		frame.getContentPane().add(btnUpdate);
		btnCancel.setBounds(240, 213, 86, 37);
		
		frame.getContentPane().add(btnCancel);
	}
}