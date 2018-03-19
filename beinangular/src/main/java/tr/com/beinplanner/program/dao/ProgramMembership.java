package tr.com.beinplanner.program.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import tr.com.beinplanner.definition.dao.DefFirm;


@Entity
@Table(name="program_membership")
@Scope("prototype")
@JsonTypeName("pm")
public class ProgramMembership extends ProgramFactory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PROG_ID")
	private long 	progId;
	
	@Transient
	private String type="pm";
	
	
	@Column(name="PROG_NAME")
	private String 	progName;
	
	
	@Column(name="PROG_PRICE")
	private double 	progPrice;

	@Column(name="PROG_DESCRIPTION")
	private String 	progDescription;

	@Column(name="PROG_COMMENT")
	private String 	progComment;

	@JsonIgnore
	@Column(name="FIRM_ID")
	private int 	firmId;

	@Column(name="PROG_STATUS")
	private int 	progStatus;

	@Column(name="PROG_USER_ID")
	private long 	progUserId;

	@Column(name="CREATE_TIME")
	private Date 	createTime=new Date();//=GlobalUtil.getCurrentDateByTimeZone();
	
	@Transient
	private String 	createTimeStr;
	
	
	@Column(name="PROG_RESTRICTION")
	private int 	progRestriction;  
	
	@Column(name="PROG_DURATION")
	private int 	progDuration;

	@Column(name="PROG_DURATION_TYPE")
	private int 	progDurationType;

	
	
	
	
	
	@Column(name="MAX_FREEZE_COUNT")
	private int 	maxFreezeCount;
	
	@Column(name="FREEZE_DURATION")
	private int 	freezeDuration;
	
	@Column(name="FREEZE_DURATION_TYPE")
	private int 	freezeDurationType;
	
	@Column(name="PROG_ICON")
	private String progIcon;
	
	@Column(name="PROG_SHORT_NAME")
	private String progShortName;
	
	
	@Transient
	private DefFirm defFirm;

	@OneToMany(mappedBy="progId")
	private List<ProgramMembershipDetail> programMembershipDetails;


	



	public String getProgName() {
		return progName;
	}



	public void setProgName(String progName) {
		this.progName = progName;
	}



	public double getProgPrice() {
		return progPrice;
	}



	public void setProgPrice(double progPrice) {
		this.progPrice = progPrice;
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



	public long getProgUserId() {
		return progUserId;
	}



	public void setProgUserId(long progUserId) {
		this.progUserId = progUserId;
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



	public int getProgRestriction() {
		return progRestriction;
	}



	public void setProgRestriction(int progRestriction) {
		this.progRestriction = progRestriction;
	}



	public int getProgDuration() {
		return progDuration;
	}



	public void setProgDuration(int progDuration) {
		this.progDuration = progDuration;
	}



	public int getProgDurationType() {
		return progDurationType;
	}



	public void setProgDurationType(int progDurationType) {
		this.progDurationType = progDurationType;
	}



	public int getMaxFreezeCount() {
		return maxFreezeCount;
	}



	public void setMaxFreezeCount(int maxFreezeCount) {
		this.maxFreezeCount = maxFreezeCount;
	}



	public int getFreezeDuration() {
		return freezeDuration;
	}



	public void setFreezeDuration(int freezeDuration) {
		this.freezeDuration = freezeDuration;
	}



	public int getFreezeDurationType() {
		return freezeDurationType;
	}



	public void setFreezeDurationType(int freezeDurationType) {
		this.freezeDurationType = freezeDurationType;
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



	public String getType() {
		return type;
	}



	public void setType(String type) {
		super.setType(type);
		this.type = type;
	}



	public List<ProgramMembershipDetail> getProgramMembershipDetails() {
		return programMembershipDetails;
	}



	public void setProgramMembershipDetails(List<ProgramMembershipDetail> programMembershipDetails) {
		this.programMembershipDetails = programMembershipDetails;
	}



	
	
}
