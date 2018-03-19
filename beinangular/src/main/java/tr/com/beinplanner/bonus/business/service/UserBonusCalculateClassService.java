package tr.com.beinplanner.bonus.business.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.bonus.business.calculator.CalculateService;
import tr.com.beinplanner.bonus.business.calculator.classes.CalculateClassBonusToRate;
import tr.com.beinplanner.bonus.business.calculator.classes.CalculateClassBonusToStatic;
import tr.com.beinplanner.bonus.business.calculator.classes.CalculateClassBonusToStaticRate;
import tr.com.beinplanner.bonus.businessDao.UserBonusObj;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentClass;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.service.UserBonusPaymentClassService;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.service.ScheduleClassService;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.BonusTypes;

@Service
@Scope("prototype")
public class UserBonusCalculateClassService  implements  UserBonusCalculateService {

	@Autowired
	ScheduleClassService scheduleClassService;
	
	@Autowired
	CalculateClassBonusToRate calculateClassBonusToRate;
	
	@Autowired
	CalculateClassBonusToStatic calculateClassBonusToStatic;
	
	@Autowired
	CalculateClassBonusToStaticRate calculateClassBonusToStaticRate;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserBonusPaymentClassService userBonusPaymentClassService;
	
	@SuppressWarnings("unchecked")
	@Override
	public UserBonusObj findStaffBonusObj(long schStaffId, Date startDate, Date endDate,int firmId) {
		
		List<ScheduleTimePlan> scheduleTimePlans=scheduleClassService.findScheduleTimePlansPlanByDatesForStaff(schStaffId, startDate, endDate,firmId);
		
		CalculateService calculateService=null;
		User  staff=userService.findUserById(schStaffId);
	
		if(staff.getBonusTypeC()==BonusTypes.BONUS_IS_TYPE_RATE){
			calculateService=calculateClassBonusToRate;
		}else if(staff.getBonusTypeC()==BonusTypes.BONUS_IS_TYPE_STATIC){
			calculateService=calculateClassBonusToStatic;
		}else if(staff.getBonusTypeC()==BonusTypes.BONUS_IS_TYPE_STATIC_RATE){
			calculateService=calculateClassBonusToStaticRate;
		}
		
		
		
		List<UserBonusPaymentFactory> bonusPaymentClasses=userBonusPaymentClassService.findUserBonusPaymentByDate(schStaffId, startDate, endDate);
		
		double payedAmount=0;
		if(bonusPaymentClasses!=null){
			for (UserBonusPaymentFactory userBonusPaymentFactory : bonusPaymentClasses) {
				payedAmount+=((UserBonusPaymentClass)userBonusPaymentFactory).getBonAmount();
			}
		}
		UserBonusObj userBonusObj=calculateService.calculateIt(scheduleTimePlans,schStaffId,staff.getFirmId());
		userBonusObj.setUserBonusPaymentFactories((List<UserBonusPaymentFactory>)bonusPaymentClasses);
		userBonusObj.setPayedAmount(payedAmount);
		userBonusObj.setBonusType(staff.getBonusTypeC());
		
		
		return userBonusObj;
		
	}

	
	
}
