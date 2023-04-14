package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class DatabaseLogin {
	public static final String defaultIP = "127.0.0.1";
	public static final String defaultPort = "3306";
	public static final String defaultSchema = "nhpi";
	
    private String username;
    private String password;
    private String ip;
    private String port;
    private String schema;
    
    private final String fileCreateError = "Error on trying to create login.txt. Error given:\n";
	private final String fileWriteError = "Error on trying to write to login.txt. Error given:\n";
	private File loginFile;
	
	public DatabaseLogin(String filename) {
		loginFile = new File(filename);
		checkLoginFile();
		loadLoginDetails();
	}

	public void checkLoginFile() {
		// Check for login file and set the parameters
		try {
			Scanner fileReader = new Scanner(loginFile);
			fileReader.close();
		} catch (FileNotFoundException e) {
			// Attempt to create the login.txt if it does not exist
			createLoginFile();
		}
	}
	
	public void createLoginFile() {
		try {
			loginFile.createNewFile();
			saveLoginDetails("", "", defaultIP, defaultPort, defaultSchema);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, fileCreateError + e.getMessage() + "\nClosing.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void saveLoginDetails(String username, String password, String ip, String port, String schema) {
		try {
			PrintWriter writer = new PrintWriter(loginFile);
			writer.println(username);
			writer.println(password);
			writer.println(ip);
			writer.println(port);
			writer.println(schema);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, fileWriteError + e.getMessage() + "\nClosing.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void loadLoginDetails() {
		try {
			Scanner fileReader = new Scanner(loginFile);
			username = fileReader.nextLine();
			password = fileReader.nextLine();
			ip = fileReader.nextLine();
			port = fileReader.nextLine();
			schema = fileReader.nextLine();
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException ex) {
			saveLoginDetails("", "", defaultIP, defaultPort, defaultSchema); // remakes login.txt if it had less than 5 lines or some unknown error
			loadLoginDetails(); // attempt to reload
		}
	}
	
	public void setLoginDetails(String username, String password, String ip, String port, String schema) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.schema = schema;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getIP() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public String getSchema() {
		return schema;
	}
}
