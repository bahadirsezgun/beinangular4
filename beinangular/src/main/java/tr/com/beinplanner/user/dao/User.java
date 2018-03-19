package tr.com.beinplanner.user.dao;

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

import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;



@Entity
@Table(name = "user") 
public class User implements Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_SURNAME")
	private String userSurname;

	@JsonIgnore
	@Column(name="PASSWORD")
	private String password;

	@Transient
	private String oldPassword;

	
	@Transient
	private String newPassword;

	
	@Column(name="USER_BIRTHDAY")
	private Date userBirthday;

	@JsonIgnore
	@Column(name="FIRM_ID")
	private int firmId;
	
	@Column(name="STATE_ID",columnDefinition="default '0'")
	private int stateId;
	
	@Column(name="CITY_ID",columnDefinition="default '0'")
	private int cityId;
	
	@Column(name="USER_ADDRESS")
	private String userAddress;

	@Column(name="USER_PHONE")
	private String userPhone;

	@Column(name="USER_GSM")
	private String userGsm;

	@Column(name="USER_EMAIL")
	private String userEmail;

	@Column(name="PROFILE_URL")
	private String profileUrl;
	
	@Column(name="USER_TYPE",columnDefinition="default '1'") // MEMBER
	private int userType;
	

	@Column(name="USER_SSN")
	private String userSsn;

	@Column(name="USER_GENDER")
	private long userGender;
	
	@Column(name="STAFF_ID")
	private long staffId;
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();

	@Column(name="URL_TYPE")
	private int urlType;

	@Column(name="BONUS_TYPE_P")
	private int bonusTypeP;
	
	@Column(name="BONUS_TYPE_C")
	private int bonusTypeC;
	
	@Column(name="USER_COMMENT")
	private String userComment;

	@Column(name="STAFF_STATU")
	private int staffStatu;

	
	

	/**
	 * @comment This attribute is for calendar times of scheduler staff.
	 */
	@Transient
	private List<ScheduleTimePlan> scheduleTimePlans;
	

	/**
	 * @comment This attribute is for calendar free sales of members.
	 */
	@Transient
	private long saleId;
	
	@Transient
	private long suppId;
	
	@Transient
	private long sucpId;
	


	



	public long getSuppId() {
		return suppId;
	}



	public void setSuppId(long suppId) {
		this.suppId = suppId;
	}



	public long getSucpId() {
		return sucpId;
	}



	public void setSucpId(long sucpId) {
		this.sucpId = sucpId;
	}



	public long getSaleId() {
		return saleId;
	}



	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}



	public List<ScheduleTimePlan> getScheduleTimePlans() {
		return scheduleTimePlans;
	}



	public void setScheduleTimePlans(List<ScheduleTimePlan> scheduleTimePlans) {
		this.scheduleTimePlans = scheduleTimePlans;
	}



	public String getUserComment() {
		return userComment;
	}



	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}



	public User(User user) {
		    this.userEmail = user.getUserEmail();
	        this.bonusTypeC = user.getBonusTypeC() ;
	        this.bonusTypeP = user.getBonusTypeP();
	        this.cityId =user.getCityId();
	        this.createTime = user.getCreateTime();
	        this.password = user.getPassword();
	        this.firmId=user.getFirmId();
	        this.profileUrl=user.getProfileUrl();
	        this.staffId=user.getStaffId();
	        this.stateId=user.getStateId();
	        this.urlType=user.getUrlType();
	        this.userAddress=user.getUserAddress();
	        this.userBirthday=user.getUserBirthday();
	        this.userGender=user.getUserGender();
	        this.userGsm=user.getUserGsm();
	        this.userId=user.getUserId();
	        this.userName=user.getUserName();
	        this.userPhone=user.getUserPhone();
	        this.userSsn=user.getUserSsn();
	        this.userSurname=user.getUserSurname();
	        this.userType=user.getUserType();
	        this.userComment=user.getUserComment();
	        
	}
	
	
	
	public User() {
		super();
		
	}



	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
	
	public int getFirmId() {
		return firmId;
	}



	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}



	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserGsm() {
		return userGsm;
	}
	public void setUserGsm(String userGsm) {
		this.userGsm = userGsm;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	public int getUserType() {
		return userType;
	}



	public void setUserType(int userType) {
		this.userType = userType;
	}



	public String getUserSsn() {
		return userSsn;
	}
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	public long getUserGender() {
		return userGender;
	}
	public void setUserGender(long userGender) {
		this.userGender = userGender;
	}
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getUrlType() {
		return urlType;
	}
	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}
	public int getBonusTypeP() {
		return bonusTypeP;
	}
	public void setBonusTypeP(int bonusTypeP) {
		this.bonusTypeP = bonusTypeP;
	}
	public int getBonusTypeC() {
		return bonusTypeC;
	}
	public void setBonusTypeC(int bonusTypeC) {
		this.bonusTypeC = bonusTypeC;
	}



	public int getStaffStatu() {
		return staffStatu;
	}



	public void setStaffStatu(int staffStatu) {
		this.staffStatu = staffStatu;
	}



	public String getOldPassword() {
		return oldPassword;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}



	public String getNewPassword() {
		return newPassword;
	}



	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	
	
	
	
}
