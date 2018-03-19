package tr.com.beinplanner.settings.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pt_restrictions")
public class PtRestrictions {

	@Id
	@Column(name="FIRM_ID")
	private int firmId;

	@Column(name="MEMBER_COUNT")
	private String memberCount;
	
	
	public int getFirmId() {
		return firmId;
	}
	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
	public String getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}
	
	
	
	
	
	
}
