package tr.com.beinplanner.schedule.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentPersonal;
import tr.com.beinplanner.bonus.service.UserBonusPaymentPersonalService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.repository.ScheduleTimePlanRepository;
import tr.com.beinplanner.schedule.repository.ScheduleUsersPersonalPlanRepository;
import tr.com.beinplanner.schedule.service.ScheduleService;
import tr.com.beinplanner.settings.service.SettingsService;
import tr.com.beinplanner.util.BonusLockUtil;
import tr.com.beinplanner.util.DateTimeUtil;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("schedulePersonalFacade")
public class SchedulePersonalFacade implements SchedulePersonalClassFacadeService {

	@Autowired
	LoginSession loginSession;
	
	@Autowired
	UserBonusPaymentPersonalService userBonusPaymentPersonalService;
	
	@Autowired
	SettingsService settingsService;
	
	@Autowired
	ScheduleUsersPersonalPlanRepository scheduleUsersPersonalPlanRepository;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	ScheduleTimePlanRepository scheduleTimePlanRepository;
	
	
	@Autowired
	@Qualifier("scheduleClassFacade")
	SchedulePersonalClassFacadeService schedulePersonalClassFacadeService;
	
	@Override
	public synchronized HmiResultObj canScheduleChange(long schtId) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(settingsService.findPtLock(loginSession.getUser().getFirmId()).getBonusLock()==BonusLockUtil.BONUS_LOCK_FLAG) {
			ScheduleTimePlan scheduleTimePlan=scheduleService.findScheduleTimePlanById(schtId);
			if(scheduleTimePlan!=null) {
				List<UserBonusPaymentFactory> userBonusPaymentPersonal=userBonusPaymentPersonalService.controlUserBonusPaymentByDate(scheduleTimePlan.getSchtStaffId(), scheduleTimePlan.getPlanStartDate());
			    if(userBonusPaymentPersonal.size()>0) {
			    	hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
					hmiResultObj.setResultMessage("bonusPayedForTimePlan");
				}
			}
		}
		return hmiResultObj;
	}


	@Override
	public synchronized HmiResultObj canScheduleTimePlanCreateInChain(ScheduleTimePlan scheduleTimePlan) {
		// TODO control must be extended if class duration and calendar period not equal each other. 
		
		scheduleTimePlan.setPlanStartDate(DateTimeUtil.setMillisecondTo0(scheduleTimePlan.getPlanStartDate() ));
		
		HmiResultObj hmiResultObj=schedulePersonalClassFacadeService.canScheduleTimePlanCreateInChain(scheduleTimePlan);
		if(hmiResultObj.getResultStatu().equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			ScheduleTimePlan schTPP=scheduleTimePlanRepository.findScheduleTimePlanPersonalPlanByDateTimeForStaff(scheduleTimePlan.getSchtStaffId(), scheduleTimePlan.getPlanStartDate());
			if(schTPP!=null) {
				hmiResultObj.setResultMessage("instructorhaveGotClassesInThisTime");
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultObj(schTPP);
			}
			return hmiResultObj;
		}else {
			return hmiResultObj;
		}
		
	}


	@Override
	public synchronized HmiResultObj canScheduleTimePlanDelete(ScheduleTimePlan scheduleTimePlan) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(settingsService.findPtLock(loginSession.getUser().getFirmId()).getBonusLock()==BonusLockUtil.BONUS_LOCK_FLAG) {
				List<UserBonusPaymentFactory> userBonusPaymentPersonal=userBonusPaymentPersonalService.controlUserBonusPaymentByDate(scheduleTimePlan.getSchtStaffId(), scheduleTimePlan.getPlanStartDate());
			    if(userBonusPaymentPersonal.size()>0) {
			    	hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
					hmiResultObj.setResultMessage("bonusPayedForTimePlan");
				}
			
		}
		return hmiResultObj;
	}
	
	

}
