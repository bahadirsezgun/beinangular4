package tr.com.beinplanner.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.business.IScheduleMembership;
import tr.com.beinplanner.schedule.businessEntity.ScheduleMembershipFreezeObj;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipTimePlan;
import tr.com.beinplanner.schedule.facade.ScheduleMembershipFacade;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ProgDurationTypes;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("scheduleMembershipService")
public class ScheduleMembershipService {

	@Autowired
	@Qualifier("scheduleMembership")
	IScheduleMembership iScheduleMembership;
	
	@Autowired
	ProgramService programService;
	
	
	@Autowired
	@Qualifier("scheduleMembershipFacade")
	ScheduleMembershipFacade scheduleMembershipFacade;
	
	
	public synchronized ScheduleFactory findScheduleFactoryPlanBySaleId(long saleId) {
		return iScheduleMembership.findSchedulePlanBySaleId(saleId);
	}
	
	public synchronized ScheduleFactory findPlanById(long smpId) {
		return iScheduleMembership.findSchedulePlanById(smpId);
	}
	
	
	public synchronized ScheduleMembershipTimePlan findTimePlanById(long smtpId) {
		return iScheduleMembership.findScheduleTimePlanById(smtpId);
	}
	
	public synchronized List<ScheduleMembershipTimePlan> findTimePlanByPlanId(long smpId) {
		return iScheduleMembership.findScheduleTimePlanByPlanId(smpId);
	}
	
	public synchronized HmiResultObj createPlan(ScheduleMembershipPlan scheduleMembershipPlan) {
		
		
		
		ProgramMembership programMembership=programService.findProgramMembershipById(scheduleMembershipPlan.getProgId());
		
		Date smpEndDate=(Date)scheduleMembershipPlan.getSmpStartDate().clone();
		
		if(programMembership.getProgDurationType()==ProgDurationTypes.DURATION_TYPE_DAILY){
			smpEndDate=ProgDurationTypes.getDateForNextDate(smpEndDate, programMembership.getProgDuration());
		}else if(programMembership.getProgDurationType()==ProgDurationTypes.DURATION_TYPE_WEEKLY){
			smpEndDate=ProgDurationTypes.getDateForNextDate(smpEndDate, programMembership.getProgDuration()*7);
		}else if(programMembership.getProgDurationType()==ProgDurationTypes.DURATION_TYPE_MONTHLY){
			smpEndDate=ProgDurationTypes.getDateForNextMonth(smpEndDate, programMembership.getProgDuration());
		}
		scheduleMembershipPlan.setSmpEndDate(smpEndDate);
		
		HmiResultObj hmiResultObj= scheduleMembershipFacade.canScheduleCreate(scheduleMembershipPlan);
		
		if(hmiResultObj.getResultStatu()==ResultStatuObj.RESULT_STATU_FAIL_STR){
			return hmiResultObj;
		}
		
		hmiResultObj=iScheduleMembership.createPlan(scheduleMembershipPlan);
		
		
		
		
		return hmiResultObj;
		
		
		
	}
	
	
	public synchronized HmiResultObj updatePlan(ScheduleMembershipPlan scheduleMembershipPlan) {
		HmiResultObj hmiResultObj= scheduleMembershipFacade.canScheduleCreate(scheduleMembershipPlan);
		if(hmiResultObj.getResultStatu()==ResultStatuObj.RESULT_STATU_FAIL_STR){
			return hmiResultObj;
		}
		hmiResultObj=iScheduleMembership.createPlan(scheduleMembershipPlan);
		return hmiResultObj;
	}
	
	public synchronized HmiResultObj createTimePlan(ScheduleMembershipTimePlan scheduleMembershipTimePlan) {
		return iScheduleMembership.createTimePlan(scheduleMembershipTimePlan);
	}
	
