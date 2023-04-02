package windows;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class WindowFrame {
	protected JFrame frame;
	
	public WindowFrame() {
		frame = new JFrame();
		frame.setResizable(false);
	}
	
	public abstract void createWindow();
	
	public void destroyWindow() {
		frame.dispose();
	}
}
