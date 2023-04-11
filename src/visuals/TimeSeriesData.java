package visuals;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import database.Database;
import windows.WindowFrame;

public class TimeSeriesData {
	private String location;
	private String startDate;
	private String endDate;
	private HashMap<ChartType, Boolean> chartSettings;
	private HashMap<Date, Double> loadedData;
	
	public TimeSeriesData(String location, String startDate, String endDate) {
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		setDefaultSettings();
		setLoadedData(Database.getInstance().getQuery().getNHPI(location, startDate, endDate));
	}
	
	public TimeSeriesData(TimeSeriesData timeSeries) {
		// deep copy
		this.location = new String(timeSeries.getLocation());
		this.startDate = new String(timeSeries.getStartDate());
		this.endDate = new String(timeSeries.getEndDate());
		
		HashMap<Date, Double> copy = new HashMap<>();
		for (Map.Entry<Date, Double> entry: timeSeries.loadedData.entrySet()) {
			copy.put(new Date(entry.getKey().getTime()), Double.valueOf(entry.getValue()));
		}
		
		setLoadedData(copy);
	}
	
	public TimeSeriesData() {
		
	}

	private void setDefaultSettings() {
		chartSettings = new HashMap<>();
		chartSettings.put(ChartType.LINE_CHART, false);
		chartSettings.put(ChartType.PLOT_CHART, false);
		chartSettings.put(ChartType.HISTOGRAM_CHART, false);
		chartSettings.put(ChartType.STACKED_AREA_CHART, false);
	}
	
	public void setSetting(ChartType type, boolean setting) {
		chartSettings.put(type, setting);
	}
	
	public boolean getSetting(ChartType type) {
		return chartSettings.get(type);
	}
	
	public int getSettingsCount() {
		int count = 0;
		if (chartSettings.get(ChartType.LINE_CHART))
			count++;
		if (chartSettings.get(ChartType.PLOT_CHART))
			count++;
		if (chartSettings.get(ChartType.HISTOGRAM_CHART))
			count++;
		if (chartSettings.get(ChartType.STACKED_AREA_CHART))
			count++;
		return count;
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
	
	public Date getStartDateAsDate() {
		try {
			return WindowFrame.dateFormat.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = WindowFrame.dateFormat.format(startDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = WindowFrame.dateFormat.format(endDate);
	}
	
	public Date getEndDateAsDate() {
		try {
			return WindowFrame.dateFormat.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public HashMap<Date, Double> getLoadedData() {
		return loadedData;
	}
	
	public void setLoadedData(HashMap<Date, Double> loadedData) {
		this.loadedData = loadedData;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String toString() {
		return location + " (" + startDate + " to " + endDate + ")";
	}
	
	public boolean equals(TimeSeriesData otherSeries) {
		return this.startDate.equals(otherSeries.getStartDate()) && this.endDate.equals(otherSeries.getEndDate()) && this.location.equals(otherSeries.getLocation());
	}
}