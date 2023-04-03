package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import database.Database;

public class LoginWindow extends WindowFrame {
	private final String title = "Login";
	private final JLabel lblAppTitle = new JLabel("Affordable House Region Finder");
	private final JLabel lblLoginDesc = new JLabel("Login into the Database");
	private final JLabel lblLoginInfo = new JLabel("Login Information");
	private final JLabel lblDatabaseInfo = new JLabel("Database Information");
	private final JLabel lblUser = new JLabel("Username:");
	private final JLabel lblPass = new JLabel("Password:");
	private final JLabel lblIP = new JLabel("IP");
	private final JLabel lblPort = new JLabel("Port");
	private final JLabel lblSchema = new JLabel("Schema");
	private final JTextField userInput = new JTextField();
	private final JPasswordField passInput = new JPasswordField();
	private final JTextField ipInput = new JTextField();
	private final JTextField portInput = new JTextField();
	private final JTextField schemaInput = new JTextField();
	private final JSeparator sep = new JSeparator();
	private final JButton btnLogin = new JButton("Login");
	
	private final String loginError = "Error logging into database. Error given:\n";
	private final String missingSchema = "Please input the schema.";
	
	public LoginWindow () {
		createWindow();
	}
	
	@Override
	public void createWindow() {
		frame.setSize(320, 340);
		frame.setLocationRelativeTo(null);
		frame.setTitle(title);
		Font normalText = new Font("Dialog", 0, 11);
		
		lblAppTitle.setFont(new Font("Dialog", 1, 18));
		lblAppTitle.setBounds(12, 15, 300, 23);
		lblLoginDesc.setFont(new Font("Dialog", 0, 14));
		lblLoginDesc.setBounds(75, 45, 160, 18);
		
		lblLoginInfo.setFont(normalText);
		lblLoginInfo.setBounds(16, 80, 160, 14);
		lblUser.setFont(normalText);
		lblUser.setBounds(16, 105, 80, 14);
		userInput.setBounds(80, 105, 210, 18);
		userInput.setText(Database.loginDetails.getUsername());
		lblPass.setFont(normalText);
		lblPass.setBounds(16, 130, 80, 14);
		passInput.setBounds(80, 130, 210, 18);
		passInput.setText(Database.loginDetails.getPassword());
		
		sep.setBounds(0, 165, 320, 18);
		
		lblDatabaseInfo.setFont(normalText);
		lblDatabaseInfo.setBounds(16, 180, 160, 14);
		lblIP.setFont(normalText);
		lblIP.setBounds(16, 205, 20, 14);
		ipInput.setBounds(65, 205, 80, 18);
		ipInput.setText(Database.loginDetails.getIP());
		lblSchema.setFont(normalText);
		lblSchema.setBounds(16, 230, 50, 14);
		schemaInput.setBounds(65, 230, 80, 18);
		schemaInput.setText(Database.loginDetails.getSchema());
		lblPort.setFont(normalText);
		lblPort.setBounds(165, 205, 20, 14);
		portInput.setBounds(195, 205, 95, 18);
		portInput.setText(Database.loginDetails.getPort());
		
		btnLogin.setBounds(110, 260, 80, 27);
		btnLogin.addActionListener(e -> login(e));
		
		frame.getContentPane().add(lblAppTitle);
		frame.getContentPane().add(lblLoginDesc);
		frame.getContentPane().add(lblLoginInfo);
		frame.getContentPane().add(lblUser);
		frame.getContentPane().add(userInput);
		frame.getContentPane().add(lblPass);
		frame.getContentPane().add(passInput);
		frame.getContentPane().add(sep);
		frame.getContentPane().add(lblDatabaseInfo);
		frame.getContentPane().add(lblIP);
		frame.getContentPane().add(ipInput);
		frame.getContentPane().add(lblPort);
		frame.getContentPane().add(portInput);
		frame.getContentPane().add(lblSchema);
		frame.getContentPane().add(schemaInput);
		frame.getContentPane().add(btnLogin);
		
		frame.setVisible(true);
	}

	private void login(ActionEvent e) {
		// The schema MUST be inputted. Empty IP and Port will always have its own defaults if not entered.
		if (schemaInput.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, missingSchema, "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (ipInput.getText().isEmpty())
			ipInput.setText("127.0.0.1");
		if (portInput.getText().isEmpty())
			portInput.setText("3306");
		
		// Test connection and show error if fails, otherwise save the successful login details and getInstance()
		Database.loginDetails.setLoginDetails(userInput.getText(), String.valueOf(passInput.getPassword()), ipInput.getText(), portInput.getText(), schemaInput.getText());
		try {
			Database.testSQLConnection();
			Database.loginDetails.saveLoginDetails(userInput.getText(), String.valueOf(passInput.getPassword()), ipInput.getText(), portInput.getText(), schemaInput.getText());
			Database.getInstance();
			destroyWindow();
			
			new MainWindow();
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(null, loginError + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
