package windows;

import java.util.HashMap;

public class TimeSeries {
	private String location;
	private String startDate;
	private String endDate;
	private HashMap<ChartType, Boolean> chartSettings;
	
	public TimeSeries(String location, String startDate, String endDate) {
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		setDefaultSettings();
	}
	
	private void setDefaultSettings() {
		chartSettings = new HashMap<>();
		chartSettings.put(ChartType.LINE_CHART, false);
		chartSettings.put(ChartType.PLOT_CHART, false);
		chartSettings.put(ChartType.STACKED_AREA_CHART, false);
		chartSettings.put(ChartType.HISTOGRAM_CHART, false);
	}
	
	public void setSetting(ChartType type, boolean setting) {
		chartSettings.put(type, setting);
	}
	
	public boolean getSetting(ChartType type) {
		return chartSettings.get(type);
	}
	
	public boolean isChartEnabled(ChartType type) {
		return chartSettings.get(type);
	}

	public String getLocation() {
		return location;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public String toString() {
		return location + " (" + startDate + " to " + endDate + ")";
	}
	
	public boolean equals(TimeSeries otherSeries) {
		return this.startDate.equals(otherSeries.getStartDate()) && this.endDate.equals(otherSeries.getEndDate()) && this.location.equals(otherSeries.getLocation());
	}
}