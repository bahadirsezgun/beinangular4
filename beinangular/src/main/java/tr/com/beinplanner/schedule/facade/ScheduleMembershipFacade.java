package tr.com.beinplanner.schedule.facade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.businessEntity.ScheduleMembershipFreezeObj;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipTimePlan;
import tr.com.beinplanner.schedule.repository.ScheduleMembershipPlanRepository;
import tr.com.beinplanner.schedule.repository.ScheduleMembershipTimePlanRepository;
import tr.com.beinplanner.util.ProgDurationTypes;
import tr.com.beinplanner.util.ResultStatuObj;
@Service
@Qualifier("scheduleMembershipFacade")
public class ScheduleMembershipFacade implements ScheduleMembershipFacadeService{

	
	@Autowired
	ScheduleMembershipPlanRepository scheduleMembershipPlanRepository;
	
	@Autowired
	ScheduleMembershipTimePlanRepository scheduleMembershipTimePlanRepository;
	
	@Autowired
	LoginSession loginSession;
	
	
	@Override
	public synchronized HmiResultObj canScheduleCreate(ScheduleMembershipPlan scheduleFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		List<ScheduleMembershipPlan> scheduleFactories=scheduleMembershipPlanRepository.findByUserId(((ScheduleMembershipPlan)scheduleFactory).getUserId());
		
		
		for (ScheduleMembershipPlan sf : scheduleFactories) {
			if(sf.getSmpEndDate().after(scheduleFactory.getSmpStartDate()) && sf.getSaleId()!=scheduleFactory.getSaleId()){
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("prevMembershipNotFinished");
				return hmiResultObj;
			}
		}

		return hmiResultObj;
	}

	@Override
	public synchronized HmiResultObj canScheduleFreeze(ScheduleMembershipFreezeObj smp,ScheduleMembershipPlan smpInDb,ProgramMembership pmf) {
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		if(smpInDb.getSmpFreezeCount()>=pmf.getMaxFreezeCount()){
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("maxFreeezeCountExceeded");
			return hmiResultObj;
		}
		
		/*
		int freezeDuration=pmf.getFreezeDuration();
		
		Date freezeStartDate=(Date)smp.getSmpStartDate().clone();
		if(pmf.getFreezeDurationType()==ProgDurationTypes.DURATION_TYPE_MONTHLY){
			if(freezeStartDate.after(ProgDurationTypes.getDateForNextMonth(smp.getSmpEndDate(),freezeDuration*-1))){
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("membershipAlreadyFinished");
				return hmiResultObj;
			}
		}else{
			if(pmf.getFreezeDurationType()==ProgDurationTypes.DURATION_TYPE_WEEKLY){
				freezeDuration=freezeDuration*7;
			}
			
			if(freezeStartDate.after(ProgDurationTypes.getDateForNextDate(smp.getSmpEndDate(),freezeDuration*-1))){
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("membershipAlreadyFinished");
				return hmiResultObj;
			}
		}
		*/
		return hmiResultObj;
	}

	@Override
	public synchronized HmiResultObj canScheduleUnFreeze(ScheduleMembershipPlan smp,ScheduleMembershipTimePlan scheduleMembershipTimePlan,ProgramMembership pmf) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		Date todayDate=new Date();
			if(todayDate.after(ProgDurationTypes.getDateForNextDate(smp.getSmpStartDate(),5))){
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("fiveDaysBeforeFreeze");
				return hmiResultObj;
			}
		return hmiResultObj;
	}

	@Override
	public synchronized HmiResultObj canScheduleDelete(PacketSaleMembership packetSaleFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		ScheduleMembershipPlan smp=(ScheduleMembershipPlan)scheduleMembershipPlanRepository.findBySaleId(packetSaleFactory.getSaleId());
		Date todayDate=new Date();
		if(todayDate.after(ProgDurationTypes.getDateForNextDate(smp.getSmpStartDate(),5))){
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("fiveDaysBeforeDelete");
			return hmiResultObj;
		}
		return hmiResultObj;
	}

}
