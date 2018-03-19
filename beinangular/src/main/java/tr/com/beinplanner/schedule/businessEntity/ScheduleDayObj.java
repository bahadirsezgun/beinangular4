package tr.com.beinplanner.schedule.businessEntity;

import java.util.Date;

public class ScheduleDayObj {

	private String dayName;
	private String dayTime;
	private Date dayDate;
	
	
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getDayTime() {
		return dayTime;
	}
	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}
	public Date getDayDate() {
		return dayDate;
	}
	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}
	
	
}
