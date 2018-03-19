package tr.com.beinplanner.schedule.businessEntity;

import java.util.Date;
import java.util.List;

import tr.com.beinplanner.schedule.dao.ScheduleFactory;

public class StaffClassPlans {

	private long 	saleId;
	private long 	schId;
	private long 	schtId;
	private String 	progName;
	private Date 	planStartDate;
	private String 	planStartDateStr;
	private String 	planStartTimeStr;
	private int 	schCount;  //Planlamaya ait sayı
	private int 	progCount; // Üyenin aldığı ders sayısı
	private String  userName;
	private String  userSurname;
	private String 	colorOfLine;
	private String 	colorOfLineFont;
	private String planDay;
	private int statuTp;
	
	private List<ScheduleFactory> schf;
	
	
	public List<ScheduleFactory> getSchf() {
		return schf;
	}
	public void setSchf(List<ScheduleFactory> schf) {
		this.schf = schf;
	}
	public long getSaleId() {
		return saleId;
	}
	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}
	public long getSchId() {
		return schId;
	}
	public void setSchId(long schId) {
		this.schId = schId;
	}
	public long getSchtId() {
		return schtId;
	}
	public void setSchtId(long schtId) {
		this.schtId = schtId;
	}
	public String getProgName() {
		return progName;
	}
	public void setProgName(String progName) {
		this.progName = progName;
	}
	public Date getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}
	
	public int getSchCount() {
		return schCount;
	}
	public void setSchCount(int schCount) {
		this.schCount = schCount;
	}
	public int getProgCount() {
		return progCount;
	}
	public void setProgCount(int progCount) {
		this.progCount = progCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSurname() {
		return userSurname;
	}
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	public String getColorOfLine() {
		return colorOfLine;
	}
	public void setColorOfLine(String colorOfLine) {
		this.colorOfLine = colorOfLine;
	}
	public String getPlanStartDateStr() {
		return planStartDateStr;
	}
	public void setPlanStartDateStr(String planStartDateStr) {
		this.planStartDateStr = planStartDateStr;
	}
	public String getPlanStartTimeStr() {
		return planStartTimeStr;
	}
	public void setPlanStartTimeStr(String planStartTimeStr) {
		this.planStartTimeStr = planStartTimeStr;
	}
	public String getColorOfLineFont() {
		return colorOfLineFont;
	}
	public void setColorOfLineFont(String colorOfLineFont) {
		this.colorOfLineFont = colorOfLineFont;
	}
	public String getPlanDay() {
		return planDay;
	}
	public void setPlanDay(String planDay) {
		this.planDay = planDay;
	}
	public int getStatuTp() {
		return statuTp;
	}
	public void setStatuTp(int statuTp) {
		this.statuTp = statuTp;
	}
	
	
	
	
	
	
}
