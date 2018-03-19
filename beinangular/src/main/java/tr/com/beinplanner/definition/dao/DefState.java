package tr.com.beinplanner.definition.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="def_state")
public class DefState {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="STATE_ID")
	private int stateId;
	
	@Column(name="STATE_NAME")
	private String stateName;
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();
	@Transient
	private String createTimeStr;
	@Transient
	private boolean selected;
	
	@Column(name="FIRM_ID")
	private int firmId;
	
	@OneToMany(mappedBy="stateId")
	private List<DefCity> defCities;

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public List<DefCity> getDefCities() {
		return defCities;
	}

	public void setDefCities(List<DefCity> defCities) {
		this.defCities = defCities;
	}
	
	
}
