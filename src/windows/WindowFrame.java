package windows;

import javax.swing.JFrame;

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
