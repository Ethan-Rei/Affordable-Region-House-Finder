package windows;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class TimeSeriesEditorWindow extends InternalFrame {
	
	private final String title = "Chart Editor";
	private final JButton btnAdd = new JButton("Add time series to chart");
	private final JComboBox<String> boxChart1 = new JComboBox<>();
	private final JComboBox<String> boxChart2 = new JComboBox<>();
	private final JLabel lblModify = new JLabel("Select time series chart to change:");
	private final JLabel lblFrom = new JLabel("Select time series to add to the chart:");
	
	private final JButton ref;
	
	public static void main(String[] args) {
		new TimeSeriesEditorWindow(null);
	}
	
	public TimeSeriesEditorWindow(JButton ref) {
		this.ref = ref;
		createFrame();
	}
	
	private void createFrame() {
		frame.setSize(280, 300);
		frame.setTitle(title);
		
		lblModify.setBounds(20, 20, 200, 13);
		frame.getContentPane().add(lblModify);
		
		boxChart1.setBounds(20, 50, 220, 27);
		frame.getContentPane().add(boxChart1);
		
		lblFrom.setBounds(20, 107, 250, 13);
		frame.getContentPane().add(lblFrom);
		
		boxChart2.setBounds(20, 137, 220, 27);
		frame.getContentPane().add(boxChart2);
		
		btnAdd.setBounds(45, 200, 170, 27);
		frame.getContentPane().add(btnAdd);
		
		frame.setVisible(true);
	}
	
	public void close() {
		ref.setEnabled(true);
	}
	
}