	public synchronized HmiResultObj deleteTimePlan(ScheduleMembershipTimePlan scheduleMembershipTimePlan) {
		return iScheduleMembership.deleteTimePlan(scheduleMembershipTimePlan);
	}
	
	
	public synchronized HmiResultObj freezeSchedule(@RequestBody ScheduleMembershipFreezeObj smp){
		HmiResultObj hmiResultObj=new HmiResultObj();
		ScheduleMembershipPlan smpInDb=(ScheduleMembershipPlan)findPlanById(smp.getSmpId());
		ProgramMembership pmf=programService.findProgramMembershipById(smpInDb.getProgId());
		
		hmiResultObj=scheduleMembershipFacade.canScheduleFreeze(smp, smpInDb, pmf);
		
		
		if(hmiResultObj.getResultStatu().equals(ResultStatuObj.RESULT_STATU_FAIL_STR)){
			return hmiResultObj;
		}else{
		
			int freezeDuration=pmf.getFreezeDuration();
			Date freezeEndDate=new Date();
			Date smpEndDate=new Date();
			Date freezeStartDate=(Date)smp.getSmpStartDate().clone();
			if(pmf.getFreezeDurationType()==ProgDurationTypes.DURATION_TYPE_MONTHLY){
				freezeEndDate=OhbeUtil.getDateForNextMonth(freezeStartDate, freezeDuration);
				smpEndDate=OhbeUtil.getDateForNextMonth(smpInDb.getSmpEndDate(), freezeDuration);
			}else{
				if(pmf.getFreezeDurationType()==ProgDurationTypes.DURATION_TYPE_WEEKLY){
					freezeDuration=freezeDuration*7;
				}
				freezeEndDate=OhbeUtil.getDateForNextDate(freezeStartDate, freezeDuration);
				smpEndDate=OhbeUtil.getDateForNextDate(smpInDb.getSmpEndDate(), freezeDuration);
			}
			
			
			
			smpInDb.setSmpEndDate(smpEndDate);
			smpInDb.setSmpFreezeCount(smpInDb.getSmpFreezeCount()+1);
			hmiResultObj=updatePlan(smpInDb);
			
			if(hmiResultObj.getResultStatu().equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
				ScheduleMembershipPlan smpR=(ScheduleMembershipPlan) hmiResultObj.getResultObj();
				
				ScheduleMembershipTimePlan scheduleMembershipTimePlan=new ScheduleMembershipTimePlan();
				scheduleMembershipTimePlan.setSmpEndDate(freezeEndDate);
				scheduleMembershipTimePlan.setSmpStartDate(freezeStartDate);
				scheduleMembershipTimePlan.setSmpId(smpR.getSmpId());
				scheduleMembershipTimePlan.setSmpComment(smpR.getSmpComment());
				
				hmiResultObj=createTimePlan(scheduleMembershipTimePlan);
			}
		}
		return hmiResultObj;
	}
	
	public synchronized HmiResultObj unFreezeSchedule(@PathVariable("smtpId") long smtpId,@PathVariable("smpId") long smpId) {
		
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		
		
		ScheduleMembershipPlan smp=(ScheduleMembershipPlan)findPlanById(smpId);
		ScheduleMembershipTimePlan scheduleMembershipTimePlan=findTimePlanById(smtpId);
		ProgramMembership pmf=programService.findProgramMembershipById(smp.getProgId());
		
		
		hmiResultObj=scheduleMembershipFacade.canScheduleUnFreeze(smp, scheduleMembershipTimePlan, pmf);
		
		if(hmiResultObj.getResultStatu()==ResultStatuObj.RESULT_STATU_FAIL_STR){
			return hmiResultObj;
		}else{
		
				int freezeDuration=pmf.getFreezeDuration();
				
				if(pmf.getFreezeDurationType()==ProgDurationTypes.DURATION_TYPE_MONTHLY){
					smp.setSmpEndDate(OhbeUtil.getDateForNextMonth(smp.getSmpEndDate(), freezeDuration*-1));
				}else{
					if(pmf.getFreezeDurationType()==ProgDurationTypes.DURATION_TYPE_WEEKLY){
						freezeDuration=freezeDuration*7;
					}
					smp.setSmpEndDate(OhbeUtil.getDateForNextDate(smp.getSmpEndDate(), freezeDuration*-1));
				}
				
				smp.setSmpFreezeCount(smp.getSmpFreezeCount()-1);
				updatePlan(smp);
				
				hmiResultObj=deleteTimePlan(scheduleMembershipTimePlan);
		}
		return hmiResultObj;
	}
	
	
}
