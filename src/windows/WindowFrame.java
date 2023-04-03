package windows;

import javax.swing.JFrame;

public abstract class WindowFrame {
	protected JFrame frame;
	
	public WindowFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	public abstract void createWindow();
	
	public void destroyWindow() {
		frame.dispose();
	}
}
