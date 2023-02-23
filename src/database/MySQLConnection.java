package database;

import java.io.File;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class MySQLConnection implements DatabaseConnection {
    private Connection connection;
    
    // Change these as needed
    private String loginFile = "login.txt";
    private String ip;
    private String port;
    private String schema;
    private String username;
    private String password;
    private String address;
	
	public MySQLConnection () {
		try {
			// get sql login details from login.txt
			File login = new File(loginFile);
			Scanner fileReader = new Scanner(login);
			ip = fileReader.nextLine();
			schema = fileReader.nextLine();
			port = fileReader.nextLine();
			username = fileReader.nextLine();
			password = fileReader.nextLine();
			address = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
			fileReader.close();
			
			connection = DriverManager.getConnection(address, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
	
}
