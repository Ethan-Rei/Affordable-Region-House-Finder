package windows;

import java.util.HashMap;

public class TimeSeries {
	private String location;
	private String startDate;
	private String endDate;
	private HashMap<String, Boolean> chartSettings = new HashMap<>();
	
	public TimeSeries(String location, String startDate, String endDate) {
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	
	
	
}
