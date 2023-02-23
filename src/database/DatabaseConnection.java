package database;

import java.sql.Connection;

interface DatabaseConnection {
	public Connection getConnection();
}
