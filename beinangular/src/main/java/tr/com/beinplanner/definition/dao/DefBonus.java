package tr.com.beinplanner.definition.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tr.com.beinplanner.program.dao.ProgramFactory;
@Entity
@Table(name="def_bonus")
public class DefBonus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BONUS_ID")
	private long bonusId;
	
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="BONUS_VALUE")
	private double bonusValue;
	
	@Column(name="BONUS_COUNT")
	private int bonusCount;
	
	@Column(name="BONUS_PROG_ID")
	private int bonusProgId;
	
	@Column(name="BONUS_TYPE")
	private int bonusType;
	
	@Column(name="BONUS_IS_TYPE")
	private int bonusIsType;

	
	@Transient
	private ProgramFactory programFactory;
	
	
	public long getBonusId() {
		return bonusId;
	}

	public void setBonusId(long bonusId) {
		this.bonusId = bonusId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(double bonusValue) {
		this.bonusValue = bonusValue;
	}

	public int getBonusCount() {
		return bonusCount;
	}

	public void setBonusCount(int bonusCount) {
		this.bonusCount = bonusCount;
	}

	public int getBonusProgId() {
		return bonusProgId;
	}

	public void setBonusProgId(int bonusProgId) {
		this.bonusProgId = bonusProgId;
	}

	public int getBonusType() {
		return bonusType;
	}

	public void setBonusType(int bonusType) {
		this.bonusType = bonusType;
	}

	public int getBonusIsType() {
		return bonusIsType;
	}

	public void setBonusIsType(int bonusIsType) {
		this.bonusIsType = bonusIsType;
	}

	public ProgramFactory getProgramFactory() {
		return programFactory;
	}

	public void setProgramFactory(ProgramFactory programFactory) {
		this.programFactory = programFactory;
	}
  
  
  
  
  
  
}
