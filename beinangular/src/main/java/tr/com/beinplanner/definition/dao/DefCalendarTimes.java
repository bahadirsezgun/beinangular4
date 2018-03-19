package tr.com.beinplanner.definition.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="def_calendar_times")
public class DefCalendarTimes {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CAL_IDX")
	private int calIdx;
	
	@Column(name="START_TIME")
	private String startTime;
	
	@Column(name="END_TIME")
	private String endTime;
	
	@Column(name="DURATION")
	private int duration;
	
	@Column(name="CAL_PERIOD")
	private int calPeriod;
	
	@JsonIgnore
	@Column(name="FIRM_ID")
	private int firmId;

	public int getCalIdx() {
		return calIdx;
	}

	public void setCalIdx(int calIdx) {
		this.calIdx = calIdx;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCalPeriod() {
		return calPeriod;
	}

	public void setCalPeriod(int calPeriod) {
		this.calPeriod = calPeriod;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
	
}
