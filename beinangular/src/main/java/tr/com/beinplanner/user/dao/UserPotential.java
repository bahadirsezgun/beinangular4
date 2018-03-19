package tr.com.beinplanner.user.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "user_potential") 
public class UserPotential {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_SURNAME")
	private String userSurname;

	
	@Column(name="USER_GSM")
	private String userGsm;

	@Column(name="USER_EMAIL")
	private String userEmail;
	
	@Column(name="USER_GENDER")
	private long userGender;
	
	@Column(name="P_COMMENT")
	private String 		pComment;
	
	@Column(name="P_STATU")
	private int 		pStatu;
	
	@Transient
	private String 		pStatuStr;
	
	
	

	@Column(name="FIRM_ID")
	private int firmId;
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Column(name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Column(name="STAFF_ID")
	private long staffId;

	@Transient
	private User staff;
	
	public String getpStatuStr() {
		return pStatuStr;
	}

	public void setpStatuStr(String pStatuStr) {
		this.pStatuStr = pStatuStr;
	}
	
	public User getStaff() {
		return staff;
	}

	public void setStaff(User staff) {
		this.staff = staff;
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

	public long getUserGender() {
		return userGender;
	}

	public void setUserGender(long userGender) {
		this.userGender = userGender;
	}

	public String getpComment() {
		return pComment;
	}

	public void setpComment(String pComment) {
		this.pComment = pComment;
	}

	public int getpStatu() {
		return pStatu;
	}

	public void setpStatu(int pStatu) {
		this.pStatu = pStatu;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	
	
	

	
	
	
}
