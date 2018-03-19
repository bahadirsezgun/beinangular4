package tr.com.beinplanner.schedule.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentClass;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.service.UserBonusPaymentClassService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.repository.ScheduleTimePlanRepository;
import tr.com.beinplanner.schedule.repository.ScheduleUsersClassPlanRepository;
import tr.com.beinplanner.schedule.service.ScheduleService;
import tr.com.beinplanner.settings.service.SettingsService;
import tr.com.beinplanner.util.BonusLockUtil;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("scheduleClassFacade")
public class ScheduleClassFacade implements SchedulePersonalClassFacadeService {

	@Autowired
	LoginSession loginSession;
	
	@Autowired
	UserBonusPaymentClassService userBonusPaymentClassService;
	
	@Autowired
	SettingsService settingsService;
	
	@Autowired
	ScheduleUsersClassPlanRepository scheduleUsersClassPlanRepository;
	

	@Autowired
	ScheduleTimePlanRepository scheduleTimePlanRepository;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Override
	public synchronized HmiResultObj canScheduleChange(long schtId) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(settingsService.findPtLock(loginSession.getUser().getFirmId()).getBonusLock()==BonusLockUtil.BONUS_LOCK_FLAG) {
			ScheduleTimePlan scheduleTimePlan=scheduleService.findScheduleTimePlanById(schtId);
			if(scheduleTimePlan!=null) {
				List<UserBonusPaymentFactory> userBonusPaymentClass=userBonusPaymentClassService.controlUserBonusPaymentByDate(scheduleTimePlan.getSchtStaffId(), scheduleTimePlan.getPlanStartDate());
			    if(userBonusPaymentClass.size()>0) {
			    	hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
					hmiResultObj.setResultMessage("bonusPayedForTimePlan");
				}
			}
		}
		return hmiResultObj;
	}

	@Override
	public synchronized HmiResultObj canScheduleTimePlanCreateInChain(ScheduleTimePlan scheduleTimePlan) {
		    HmiResultObj hmiResultObj=new HmiResultObj();
			ScheduleTimePlan schTPP=scheduleTimePlanRepository.findScheduleTimePlanClassPlanByDateTimeForStaff(scheduleTimePlan.getSchtStaffId(), scheduleTimePlan.getPlanStartDate());
			if(schTPP!=null) {
				hmiResultObj.setResultMessage("instructorhaveGotClassesInThisTime");
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultObj(schTPP);
			}else {
				hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			}
			return hmiResultObj;
		
	}

	@Override
	public synchronized HmiResultObj canScheduleTimePlanDelete(ScheduleTimePlan scheduleTimePlan) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(settingsService.findPtLock(loginSession.getUser().getFirmId()).getBonusLock()==BonusLockUtil.BONUS_LOCK_FLAG) {
				List<UserBonusPaymentFactory> userBonusPaymentClass=userBonusPaymentClassService.controlUserBonusPaymentByDate(scheduleTimePlan.getSchtStaffId(), scheduleTimePlan.getPlanStartDate());
			    if(userBonusPaymentClass.size()>0) {
			    	hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
					hmiResultObj.setResultMessage("bonusPayedForTimePlan");
				}
			
		}
		return hmiResultObj;
	}

	
}
