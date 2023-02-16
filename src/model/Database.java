package model;

import java.sql.ResultSet;

public interface Database {
	public ResultSet query(String query);
	public void importData(String path);
}
