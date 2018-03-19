package tr.com.beinplanner.schedule.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tr.com.beinplanner.schedule.businessEntity.ScheduleTimeObj;
import tr.com.beinplanner.user.dao.User;


/**
 * 
 * @author bahadir
 * @comment Zaman Planı oluşturmada en üst objesidir.
 *          Genel Yapı : <br>
 *   		SchedulePlan -> List[ScheduleTimePlan] <br>
 *                       -> ScheduleTimePlan -> List[ScheduleFactory] <br>
 *                                              ScheduleUsersClassPlan.class, name = "sucp",<br>
					   							ScheduleMembershipPlan.class, name = "smp",<br>
					   							ScheduleUsersPersonalPlan.class, name = "supp"
 * 
 */
@Entity
@Table(name="schedule_plan")
public class SchedulePlan {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SCH_ID")
	private long 					schId;
	
	@Column(name="PROG_ID")
	private long					progId;
	
	@Transient
	private String 					progName;
	
	@Column(name="PROG_TYPE")
	private int 					progType;
	
	@Transient
	private String 					progTypeStr;
		
	@Column(name="SCH_COUNT")
	private int 					schCount;
	
	@Column(name="SCH_STAFF_ID")
	private long 					schStaffId;
	
	@Column(name="FIRM_ID")
	private int						firmId;
	
	
	public int getFirmId() {
		return firmId;
	}
	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
	/**
	 * @author bahadir
	 * @comment Veritabanına ilgili firma için time plan yaratılır.
	 */
	
	
	@Transient
    private List<ScheduleTimePlan>  scheduleTimePlans;
	
	
	
	
	
	
	/*****************************/
	/*MAIN INSTRUCTOR INFORMATION
	 * THIS IS FOR STAFF INFORMATION */
	/*****************************/
	@Transient
	private String 					userName;
	@Transient
	private String 					userSurname;
	@Transient
	private String 					profileUrl;
	@Transient
	private int 					urlType;
	@Transient
	private int 					userGender;
	
	/*****************************/
	/*PLANNING EXTRA INFORMATION
	 * THIS IS FLAGS */
	/*****************************/
	@Transient
	private int 					finishedPlan;
	@Transient
	private int 					programCount;
	@Transient
	private int 					plannedCount;
	@Transient
	private int 					willPlanCount;
	
	
	/**
	 * @author bahadir
	 * @comment Sistem uzerinde yapılması gereken kac planlama var sorunusun cevabidir.
	 */
	@Transient
	private int 					leftProgCount;
	
	/**
	 * @author bahadir
	 * @comment Schedule Time Planlama yaparken periodik olarakmı yok sa tekil olarak mi yapılacak.
	 */
	@Transient
	private int 					timeType;
	
	/**
	 * @author bahadir
	 *  @comment Periyodik planlama yapılırken kaç adet planlama yapılması gerektigi ile ilgili flag.
	 */
	@Transient
	private int 					periodCount;
	
	/*****************************/
	/* USERS MUST SEND SEPERATELY 
	 * TO CHECK THAT THEY HAVE SALE RECORDE */
	/*****************************/
	@Transient
	private List<User> 				users;
	
	
	/**
	 * @author bahadir
	 *  @comment Planlamanın periyodik olarak yapılması durumunda, planlama gün,saat bilgileri javascript ile gönderilir. planStartDateStr başlama noktası seçilir ve bu tarihe göre periyodik olarak ScheduleTimePlan objesi oluşturulur.
	 */
	@Transient
	private List<ScheduleTimeObj> 	scheduleTimeObjs;
	
	/**
	 * @author bahadir
	 *  @comment Planlama için bir açıklama yapılırken Ana objeye javascript ile acıklama setlenir. Bu acıklama daha sonra her bir time plan için setlenir.
	 */
	@Transient
	private String tpComment;
	
	
	
	
	
	/**
	 * @author bahadir
	 *  @comment Planlamanın başlama tarihi javascript ile gönderilir. Bu tarih başlama noktası seçilir ve bu tarihe göre ScheduleTimePlan objesi oluşturulur.
	 */
	@Transient
	private String 		planStartDateStr;
	
	
	/**
	 * @author bahadir
	 *  @comment Planlamanın başlama saati javascript ile gönderilir. Bu saat periyodik planlama değil ise kullanılır. planStartDateStr e eklenir.
	 */
	@Transient
	private String 		planStartDateTime;
	
	
	public long getSchId() {
		return schId;
	}
	public void setSchId(long schId) {
		this.schId = schId;
	}
	
	public long getProgId() {
		return progId;
	}
	public void setProgId(long progId) {
		this.progId = progId;
	}
	public int getProgType() {
		return progType;
	}
	public void setProgType(int progType) {
		this.progType = progType;
	}
	public int getSchCount() {
		return schCount;
	}
	public void setSchCount(int schCount) {
		this.schCount = schCount;
	}
	public long getSchStaffId() {
		return schStaffId;
	}
	public void setSchStaffId(long schStaffId) {
		this.schStaffId = schStaffId;
	}
	
	

