package tr.com.beinplanner.bonus.businessDao;

import java.util.Date;

public class UserBonusSearchObj {

	private long schStaffId;
	
	private int queryType;
	
	private int month;
	private int year;
	
	private Date startDate;
	private Date endDate;
	
	
	
	public long getSchStaffId() {
		return schStaffId;
	}
	public void setSchStaffId(long schStaffId) {
		this.schStaffId = schStaffId;
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
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	
	
	
	
}
