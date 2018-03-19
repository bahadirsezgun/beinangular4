package tr.com.beinplanner.packetpayment.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Table(name="packet_payment_membership_detail")
@JsonTypeName("ppmd")
public class PacketPaymentMembershipDetail extends PacketPaymentDetailFactory {

	@Transient
	private String type="ppmd";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PAY_DET_ID")
	private long payDetId;
	
	@Column(name="PAY_ID")
	private long payId;
	
	@Column(name="PAY_AMOUNT")
	private double payAmount;
	
	@Column(name="PAY_DATE")
	private Date payDate;
	
	@Transient
	private String payDateStr;
	
	@Column(name="CHANGE_DATE")
	private Date changeDate=new Date();

	
	@Column(name="PAY_TYPE")
	private int payType;
	
	@Column(name="PAY_COMMENT")
	private String payComment;
	
	@Column(name="PAY_CONFIRM")
	private int payConfirm;

	public long getPayDetId() {
		return payDetId;
	}

	public void setPayDetId(long payDetId) {
		this.payDetId = payDetId;
	}

	public long getPayId() {
		return payId;
	}

	public void setPayId(long payId) {
		this.payId = payId;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayDateStr() {
		return payDateStr;
	}

	public void setPayDateStr(String payDateStr) {
		this.payDateStr = payDateStr;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getPayComment() {
		return payComment;
	}

	public void setPayComment(String payComment) {
		this.payComment = payComment;
	}

	public int getPayConfirm() {
		return payConfirm;
	}

	public void setPayConfirm(int payConfirm) {
		this.payConfirm = payConfirm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
