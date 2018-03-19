package tr.com.beinplanner.dashboard.businessEntity;

import java.util.List;

import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;

public class LastClasses{

	
	private List<ScheduleTimePlan> stpTW;
	private List<ScheduleTimePlan> stpNW;
	
	private List<ScheduleMembershipPlan> stpMTW;
	private List<ScheduleMembershipPlan> stpMNW;
	
	public List<ScheduleTimePlan> getStpTW() {
		return stpTW;
	}
	public void setStpTW(List<ScheduleTimePlan> stpTW) {
		this.stpTW = stpTW;
	}
	public List<ScheduleTimePlan> getStpNW() {
		return stpNW;
	}
	public void setStpNW(List<ScheduleTimePlan> stpNW) {
		this.stpNW = stpNW;
	}
	public List<ScheduleMembershipPlan> getStpMTW() {
		return stpMTW;
	}
	public void setStpMTW(List<ScheduleMembershipPlan> stpMTW) {
		this.stpMTW = stpMTW;
	}
	public List<ScheduleMembershipPlan> getStpMNW() {
		return stpMNW;
	}
	public void setStpMNW(List<ScheduleMembershipPlan> stpMNW) {
		this.stpMNW = stpMNW;
	}
	
	
	
}
