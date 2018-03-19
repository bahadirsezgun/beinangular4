package tr.com.beinplanner.settings.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pt_lock")
public class PtLock {

	@JsonIgnore
	@Id
	@Column(name="FIRM_ID")
	private int firmId;
	
	@Column(name="BONUS_LOCK")
	private int bonusLock;

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public int getBonusLock() {
		return bonusLock;
	}

	public void setBonusLock(int bonusLock) {
		this.bonusLock = bonusLock;
	}
	
	
	
}
