package tr.com.beinplanner.definition.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="def_firm")
public class DefFirm {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FIRM_ID")
	private int firmId;
	
	@Column(name="FIRM_NAME")
	private String firmName;
	
	@Column(name="FIRM_PHONE")
	private String firmPhone;
	
	@Column(name="FIRM_ADDRESS")
	private String firmAddress;
	
	@Column(name="FIRM_EMAIL")
	private String firmEmail;

	
	@Column(name="FIRM_CITY_NAME")
	private String firmCityName;
	
	@Column(name="FIRM_STATE_NAME")
	private String firmStateName;
	
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Transient
	private String createTimeStr;
	
	@Transient
	private boolean selected;
	
	@Column(name="FIRM_RESTRICTION")
	private int firmRestriction;
	
	@Column(name="FIRM_AUTH_PERSON")
	private String firmAuthPerson;
	
	@Column(name="FIRM_GROUP_ID")
	private int firmGroupId;

	@JsonIgnore
	@Column(name="STRIPE_CUST_ID")
	private String stripeCustId;
	
	
	

	@Column(name="FIRM_APPROVED")
	private int firmApproved;

	
	public String getStripeCustId() {
		return stripeCustId;
	}

	public void setStripeCustId(String stripeCustId) {
		this.stripeCustId = stripeCustId;
	}
	
	public int getFirmApproved() {
		return firmApproved;
	}

	public void setFirmApproved(int firmApproved) {
		this.firmApproved = firmApproved;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmPhone() {
		return firmPhone;
	}

	public void setFirmPhone(String firmPhone) {
		this.firmPhone = firmPhone;
	}

	public String getFirmAddress() {
		return firmAddress;
	}

	public void setFirmAddress(String firmAddress) {
		this.firmAddress = firmAddress;
	}

	public String getFirmEmail() {
		return firmEmail;
	}

	public void setFirmEmail(String firmEmail) {
		this.firmEmail = firmEmail;
	}

	


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getFirmRestriction() {
		return firmRestriction;
	}

	public void setFirmRestriction(int firmRestriction) {
		this.firmRestriction = firmRestriction;
	}

	public String getFirmAuthPerson() {
		return firmAuthPerson;
	}

	public void setFirmAuthPerson(String firmAuthPerson) {
		this.firmAuthPerson = firmAuthPerson;
	}

	public int getFirmGroupId() {
		return firmGroupId;
	}

	public void setFirmGroupId(int firmGroupId) {
		this.firmGroupId = firmGroupId;
	}

	public String getFirmCityName() {
		return firmCityName;
	}

	public void setFirmCityName(String firmCityName) {
		this.firmCityName = firmCityName;
	}

	public String getFirmStateName() {
		return firmStateName;
	}

	public void setFirmStateName(String firmStateName) {
		this.firmStateName = firmStateName;
	}
	
	
	
}
