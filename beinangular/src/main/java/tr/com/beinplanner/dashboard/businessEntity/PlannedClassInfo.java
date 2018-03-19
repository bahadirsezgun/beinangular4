package tr.com.beinplanner.dashboard.businessEntity;

public class PlannedClassInfo {

	

	private long classCount;
	private String monthName;
	private int month;
	
	private String staffName;
	private long staffId;
	
	
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	public long getClassCount() {
		return classCount;
	}
	public void setClassCount(long classCount) {
		this.classCount = classCount;
	}
	
	
	
	
}
