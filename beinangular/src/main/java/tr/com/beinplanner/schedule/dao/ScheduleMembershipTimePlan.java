package tr.com.beinplanner.schedule.dao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="schedule_membership_time_plan")
public class ScheduleMembershipTimePlan {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SMTP_ID")
	private long 	smtpId;
	
	@Column(name="SMP_ID")
	private long 	smpId;
	
	@Column(name="SMP_START_DATE")
	private Date 	smpStartDate;
	
	@Column(name="SMP_END_DATE")
	private Date 	smpEndDate;
	@Column(name="SMP_COMMENT")
	private String 	smpComment;
	
	@Transient
    private ScheduleMembershipPlan scheduleMembershipPlan;
	
	
	@Transient
	private String 	smpStartDateStr;
	@Transient
	private String 	smpEndDateStr;
	@Transient
	private String smpStartDayName;
	@Transient
	private String smpEndDayName;
	@Transient
	private String smpStartDayTime;
	@Transient
	private String smpEndDayTime;
	@Transient
	private boolean first=false;
	
	@Transient
	private String smpStatusStr;
	@Transient
	private String smpFreezeEndDateStr;
	@Transient
	private String smpFreezeEndDayName;
	
	
	public long getSmtpId() {
		return smtpId;
	}
	public void setSmtpId(long smtpId) {
		this.smtpId = smtpId;
	}
	public long getSmpId() {
		return smpId;
	}
	public void setSmpId(long smpId) {
		this.smpId = smpId;
	}
	public Date getSmpStartDate() {
		return smpStartDate;
	}
	public void setSmpStartDate(Date smpStartDate) {
		this.smpStartDate = smpStartDate;
	}
	public Date getSmpEndDate() {
		return smpEndDate;
	}
	public void setSmpEndDate(Date smpEndDate) {
		this.smpEndDate = smpEndDate;
	}
	public String getSmpStartDateStr() {
		return smpStartDateStr;
	}
	public void setSmpStartDateStr(String smpStartDateStr) {
		this.smpStartDateStr = smpStartDateStr;
	}
	public String getSmpEndDateStr() {
		return smpEndDateStr;
	}
	public void setSmpEndDateStr(String smpEndDateStr) {
		this.smpEndDateStr = smpEndDateStr;
	}
	public String getSmpComment() {
		return smpComment;
	}
	public void setSmpComment(String smpComment) {
		this.smpComment = smpComment;
	}
	public String getSmpStartDayName() {
		return smpStartDayName;
	}
	public void setSmpStartDayName(String smpStartDayName) {
		this.smpStartDayName = smpStartDayName;
	}
	public String getSmpEndDayName() {
		return smpEndDayName;
	}
	public void setSmpEndDayName(String smpEndDayName) {
		this.smpEndDayName = smpEndDayName;
	}
	public String getSmpStartDayTime() {
		return smpStartDayTime;
	}
	public void setSmpStartDayTime(String smpStartDayTime) {
		this.smpStartDayTime = smpStartDayTime;
	}
	public String getSmpEndDayTime() {
		return smpEndDayTime;
	}
	public void setSmpEndDayTime(String smpEndDayTime) {
		this.smpEndDayTime = smpEndDayTime;
	}
	public String getSmpStatusStr() {
		return smpStatusStr;
	}
	public void setSmpStatusStr(String smpStatusStr) {
		this.smpStatusStr = smpStatusStr;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public String getSmpFreezeEndDateStr() {
		return smpFreezeEndDateStr;
	}
	public void setSmpFreezeEndDateStr(String smpFreezeEndDateStr) {
		this.smpFreezeEndDateStr = smpFreezeEndDateStr;
	}
	public String getSmpFreezeEndDayName() {
		return smpFreezeEndDayName;
	}
	public void setSmpFreezeEndDayName(String smpFreezeEndDayName) {
		this.smpFreezeEndDayName = smpFreezeEndDayName;
	}
	

	public ScheduleMembershipPlan getScheduleMembershipPlan() {
		return scheduleMembershipPlan;
	}
	public void setScheduleMembershipPlan(ScheduleMembershipPlan scheduleMembershipPlan) {
		this.scheduleMembershipPlan = scheduleMembershipPlan;
	}
	
}
