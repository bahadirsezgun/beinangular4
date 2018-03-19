package tr.com.beinplanner.packetpayment.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="type")
@JsonSubTypes({@JsonSubTypes.Type(value = PacketPaymentPersonal.class, name = "ppp"),
			   @JsonSubTypes.Type(value = PacketPaymentClass.class, name = "ppc"),
			   @JsonSubTypes.Type(value = PacketPaymentMembership.class, name = "ppm")})
public abstract class PacketPaymentFactory {

	
	private Date payDate;

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	
	
	
	

	
	
	
		
	
	
}
