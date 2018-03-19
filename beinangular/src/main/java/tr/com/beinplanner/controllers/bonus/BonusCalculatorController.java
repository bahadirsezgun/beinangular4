package tr.com.beinplanner.controllers.bonus;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.bonus.business.service.UserBonusCalculateClassService;
import tr.com.beinplanner.bonus.business.service.UserBonusCalculatePersonalService;
import tr.com.beinplanner.bonus.business.service.UserBonusCalculateService;
import tr.com.beinplanner.bonus.businessDao.UserBonusObj;
import tr.com.beinplanner.bonus.businessDao.UserBonusSearchObj;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.util.BonusTypes;
import tr.com.beinplanner.util.DateTimeUtil;
import tr.com.beinplanner.util.OhbeUtil;

@RestController
@RequestMapping(value="/bein/bonus/calculate")
public class BonusCalculatorController {

	
	@Autowired
	UserBonusCalculateClassService userBonusCalculateClassService;
	
	@Autowired
	UserBonusCalculatePersonalService userBonusCalculatePersonalService;
	
	@Autowired
	LoginSession loginSession;
	
	
	@RequestMapping(value="/findStaffBonus/{bonType}", method = RequestMethod.POST) 
	public @ResponseBody UserBonusObj findStaffBonus(@RequestBody UserBonusSearchObj userBonusSearchObj, @PathVariable("bonType") String bonType ) {
		
		
		UserBonusCalculateService userBonusCalculateService;
		if(bonType.equals(BonusTypes.BONUS_PAYMENT_TYPE_PERSONAL)){
			userBonusCalculateService=userBonusCalculatePersonalService;
		}else{
			userBonusCalculateService=userBonusCalculateClassService;
		}
		
		if(userBonusSearchObj.getQueryType()==1){ // AYLIK SORGULAMA
			
			int year=userBonusSearchObj.getYear();
			int month=userBonusSearchObj.getMonth();
			String monthStr=""+month;
			if(month<10)
				 monthStr="0"+month;
			
			String startDateStr="01/"+monthStr+"/"+year;
			Date startDate=DateTimeUtil.getThatDayFormatNotNull(startDateStr, "dd/MM/yyyy");
			Date endDate=DateTimeUtil.getThatDayFormatNotNull(startDateStr, "dd/MM/yyyy");
			endDate=OhbeUtil.getDateForNextMonth(endDate, 1);
			
			userBonusSearchObj.setStartDate(startDate);
			userBonusSearchObj.setEndDate(endDate);
			
		}else{
			
			Date startDate=userBonusSearchObj.getStartDate();// DateTimeUtil.getThatDayFormatNotNull(userBonusSearchObj.getStartDateStr() , GlobalUtil.global.getPtScrDateFormat());
			Date endDate=userBonusSearchObj.getEndDate();//=DateTimeUtil.getThatDayFormatNotNull(userBonusSearchObj.getEndDateStr() , GlobalUtil.global.getPtScrDateFormat());
			endDate=OhbeUtil.getDateForNextDate(endDate, 1);
			
			userBonusSearchObj.setStartDate(startDate);
			userBonusSearchObj.setEndDate(endDate);
			
		}
		
		return userBonusCalculateService.findStaffBonusObj(userBonusSearchObj.getSchStaffId(), userBonusSearchObj.getStartDate(), userBonusSearchObj.getEndDate(),loginSession.getUser().getFirmId());
	}
}
