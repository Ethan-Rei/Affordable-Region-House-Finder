package windows;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public abstract class InternalFrame extends Frame{
	protected JInternalFrame frame;
	
	public abstract void createFrame();
	public abstract void close();
	
	public InternalFrame() {
		init();
	}
	
	public void init() {
		frame = new JInternalFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setClosable(true);
		frame.addPropertyChangeListener("closed", e -> close());
	}
	
	public void setInternalWindowSettings(String title, int windowWidth, int windowHeight) {
		frame.setTitle(title);
		frame.setSize(windowWidth, windowHeight);
	}
	
	public JInternalFrame getFrame() {
		return frame;
	}
}
