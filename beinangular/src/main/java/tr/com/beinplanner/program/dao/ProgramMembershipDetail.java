package tr.com.beinplanner.program.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="program_membership_detail")
public class ProgramMembershipDetail {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PROG_DET_ID")
	private long progDetId;
	
	@Column(name="PROG_ID")
	private long progId;

	@Column(name="PROG_RESTRICTED_DAY")
	private int progRestrictedDay;

	@Column(name="PROG_RESTRICTED_TIME")
	private int progRestrictedTime;
	
	
	
	public int getProgRestrictedDay() {
		return progRestrictedDay;
	}
	public void setProgRestrictedDay(int progRestrictedDay) {
		this.progRestrictedDay = progRestrictedDay;
	}
	public int getProgRestrictedTime() {
		return progRestrictedTime;
	}
	public void setProgRestrictedTime(int progRestrictedTime) {
		this.progRestrictedTime = progRestrictedTime;
	}

	public long getProgDetId() {
		return progDetId;
	}
	public void setProgDetId(long progDetId) {
		this.progDetId = progDetId;
	}
	public long getProgId() {
		return progId;
	}
	public void setProgId(long progId) {
		this.progId = progId;
	}
	
	
	
}
