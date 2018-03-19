package tr.com.beinplanner.controllers.bonus;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.bonus.businessDao.UserBonusSearchObj;
import tr.com.beinplanner.bonus.businessDao.UserPaymentObj;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.service.IUserBonusPayment;
import tr.com.beinplanner.bonus.service.UserBonusPaymentClassService;
import tr.com.beinplanner.bonus.service.UserBonusPaymentPersonalService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.BonusTypes;

@RestController
@RequestMapping(value="/bein/bonus/payment")
public class BonusPaymentController {

	
	@Autowired
	UserBonusPaymentPersonalService userBonusPaymentPersonalService;
	
	@Autowired
	UserBonusPaymentClassService userBonusPaymentClassService;
	
	@Autowired
	LoginSession loginSession;
	
	
	@RequestMapping(value="/findUserPaymentDetail/{bonType}/{schtId}", method = RequestMethod.POST) 
	public @ResponseBody UserPaymentObj findUserPaymentDetail(@PathVariable("bonType") String bonType,@PathVariable("schtId") long schtId  ) {
		
		IUserBonusPayment iUserBonusPayment;
		if(bonType.equals(BonusTypes.BONUS_PAYMENT_TYPE_PERSONAL)){
			iUserBonusPayment=userBonusPaymentPersonalService;
		}else{
			iUserBonusPayment=userBonusPaymentClassService;
		}
		
		return iUserBonusPayment.findUserPayment(schtId);
	}
	
	
	@RequestMapping(value="/saveBonusPayment/{bonType}", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj saveBonusPayment(@RequestBody UserBonusPaymentFactory userBonusPaymentFactory, @PathVariable("bonType") String bonType  ) {
		
		IUserBonusPayment iUserBonusPayment;
		if(bonType.equals(BonusTypes.BONUS_PAYMENT_TYPE_PERSONAL)){
			iUserBonusPayment=userBonusPaymentPersonalService;
		}else{
			iUserBonusPayment=userBonusPaymentClassService;
		}
		
		userBonusPaymentFactory.setFirmId(loginSession.getUser().getFirmId());
		
		return iUserBonusPayment.saveBonusPayment(userBonusPaymentFactory);
		
		
	}
	
	@RequestMapping(value="/deleteBonusPayment/{bonType}", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj deleteBonusPayment(@RequestBody UserBonusPaymentFactory userBonusPaymentFactory, @PathVariable("bonType") String bonType  ) {
	
		IUserBonusPayment iUserBonusPayment;
		if(bonType.equals(BonusTypes.BONUS_PAYMENT_TYPE_PERSONAL)){
			iUserBonusPayment=userBonusPaymentPersonalService;
		}else{
			iUserBonusPayment=userBonusPaymentClassService;
		}
		userBonusPaymentFactory.setFirmId(loginSession.getUser().getFirmId());
		
		return iUserBonusPayment.deleteBonusPayment(userBonusPaymentFactory);
		
	}
	
	@RequestMapping(value="/findStaffBonusPayment/{bonType}", method = RequestMethod.POST) 
	public @ResponseBody List<UserBonusPaymentFactory> findStaffBonusPayment(@RequestBody UserBonusSearchObj userBonusSearchObj, @PathVariable("bonType") String bonType  ) {
		
		IUserBonusPayment iUserBonusPayment;
		if(bonType.equals(BonusTypes.BONUS_PAYMENT_TYPE_PERSONAL)){
			iUserBonusPayment=userBonusPaymentPersonalService;
		}else{
			iUserBonusPayment=userBonusPaymentClassService;
		}
		
	
		return iUserBonusPayment.findStaffBonusPayment(userBonusSearchObj);
		
	}
	
	
}
