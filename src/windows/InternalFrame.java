package windows;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public abstract class InternalFrame {
	protected JInternalFrame frame;
	
	public InternalFrame() {
		frame = new JInternalFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setClosable(true);
		frame.addPropertyChangeListener("closed", e -> close());
	}
	
	public abstract void close();
	
	public JInternalFrame getFrame() {
		return frame;
	}
}
