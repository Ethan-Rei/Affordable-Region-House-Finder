package windows;

import javax.swing.JFrame;

public abstract class WindowFrame {
	protected JFrame frame;
	
	public abstract void createWindow();
	
	public WindowFrame() {
		init();
	}
	
	public void init() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	public void destroyWindow() {
		frame.dispose();
	}
	
	public void setWindowSettings(String title, int windowWidth, int windowHeight) {
		frame.setTitle(title);
		frame.setSize(windowWidth, windowHeight);
		frame.setLocationRelativeTo(null);
	}
}
