package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import database.Database;

public class LoginWindow extends WindowFrame {
	private final String title = "Login";
	private final JLabel appTitle = new JLabel("Affordable House Region Finder");
	private final JLabel loginDesc = new JLabel("Login into the Database");
	private final JLabel loginInfo = new JLabel("Login Information");
	private final JLabel databaseInfo = new JLabel("Database Information");
	private final JLabel user = new JLabel("Username:");
	private final JLabel pass = new JLabel("Password:");
	private final JLabel ip = new JLabel("IP");
	private final JLabel port = new JLabel("Port");
	private final JLabel schema = new JLabel("Schema");
	private final JTextField userInput = new JTextField();
	private final JPasswordField passInput = new JPasswordField();
	private final JTextField ipInput = new JTextField();
	private final JTextField portInput = new JTextField();
	private final JTextField schemaInput = new JTextField();
	private final JSeparator sep = new JSeparator();
	private final JButton login = new JButton("Login");
	
	private final String loginError = "Error logging into database. Error given:\n";
	private final String missingSchema = "Please input the schema.";
	
	public LoginWindow () {
		Database.loginDetails.checkLoginFile();
		Database.loginDetails.loadLoginDetails();
		createWindow();
	}
	
	@Override
	public void createWindow() {
		frame.setSize(320, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(title);
		Font normalText = new Font("Dialog", 0, 11);
		
		appTitle.setFont(new Font("Dialog", 1, 18));
		appTitle.setBounds(12, 15, 300, 23);
		loginDesc.setFont(new Font("Dialog", 0, 14));
		loginDesc.setBounds(75, 45, 160, 18);
		
		loginInfo.setFont(normalText);
		loginInfo.setBounds(16, 80, 160, 14);
		user.setFont(normalText);
		user.setBounds(16, 105, 80, 14);
		userInput.setBounds(80, 105, 210, 18);
		userInput.setText(Database.loginDetails.getUsername());
		pass.setFont(normalText);
		pass.setBounds(16, 130, 80, 14);
		passInput.setBounds(80, 130, 210, 18);
		passInput.setText(Database.loginDetails.getPassword());
		
		sep.setBounds(0, 165, 320, 18);
		
		databaseInfo.setFont(normalText);
		databaseInfo.setBounds(16, 180, 160, 14);
		ip.setFont(normalText);
		ip.setBounds(16, 205, 20, 14);
		ipInput.setBounds(65, 205, 80, 18);
		ipInput.setText(Database.loginDetails.getIP());
		schema.setFont(normalText);
		schema.setBounds(16, 230, 50, 14);
		schemaInput.setBounds(65, 230, 80, 18);
		schemaInput.setText(Database.loginDetails.getSchema());
		port.setFont(normalText);
		port.setBounds(165, 205, 20, 14);
		portInput.setBounds(195, 205, 95, 18);
		portInput.setText(Database.loginDetails.getPort());
		
		login.setBounds(110, 260, 80, 27);
		login.addActionListener(e -> login(e));
		
		frame.getContentPane().add(appTitle);
		frame.getContentPane().add(loginDesc);
		frame.getContentPane().add(loginInfo);
		frame.getContentPane().add(user);
		frame.getContentPane().add(userInput);
		frame.getContentPane().add(pass);
		frame.getContentPane().add(passInput);
		frame.getContentPane().add(sep);
		frame.getContentPane().add(databaseInfo);
		frame.getContentPane().add(ip);
		frame.getContentPane().add(ipInput);
		frame.getContentPane().add(port);
		frame.getContentPane().add(portInput);
		frame.getContentPane().add(schema);
		frame.getContentPane().add(schemaInput);
		frame.getContentPane().add(login);
		
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
