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

public class ChooseVisualization extends InternalFrame{
	private final JLabel chooselabel = new JLabel("Choose Visualization");
	private final JCheckBox Graohchkbx = new JCheckBox("Graph ");
	private final JCheckBox Tablechkbx = new JCheckBox("Table");
	private final JCheckBox chckbxPlotGraph = new JCheckBox("Plot Graph");
	private final JButton btnUpdate = new JButton("Update Visualization ");
	private final JButton btnCancel = new JButton("Cancel");

	/**
	 * Create the application.
	 */
	public ChooseVisualization() {
		createFrame();
	}

	private void createFrame() {
		frame.setBounds(100, 100, 450, 300);
		chooselabel.setBounds(16, 24, 149, 16);
		frame.getContentPane().add(chooselabel);
		
		Graohchkbx.setBounds(16, 63, 128, 23);
		frame.getContentPane().add(Graohchkbx);
		
		Tablechkbx.setBounds(222, 63, 128, 23);
		frame.getContentPane().add(Tablechkbx);
		
		chckbxPlotGraph.setActionCommand("");
		chckbxPlotGraph.setBounds(222, 146, 128, 23);
		frame.getContentPane().add(chckbxPlotGraph);
		
		btnUpdate.setBounds(16, 213, 177, 37);
		frame.getContentPane().add(btnUpdate);
		
		btnCancel.setBounds(240, 213, 86, 37);
		frame.getContentPane().add(btnCancel);
		frame.setVisible(true);
	}
	
	public void close() {
		MainWindow.getInstance().getBtnVisualize().setEnabled(true);
	}
}
