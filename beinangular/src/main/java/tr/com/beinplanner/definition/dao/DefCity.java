package tr.com.beinplanner.definition.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="def_city")
public class DefCity  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CITY_ID")
	private int cityId;
	
	@Column(name="STATE_ID")
	private int stateId;
	
	@Column(name="CITY_NAME")
	private String cityName;
	
	@Transient
	private boolean selected;
	
	@Column(name="FIRM_ID")
	private int firmId;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	
	
	
}
