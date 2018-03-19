package tr.com.beinplanner.income.dao;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pt_expenses") 
public class PtExpenses {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PE_ID")
	private long peId;
	
	@JsonIgnore
	@Column(name="FIRM_ID")
	private int firmId;
	
	@Column(name="PE_AMOUNT")
	private double peAmount;
	
	@Column(name="PE_COMMENT")
	private String peComment;
	
	
	@Column(name="PE_DATE")
	private Date peDate;
	
	@Column(name="PE_INOUT")
	private int peInOut;

	public long getPeId() {
		return peId;
	}

	public void setPeId(long peId) {
		this.peId = peId;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public double getPeAmount() {
		return peAmount;
	}

	public void setPeAmount(double peAmount) {
		this.peAmount = peAmount;
	}

	public String getPeComment() {
		return peComment;
	}

	public void setPeComment(String peComment) {
		this.peComment = peComment;
	}

	public Date getPeDate() {
		return peDate;
	}

	public void setPeDate(Date peDate) {
		this.peDate = peDate;
	}

	public int getPeInOut() {
		return peInOut;
	}

	public void setPeInOut(int peInOut) {
		this.peInOut = peInOut;
	}
	
	
	
}
