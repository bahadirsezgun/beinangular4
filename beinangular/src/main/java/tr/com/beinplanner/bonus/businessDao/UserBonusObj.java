package tr.com.beinplanner.bonus.businessDao;

import java.util.Date;
import java.util.List;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;

public class UserBonusObj {

	private long 	schStaffId;
	private double 	willPayAmount;
	private double 	payedAmount;
	private int 	month;
	private String 	monthName;
	private Date 	bonusStartDate;
	private Date 	bonusEndDate;
	private String 	bonusStartDateStr;
	private String 	bonusEndDateStr;
	private int bonusType;
	
	private int bonusPaymentRule;
	private int creditCardCommissionRule;
	private int creditCardCommissionRate;
	
	
	private List<UserBonusDetailObj> 		userBonusDetailObjs;
	private List<UserBonusPaymentFactory> 	userBonusPaymentFactories;


	public long getSchStaffId() {
		return schStaffId;
	}



	public void setSchStaffId(long schStaffId) {
		this.schStaffId = schStaffId;
	}



	


	public int getMonth() {
		return month;
	}



	public void setMonth(int month) {
		this.month = month;
	}



	public String getMonthName() {
		return monthName;
	}



	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}



	public Date getBonusStartDate() {
		return bonusStartDate;
	}



	public void setBonusStartDate(Date bonusStartDate) {
		this.bonusStartDate = bonusStartDate;
	}



	public Date getBonusEndDate() {
		return bonusEndDate;
	}



	public void setBonusEndDate(Date bonusEndDate) {
		this.bonusEndDate = bonusEndDate;
	}



	public String getBonusStartDateStr() {
		return bonusStartDateStr;
	}



	public void setBonusStartDateStr(String bonusStartDateStr) {
		this.bonusStartDateStr = bonusStartDateStr;
	}



	public String getBonusEndDateStr() {
		return bonusEndDateStr;
	}



	public void setBonusEndDateStr(String bonusEndDateStr) {
		this.bonusEndDateStr = bonusEndDateStr;
	}



	public List<UserBonusDetailObj> getUserBonusDetailObjs() {
		return userBonusDetailObjs;
	}



	public void setUserBonusDetailObjs(List<UserBonusDetailObj> userBonusDetailObjs) {
		this.userBonusDetailObjs = userBonusDetailObjs;
	}



	public double getWillPayAmount() {
		return willPayAmount;
	}



	public void setWillPayAmount(double willPayAmount) {
		this.willPayAmount = willPayAmount;
	}



	public double getPayedAmount() {
		return payedAmount;
	}



	public void setPayedAmount(double payedAmount) {
		this.payedAmount = payedAmount;
	}



	public List<UserBonusPaymentFactory> getUserBonusPaymentFactories() {
		return userBonusPaymentFactories;
	}



	public void setUserBonusPaymentFactories(List<UserBonusPaymentFactory> userBonusPaymentFactories) {
		this.userBonusPaymentFactories = userBonusPaymentFactories;
	}



	public int getBonusType() {
		return bonusType;
	}



	public void setBonusType(int bonusType) {
		this.bonusType = bonusType;
	}



	public int getBonusPaymentRule() {
		return bonusPaymentRule;
	}



	public void setBonusPaymentRule(int bonusPaymentRule) {
		this.bonusPaymentRule = bonusPaymentRule;
	}



	public int getCreditCardCommissionRule() {
		return creditCardCommissionRule;
	}



	public void setCreditCardCommissionRule(int creditCardCommissionRule) {
		this.creditCardCommissionRule = creditCardCommissionRule;
	}



	public int getCreditCardCommissionRate() {
		return creditCardCommissionRate;
	}



	public void setCreditCardCommissionRate(int creditCardCommissionRate) {
		this.creditCardCommissionRate = creditCardCommissionRate;
	}



	
	
	
	
}
