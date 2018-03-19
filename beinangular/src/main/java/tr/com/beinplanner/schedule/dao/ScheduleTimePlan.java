package tr.com.beinplanner.schedule.dao;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tr.com.beinplanner.program.dao.ProgramFactory;
import tr.com.beinplanner.schedule.businessEntity.PeriodicTimePlan;
import tr.com.beinplanner.user.dao.User;
@Entity
@Table(name="schedule_time_plan")
public class ScheduleTimePlan implements Cloneable{

	
	@Override
	public Object clone()  {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SCHT_ID")
	private long schtId;
	
	@Column(name="SCH_ID")
	private long schId;

	@Column(name="PLAN_START_DATE")
	private Date planStartDate;

	@Column(name="PLAN_END_DATE")
	private Date planEndDate;
	
	
	@Column(name="STATUTP")
	private int statuTp;
	
	@Column(name="SCHT_STAFF_ID")
	private long schtStaffId;
	
	@Column(name="TP_COMMENT")
	private String tpComment;
	

	@Transient
	private SchedulePlan schedulePlan;
	
	
	@Transient
	private List<ScheduleFactory>  scheduleFactories;
	
		
	@Transient
	private ProgramFactory programFactory;
	
	@Transient
	private int period;
	
	@Transient
	private int periodCount;
	
	

	@Transient
	private List<PeriodicTimePlan> periodicTimePlans;

	

	@Transient
	private String planStartDateStr;
	@Transient
	private String planEndDateStr;

	@Transient
	private int  schCount;
	
	@Transient
	private String planDayName;
	@Transient
	private String planDayTime;
	@Transient
	private String planEndDayTime;
	@Transient
	private int planCount;
	
	@Transient
	private String planStatusComment;
	@Transient
	private int planStatus;
	
	
	@Transient
	private User staff;
	
	@Transient
	private long progId;
	
	

	@Transient
	private int progType;
	@Transient
	private String progName;
	@Transient
	private String progShortName;
	
	@Transient
	private int lastPlan;
	@Transient
	private int firstPlan;
	
	@Transient
	private String sequence ;
	
	@JsonIgnore
	@Transient
	private List<ScheduleFactory> users;
	
	
	@Transient
	private int updateFlag=0;
	
	@Transient
	private String participants;
	
	
	
	
	
	public long getProgId() {
		return progId;
	}

	public void setProgId(long progId) {
		this.progId = progId;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public List<PeriodicTimePlan> getPeriodicTimePlans() {
		return periodicTimePlans;
	}

	public void setPeriodicTimePlans(List<PeriodicTimePlan> periodicTimePlans) {
		this.periodicTimePlans = periodicTimePlans;
	}

	public ProgramFactory getProgramFactory() {
		return programFactory;
	}

	public void setProgramFactory(ProgramFactory programFactory) {
		this.programFactory = programFactory;
	}

	public long getSchtId() {
		return schtId;
	}

	public void setSchtId(long schtId) {
		this.schtId = schtId;
	}

	public long getSchId() {
		return schId;
	}

	public void setSchId(long schId) {
		this.schId = schId;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public int getStatuTp() {
		return statuTp;
	}

	public void setStatuTp(int statuTp) {
		this.statuTp = statuTp;
	}

	public long getSchtStaffId() {
		return schtStaffId;
	}

	public void setSchtStaffId(long schtStaffId) {
		this.schtStaffId = schtStaffId;
	}

	public String getTpComment() {
		return tpComment;
	}

	public void setTpComment(String tpComment) {
		this.tpComment = tpComment;
	}

	public String getPlanStartDateStr() {
		return planStartDateStr;
	}

	public void setPlanStartDateStr(String planStartDateStr) {
		this.planStartDateStr = planStartDateStr;
	}

	public String getPlanEndDateStr() {
		return planEndDateStr;
	}

	public void setPlanEndDateStr(String planEndDateStr) {
		this.planEndDateStr = planEndDateStr;
	}

	public int getSchCount() {
		return schCount;
	}

	public void setSchCount(int schCount) {
		this.schCount = schCount;
	}

	public String getPlanDayName() {
		return planDayName;
	}

	public void setPlanDayName(String planDayName) {
		this.planDayName = planDayName;
	}

	public String getPlanDayTime() {
		return planDayTime;
	}

	public void setPlanDayTime(String planDayTime) {
		this.planDayTime = planDayTime;
	}

	public String getPlanEndDayTime() {
		return planEndDayTime;
	}

	public void setPlanEndDayTime(String planEndDayTime) {
		this.planEndDayTime = planEndDayTime;
	}

	public int getPlanCount() {
		return planCount;
	}

	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}

	public String getPlanStatusComment() {
		return planStatusComment;
	}

	public void setPlanStatusComment(String planStatusComment) {
		this.planStatusComment = planStatusComment;
	}

	public int getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}

	public User getStaff() {
		return staff;
	}

	public void setStaff(User staff) {
		this.staff = staff;
	}

	public int getProgType() {
		return progType;
	}

	public void setProgType(int progType) {
		this.progType = progType;
	}

	
	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	public String getProgShortName() {
		return progShortName;
	}

	public void setProgShortName(String progShortName) {
		this.progShortName = progShortName;
	}

	public int getLastPlan() {
		return lastPlan;
	}

	public void setLastPlan(int lastPlan) {
		this.lastPlan = lastPlan;
	}

	public int getFirstPlan() {
		return firstPlan;
	}

	public void setFirstPlan(int firstPlan) {
		this.firstPlan = firstPlan;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public List<ScheduleFactory> getUsers() {
		return users;
	}

	public void setUsers(List<ScheduleFactory> users) {
		this.users = users;
	}

	public int getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public SchedulePlan getSchedulePlan() {
		return schedulePlan;
	}

	public void setSchedulePlan(SchedulePlan schedulePlan) {
		this.schedulePlan = schedulePlan;
	}

	public List<ScheduleFactory> getScheduleFactories() {
		return scheduleFactories;
	}

	public void setScheduleFactories(List<ScheduleFactory> scheduleFactories) {
		this.scheduleFactories = scheduleFactories;
	}

	
	
	public int getPeriodCount() {
		return periodCount;
	}

	public void setPeriodCount(int periodCount) {
		this.periodCount = periodCount;
	}

	
	
	
	
}
