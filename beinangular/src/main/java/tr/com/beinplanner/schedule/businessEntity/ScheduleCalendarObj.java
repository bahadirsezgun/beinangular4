package tr.com.beinplanner.schedule.businessEntity;

import java.util.Date;

public class ScheduleCalendarObj {

	private Date calendarDate;
	private String calendarDateName;
	private long staffId;
	private int dayDuration;
	private int actualSize;
	
	
	
	public Date getCalendarDate() {
		return calendarDate;
	}
	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
	}
	public String getCalendarDateName() {
		return calendarDateName;
	}
	public void setCalendarDateName(String calendarDateName) {
		this.calendarDateName = calendarDateName;
	}
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	
	public int getDayDuration() {
		return dayDuration;
	}
	public void setDayDuration(int dayDuration) {
		this.dayDuration = dayDuration;
	}
	public int getActualSize() {
		return actualSize;
	}
	public void setActualSize(int actualSize) {
		this.actualSize = actualSize;
	}
	
	
	
	
	
	
}
