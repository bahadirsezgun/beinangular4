package tr.com.beinplanner.packetpayment.dao;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import tr.com.beinplanner.user.dao.User;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="type")
@JsonSubTypes({@JsonSubTypes.Type(value = PacketPaymentPersonalDetail.class, name = "pppd"),
			   @JsonSubTypes.Type(value = PacketPaymentClassDetail.class, name = "ppcd"),
			   @JsonSubTypes.Type(value = PacketPaymentMembershipDetail.class, name = "ppmd")})
public abstract class PacketPaymentDetailFactory {

	@Transient
	private User user;
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
