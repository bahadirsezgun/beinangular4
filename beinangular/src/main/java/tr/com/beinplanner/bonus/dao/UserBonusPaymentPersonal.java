package tr.com.beinplanner.bonus.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import tr.com.beinplanner.user.dao.User;

@Entity
@Table(name="user_bonus_payment_personal")
@Scope("prototype")
@JsonTypeName("bpp")
public class UserBonusPaymentPersonal extends UserBonusPaymentFactory {

	@Id
	@Column(name="BON_ID")
	private long bonId;
	
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="BON_PAYMENT_DATE")
	private Date bonPaymentDate;
	
	@Column(name="BON_AMOUNT")
	private double bonAmount;
	
	@Column(name="BON_COMMENT")
	private String bonComment;
	
	@Column(name="BON_MONTH")
	private int bonMonth;
	
	@Column(name="BON_YEAR")
	private int bonYear;
	
	@Column(name="BON_START_DATE")
	private Date bonStartDate;
	
	@Column(name="BON_END_DATE")
	private Date bonEndDate;

	@JsonIgnore
	@Column(name="FIRM_ID")
	private int firmId;

	
	

	@Transient
	private String bonType;
	@Transient
	private String bonPaymentDateStr;
	
	@Transient
	private String bonMonthName;
	
	@Transient
	private String bonStartDateStr;
	@Transient
	private String bonEndDateStr;
	@Transient
	private int bonQueryType;
	
	/********************************************************/
	/**************USER ATTRIBUTES***************************/
	/********************************************************/
	@Transient
	private User 		user;
	
	public String getBonType() {
		return bonType;
	}

	public void setBonType(String bonType) {
		this.bonType = bonType;
	}

	public String getBonPaymentDateStr() {
		return bonPaymentDateStr;
	}

	public void setBonPaymentDateStr(String bonPaymentDateStr) {
		this.bonPaymentDateStr = bonPaymentDateStr;
	}

	public String getBonMonthName() {
		return bonMonthName;
	}

	public void setBonMonthName(String bonMonthName) {
		this.bonMonthName = bonMonthName;
	}

	public String getBonStartDateStr() {
		return bonStartDateStr;
	}

	public void setBonStartDateStr(String bonStartDateStr) {
		this.bonStartDateStr = bonStartDateStr;
	}

	public String getBonEndDateStr() {
		return bonEndDateStr;
	}

	public void setBonEndDateStr(String bonEndDateStr) {
		this.bonEndDateStr = bonEndDateStr;
	}

	public int getBonQueryType() {
		return bonQueryType;
	}

	public void setBonQueryType(int bonQueryType) {
		this.bonQueryType = bonQueryType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getBonId() {
		return bonId;
	}

	public void setBonId(long bonId) {
		this.bonId = bonId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getBonPaymentDate() {
		return bonPaymentDate;
	}

	public void setBonPaymentDate(Date bonPaymentDate) {
		super.setBonPaymentDate(bonPaymentDate);
		this.bonPaymentDate = bonPaymentDate;
	}

	public double getBonAmount() {
		return bonAmount;
	}

	public void setBonAmount(double bonAmount) {
		this.bonAmount = bonAmount;
	}

	public String getBonComment() {
		return bonComment;
	}

	public void setBonComment(String bonComment) {
		this.bonComment = bonComment;
	}

	public int getBonMonth() {
		return bonMonth;
	}

	public void setBonMonth(int bonMonth) {
		this.bonMonth = bonMonth;
	}

	public int getBonYear() {
		return bonYear;
	}

	public void setBonYear(int bonYear) {
		this.bonYear = bonYear;
	}

	public Date getBonStartDate() {
		return bonStartDate;
	}

	public void setBonStartDate(Date bonStartDate) {
		this.bonStartDate = bonStartDate;
	}

	public Date getBonEndDate() {
		return bonEndDate;
	}

	public void setBonEndDate(Date bonEndDate) {
		this.bonEndDate = bonEndDate;
	}
	
	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
	
}
