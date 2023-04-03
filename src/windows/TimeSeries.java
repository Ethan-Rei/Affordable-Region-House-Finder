package windows;

import java.util.Date;

public class TimeSeries {
	private String location;
	private Date dateStart;
	private Date dateEnd;
	
	public TimeSeries (String location, Date dateStart, Date dateEnd) {
		this.location = location;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
}
