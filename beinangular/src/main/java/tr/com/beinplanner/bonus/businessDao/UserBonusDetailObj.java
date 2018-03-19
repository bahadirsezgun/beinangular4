package tr.com.beinplanner.bonus.businessDao;

import java.util.Date;
import java.util.List;

import tr.com.beinplanner.schedule.dao.ScheduleFactory;

public class UserBonusDetailObj {

	
	private Date 					planStartDate;
	private String					progName;
	private long  					schtId;
	private List<ScheduleFactory> 	scheduleFactories;
	private double					packetUnitPrice;
	private double					staffPaymentAmount;
	
	
	private double 					bonusValue;
	private int						bonusCount;
	private int						bonusType;
	private int						classCount;
	
	private int 					schCount;
	
	
	
	
	public Date getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}
	public String getProgName() {
		return progName;
	}
	public void setProgName(String progName) {
		this.progName = progName;
	}
	public long getSchtId() {
		return schtId;
	}
	public void setSchtId(long schtId) {
		this.schtId = schtId;
	}
	public List<ScheduleFactory> getScheduleFactories() {
		return scheduleFactories;
	}
	public void setScheduleFactories(List<ScheduleFactory> scheduleFactories) {
		this.scheduleFactories = scheduleFactories;
	}
	
	public int getBonusCount() {
		return bonusCount;
	}
	public void setBonusCount(int bonusCount) {
		this.bonusCount = bonusCount;
	}
	public int getBonusType() {
		return bonusType;
	}
	public void setBonusType(int bonusType) {
		this.bonusType = bonusType;
	}
	public double getBonusValue() {
		return bonusValue;
	}
	public void setBonusValue(double bonusValue) {
		this.bonusValue = bonusValue;
	}
	
	public double getPacketUnitPrice() {
		return packetUnitPrice;
	}
	public void setPacketUnitPrice(double packetUnitPrice) {
		this.packetUnitPrice = packetUnitPrice;
	}
	public double getStaffPaymentAmount() {
		return staffPaymentAmount;
	}
	public void setStaffPaymentAmount(double staffPaymentAmount) {
		this.staffPaymentAmount = staffPaymentAmount;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public int getSchCount() {
		return schCount;
	}
	public void setSchCount(int schCount) {
		this.schCount = schCount;
	}
	
	
	
	
	
}
