package tr.com.beinplanner.schedule.businessEntity;

import java.util.Date;

public class ScheduleSearchObj {

	private Date startDate;
	private Date endDate;
	private String startDateStr;
	private String endDateStr;
	private long staffId;
	
	private int month;
	private int year;
	private int typeOfSchedule;
	private int queryType;
	
	
	
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTypeOfSchedule() {
		return typeOfSchedule;
	}
	public void setTypeOfSchedule(int typeOfSchedule) {
		this.typeOfSchedule = typeOfSchedule;
	}
	
	
}
