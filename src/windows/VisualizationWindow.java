package windows;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class VisualizationWindow extends InternalFrame {
	private final JLabel chooselabel = new JLabel("Choose Visualization");
	private final JCheckBox linebox = new JCheckBox("Line Chart");
	private final JCheckBox stackbox = new JCheckBox("Stacked Chart");
	private final JCheckBox scatterbox = new JCheckBox("Scatter Plot");
	private final JCheckBox histobox = new JCheckBox("Scatter Plot");
	private final JButton btnUpdate = new JButton("Update Visualization");
	private final JButton btnCancel = new JButton("Edit time series charts");

	/**
	 * Create the application.
	 */
	public VisualizationWindow() {
		createFrame();
	}

	private void createFrame() {
		frame.setBounds(100, 100, 450, 300);
		chooselabel.setBounds(16, 24, 149, 16);
		frame.getContentPane().add(chooselabel);
		
		linebox.setBounds(16, 63, 128, 23);
		frame.getContentPane().add(linebox);
		
		histobox.setBounds(16, 146, 128, 23);
		frame.getContentPane().add(histobox);
		
		stackbox.setBounds(222, 63, 128, 23);
		frame.getContentPane().add(stackbox);
		
		scatterbox.setBounds(222, 146, 128, 23);
		frame.getContentPane().add(scatterbox);
		
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