	@Override
	public String toString() {
		////System.out.println("------------------------------------------");
		////System.out.println("-------------SCHEDULE PLAN----------------");
		////System.out.println("------------------------------------------");
		////System.out.println("schId:"+schId);
		////System.out.println("progId:"+progId);
		////System.out.println("progType:"+progType);
		////System.out.println("schCount:"+schCount);
		////System.out.println("schStaffId:"+schStaffId);
		////System.out.println("firmId:"+firmId);
		////System.out.println("------------------------------------------");
		////System.out.println("---------END-SCHEDULE PLAN----------------");
		////System.out.println("------------------------------------------");
		
		return super.toString();
	}
	
	
	
	public String getProgTypeStr() {
		return progTypeStr;
	}
	public void setProgTypeStr(String progTypeStr) {
		this.progTypeStr = progTypeStr;
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
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public int getUrlType() {
		return urlType;
	}
	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}
	public String getProgName() {
		return progName;
	}
	public void setProgName(String progName) {
		this.progName = progName;
	}
	
	public int getFinishedPlan() {
		return finishedPlan;
	}
	public void setFinishedPlan(int finishedPlan) {
		this.finishedPlan = finishedPlan;
	}
	
	public int getUserGender() {
		return userGender;
	}
	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}
	public int getProgramCount() {
		return programCount;
	}
	public void setProgramCount(int programCount) {
		this.programCount = programCount;
	}
	public int getPlannedCount() {
		return plannedCount;
	}
	public void setPlannedCount(int plannedCount) {
		this.plannedCount = plannedCount;
	}
	public int getWillPlanCount() {
		return willPlanCount;
	}
	public void setWillPlanCount(int willPlanCount) {
		this.willPlanCount = willPlanCount;
	}
	
	/**
	 * @author bahadir
	 * @comment Sistem uzerinde yapılması gereken kac planlama var sorunusun cevabidir.
	 */
	public int getLeftProgCount() {
		return leftProgCount;
	}
	public void setLeftProgCount(int leftProgCount) {
		this.leftProgCount = leftProgCount;
	}
	
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	/**
	 * @author bahadir
	 *  @comment Planlamanın periyodik olarak yapılması durumunda, planlama gün,saat bilgileri javascript ile gönderilir. planStartDateStr başlama noktası seçilir ve bu tarihe göre periyodik olarak ScheduleTimePlan objesi oluşturulur.
	 */
	public List<ScheduleTimeObj> getScheduleTimeObjs() {
		return scheduleTimeObjs;
	}
	public void setScheduleTimeObjs(List<ScheduleTimeObj> scheduleTimeObjs) {
		this.scheduleTimeObjs = scheduleTimeObjs;
	}
	
	
	/**
	 * @author bahadir
	 * @comment Schedule Time Planlama yaparken periodik olarakmı yok sa tekil olarak mi yapılacak.
	 */
	public int getTimeType() {
		return timeType;
	}
	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}
	
	/**
	 * @author bahadir
	 *  @comment Periyodik planlama yapılırken kaç edet planlama yapılması gerektigi ile ilgili flag.
	 */
	public int getPeriodCount() {
		return periodCount;
	}
	public void setPeriodCount(int periodCount) {
		this.periodCount = periodCount;
	}
	
	
	
	/**
	 * @author bahadir
	 *  @comment Planlamanın başlama tarihi javascript ile gönderilir. Bu tarih başlama noktası seçilir ve bu tarihe göre ScheduleTimePlan objesi oluşturulur.
	 */
	public String getPlanStartDateStr() {
		return planStartDateStr;
	}
	public void setPlanStartDateStr(String planStartDateStr) {
		this.planStartDateStr = planStartDateStr;
	}
	
	
	/**
	 * @author bahadir
	 *  @comment Planlamanın başlama saati javascript ile gönderilir. Bu saat periyodik planlama değil ise kullanılır. planStartDateStr e eklenir.
	 */
	public String getPlanStartDateTime() {
		return planStartDateTime;
	}
	public void setPlanStartDateTime(String planStartDateTime) {
		this.planStartDateTime = planStartDateTime;
	}
	

	/**
	 * @author bahadir
	 *  @comment Planlama için bir açıklama yapılırken Ana objeye javascript ile acıklama setlenir. Bu acıklama daha sonra her bir time plan için setlenir.
	 */
	public String getTpComment() {
		return tpComment;
	}
	public void setTpComment(String tpComment) {
		this.tpComment = tpComment;
	}
	
	
	public List<ScheduleTimePlan> getScheduleTimePlans() {
		return scheduleTimePlans;
	}
	public void setScheduleTimePlans(List<ScheduleTimePlan> scheduleTimePlans) {
		this.scheduleTimePlans = scheduleTimePlans;
	}
	
	
}
