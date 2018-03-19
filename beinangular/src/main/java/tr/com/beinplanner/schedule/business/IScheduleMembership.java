package tr.com.beinplanner.schedule.business;

import java.util.List;

import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipTimePlan;

public interface IScheduleMembership {

	
	public HmiResultObj createPlan(ScheduleMembershipPlan scheduleFactory);
	
	public HmiResultObj createTimePlan(ScheduleMembershipTimePlan scheduleMembershipTimePlan);
	
	public HmiResultObj deleteTimePlan(ScheduleMembershipTimePlan scheduleMembershipTimePlan);
	
	public HmiResultObj deletePlan(ScheduleMembershipPlan scheduleFactory);
	
	public ScheduleMembershipPlan findSchedulePlanBySaleId(long saleId);
	
	public ScheduleMembershipPlan findSchedulePlanById(long smpId);
	
	public ScheduleMembershipTimePlan findScheduleTimePlanById(long smtpId);
	
	public List<ScheduleMembershipTimePlan> findScheduleTimePlanByPlanId(long smtId);
	
	public List<ScheduleMembershipPlan> findSchedulePlanByUserId(long userId);
	
}
