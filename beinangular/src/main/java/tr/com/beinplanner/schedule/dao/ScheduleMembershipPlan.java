package tr.com.beinplanner.schedule.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import tr.com.beinplanner.user.dao.User;
@Entity
@Table(name="schedule_membership_plan")
public class ScheduleMembershipPlan extends ScheduleFactory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SMP_ID")
	private long smpId;
	
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="SMP_START_DATE")
	private Date smpStartDate;
	
	@Column(name="SMP_END_DATE")
	private Date smpEndDate;
	
	@Column(name="SMP_FREEZE_COUNT")
	private int smpFreezeCount;
	
	@Column(name="SMP_PRICE")
	private double smpPrice;
	
	@Column(name="PROG_ID")
	private long progId;
	
	@Column(name="SALE_ID")
	private long saleId;
	
	@Column(name="SMP_STATUS")
	private long smpStatus;
	
	@Column(name="SMP_COMMENT")
	private String smpComment;
	
	
	@Transient
	private String smpStartDayName;
	@Transient
	private String smpEndDayName;
	@Transient
	private String smpStartDayTime;
	@Transient
	private String smpEndDayTime;
	@Transient
	private String smpStatusStr;
	
	
	@Transient
    private List<ScheduleMembershipTimePlan> scheduleMembershipTimePlans;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID",foreignKey=@ForeignKey(foreignKeyDefinition="SMP_TO_USER_FK"),insertable=false,updatable=false)
	private User user;
	
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		super.setUser(user);
		this.user = user;
	}


	public long getSmpId() {
		return smpId;
	}


	public void setSmpId(long smpId) {
		this.smpId = smpId;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		
		this.userId = userId;
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


	public int getSmpFreezeCount() {
		return smpFreezeCount;
	}


	public void setSmpFreezeCount(int smpFreezeCount) {
		this.smpFreezeCount = smpFreezeCount;
	}


	public double getSmpPrice() {
		return smpPrice;
	}


	public void setSmpPrice(double smpPrice) {
		this.smpPrice = smpPrice;
	}


	public long getProgId() {
		return progId;
	}


	public void setProgId(long progId) {
		this.progId = progId;
	}


	public long getSaleId() {
		return saleId;
	}


	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}


	public long getSmpStatus() {
		return smpStatus;
	}


	public void setSmpStatus(long smpStatus) {
		this.smpStatus = smpStatus;
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


	public List<ScheduleMembershipTimePlan> getScheduleMembershipTimePlans() {
		return scheduleMembershipTimePlans;
	}


	public void setScheduleMembershipTimePlans(List<ScheduleMembershipTimePlan> scheduleMembershipTimePlans) {
		this.scheduleMembershipTimePlans = scheduleMembershipTimePlans;
	}
}
