package model;

import java.sql.ResultSet;

public interface Database {
	public ResultSet query(String query);
	public boolean importData(String path, String table);
	public boolean removeData(String table);
}
