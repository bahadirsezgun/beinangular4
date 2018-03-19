package tr.com.beinplanner.bonus.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="bonType")
@JsonSubTypes({@JsonSubTypes.Type(value = UserBonusPaymentClass.class, name = "bpc"),
			   @JsonSubTypes.Type(value = UserBonusPaymentPersonal.class, name = "bpp")})
public abstract class UserBonusPaymentFactory {
	
	private Date bonPaymentDate;

	private int firmId;
	
	
	
	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public Date getBonPaymentDate() {
		return bonPaymentDate;
	}

	public void setBonPaymentDate(Date bonPaymentDate) {
		this.bonPaymentDate = bonPaymentDate;
	}
	
}
