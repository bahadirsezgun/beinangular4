package tr.com.beinplanner.settings.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="pt_action")
public class PtAction {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="PTA_ID")
	private long ptaId;
	
	@Column(name="PTA_TO")
	private long ptaTo;
	
	@Column(name="PTA_FROM")
	private long ptaFrom;
	
	@Column(name="PTA_DESC")
	private String ptaDesc;
	
	@Column(name="PTA_SUBJECT")
	private String ptaSubject;
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Column(name="PTA_GRP_ID")
	private int ptaGrpId;
	
	@Column(name="PTA_UID")
	private String ptaUid;
	
	public long getPtaId() {
		return ptaId;
	}
	public void setPtaId(long ptaId) {
		this.ptaId = ptaId;
	}
	public long getPtaTo() {
		return ptaTo;
	}
	public void setPtaTo(long ptaTo) {
		this.ptaTo = ptaTo;
	}
	public long getPtaFrom() {
		return ptaFrom;
	}
	public void setPtaFrom(long ptaFrom) {
		this.ptaFrom = ptaFrom;
	}
	public String getPtaDesc() {
		return ptaDesc;
	}
	public void setPtaDesc(String ptaDesc) {
		this.ptaDesc = ptaDesc;
	}
	public String getPtaSubject() {
		return ptaSubject;
	}
	public void setPtaSubject(String ptaSubject) {
		this.ptaSubject = ptaSubject;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getPtaGrpId() {
		return ptaGrpId;
	}
	public void setPtaGrpId(int ptaGrpId) {
		this.ptaGrpId = ptaGrpId;
	}
	public String getPtaUid() {
		return ptaUid;
	}
	public void setPtaUid(String ptaUid) {
		this.ptaUid = ptaUid;
	}
	
	
	
	
}
