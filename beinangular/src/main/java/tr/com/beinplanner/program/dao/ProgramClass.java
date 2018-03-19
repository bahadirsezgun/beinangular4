package tr.com.beinplanner.program.dao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonTypeName;

import tr.com.beinplanner.definition.dao.DefFirm;

@Entity
@Table(name="program_class")
@Scope("prototype")
@JsonTypeName("pc")
public class ProgramClass extends ProgramFactory  {

	
	@Transient
	private String type="pc";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PROG_ID")
	private long 	progId;
	
	
	@Column(name="PROG_NAME")
	private String 	progName;
	
	@Column(name="CREATE_TIME")
	private Date 	createTime=new Date();//=GlobalUtil.getCurrentDateByTimeZone();
	
	@Transient
	private String 	createTimeStr;
	
	@Column(name="PROG_PRICE")
	private double 	progPrice;

	@Column(name="PROG_DURATION")
	private int 	progDuration;  
	
	@Column(name="PROG_BEFORE_DURATION")
	private int 	progBeforeDuration;

	@Column(name="PROG_AFTER_DURATION")
	private int 	progAfterDuration;

	@Column(name="PROG_USER_ID")
	private long 	progUserId;

	@Column(name="PROG_DESCRIPTION")
	private String 	progDescription;

	@Column(name="PROG_COMMENT")
	private String 	progComment;

	@Column(name="FIRM_ID")
	private int 	firmId;

	@Column(name="PROG_STATUS")
	private int 	progStatus;

	@Column(name="PROG_COUNT")
	private int 	progCount;
	
	@Column(name="MIN_MEMBER_COUNT")
	private int 	minMemberCount;
	
	@Column(name="MAX_MEMBER_COUNT")
	private int 	maxMemberCount;
	
	@Column(name="PROG_ICON")
	private String progIcon;
	
	@Column(name="PROG_SHORT_NAME")
	private String progShortName;
	
	
	@Column(name="REST_FLAG")
	private int 	restFlag;  
	
	@Column(name="REST_TYPE")
	private int 	restType;  

	@Column(name="REST_DURATION")
	private int 	restDuration;

	@Transient
	private DefFirm defFirm;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		super.setType(type);
		this.type = type;
	}

	

	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
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

	public double getProgPrice() {
		return progPrice;
	}

	public void setProgPrice(double progPrice) {
		this.progPrice = progPrice;
	}

	public int getProgDuration() {
		return progDuration;
	}

	public void setProgDuration(int progDuration) {
		this.progDuration = progDuration;
	}

	public int getProgBeforeDuration() {
		return progBeforeDuration;
	}

	public void setProgBeforeDuration(int progBeforeDuration) {
		this.progBeforeDuration = progBeforeDuration;
	}

	public int getProgAfterDuration() {
		return progAfterDuration;
	}

	public void setProgAfterDuration(int progAfterDuration) {
		this.progAfterDuration = progAfterDuration;
	}

	public long getProgUserId() {
		return progUserId;
	}

	public void setProgUserId(long progUserId) {
		this.progUserId = progUserId;
	}

	public String getProgDescription() {
		return progDescription;
	}

	public void setProgDescription(String progDescription) {
		this.progDescription = progDescription;
	}

	public String getProgComment() {
		return progComment;
	}

	public void setProgComment(String progComment) {
		this.progComment = progComment;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public int getProgStatus() {
		return progStatus;
	}

	public void setProgStatus(int progStatus) {
		this.progStatus = progStatus;
	}

	public int getProgCount() {
		return progCount;
	}

	public void setProgCount(int progCount) {
		this.progCount = progCount;
	}

	public int getMinMemberCount() {
		return minMemberCount;
	}

	public void setMinMemberCount(int minMemberCount) {
		this.minMemberCount = minMemberCount;
	}

	public int getMaxMemberCount() {
		return maxMemberCount;
	}

	public void setMaxMemberCount(int maxMemberCount) {
		this.maxMemberCount = maxMemberCount;
	}

	public String getProgIcon() {
		return progIcon;
	}

	public void setProgIcon(String progIcon) {
		this.progIcon = progIcon;
	}

	public String getProgShortName() {
		return progShortName;
	}

	public void setProgShortName(String progShortName) {
		this.progShortName = progShortName;
	}

	public int getRestFlag() {
		return restFlag;
	}

	public void setRestFlag(int restFlag) {
		this.restFlag = restFlag;
	}

	public int getRestType() {
		return restType;
	}

	public void setRestType(int restType) {
		this.restType = restType;
	}

	public int getRestDuration() {
		return restDuration;
	}

	public void setRestDuration(int restDuration) {
		this.restDuration = restDuration;
	}

	public DefFirm getDefFirm() {
		return defFirm;
	}

	public void setDefFirm(DefFirm defFirm) {
		this.defFirm = defFirm;
	}

	public long getProgId() {
		return progId;
	}

	public void setProgId(long progId) {
		this.progId = progId;
	} 
	
	
	
	
	
	
	
	
	
}
