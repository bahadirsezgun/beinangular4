package tr.com.beinplanner.controllers.stripe;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;

import tr.com.beinplanner.definition.dao.DefFirm;
import tr.com.beinplanner.definition.service.DefinitionService;
import tr.com.beinplanner.settings.dao.PtGlobal;
import tr.com.beinplanner.settings.dao.PtLock;
import tr.com.beinplanner.settings.dao.PtRules;
import tr.com.beinplanner.settings.service.SettingsService;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.BonusLockUtil;
@RestController
@RequestMapping("/stripe")
public class StripePaymentController {

	@Autowired
	UserService userService;
	
	@Autowired
	DefinitionService definitionService;
	
	
	@Autowired
	SettingsService settingsService;
	
	@RequestMapping(value="/charge", method = RequestMethod.POST) 
	public void chargeFromCard(HttpServletRequest request,HttpServletResponse response) throws IOException  {
		Stripe.apiKey = "sk_test_zRhW35HHPrDAaF1LZzBUsrap";
		
		String token = request.getParameter("stripeToken");
		String email = request.getParameter("stripeEmail");
		
		String dataAmount = request.getParameter("dataAmount");
		String currency = request.getParameter("dataAmount");
		String description = request.getParameter("description");
		String restriction = request.getParameter("restriction");
		String plan = request.getParameter("plan");
		String lang = request.getParameter("lang");

		if(userService.findUserByUserEmail(email).isPresent()) {
			response.sendRedirect("/firmCreatedBeforeException");
		}else {
		
			DefFirm df=definitionService.findFirmByEMail(email);
			if(df!=null) {
				response.sendRedirect("/firmCreatedBeforeException");
			}else {
			
				boolean isSubscriptionDone=false;
				
				// Charge the user's card:
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("amount", dataAmount);
				params.put("currency", currency);
				params.put("description", description);
				params.put("source", token);
				String stripeCustId="";
				//Charge charge=null;
				try {
				//	charge = Charge.create(params);
					
					
					Map<String, Object> customerParams = new HashMap<String, Object>();
					customerParams.put("email", email);
					customerParams.put("description", description);
					customerParams.put("source", token);
					// ^ obtained with Stripe.js
					Customer customer= Customer.create(customerParams);
					stripeCustId=customer.getId();
					
					DefFirm defFirm=new DefFirm();
					defFirm.setFirmCityName("");
					defFirm.setFirmStateName("");
					defFirm.setCreateTime(new Date());
					defFirm.setFirmAddress("");
					defFirm.setFirmApproved(0);
					defFirm.setFirmAuthPerson("-");
					defFirm.setFirmEmail(email);
					defFirm.setFirmGroupId(0);
					defFirm.setFirmName("");
					defFirm.setFirmPhone("");
					defFirm.setFirmRestriction(Integer.parseInt(restriction));
					defFirm.setStripeCustId(stripeCustId);
					
					defFirm=definitionService.createFirm(defFirm);
					
					
					createDefaultFirmSettings(defFirm, currency, lang);
					
					
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("plan", plan);
		
					System.out.println("plan is "+plan);
					
					Map<String, Object> items = new HashMap<String, Object>();
					items.put("0", item);
		
					Map<String, Object> paramPlans = new HashMap<String, Object>();
					paramPlans.put("customer", stripeCustId);
					paramPlans.put("items", items);
		
					Subscription subscription=  Subscription.create(paramPlans);
					
					isSubscriptionDone=true;
				} catch (AuthenticationException e) {
					e.printStackTrace();
				} catch (InvalidRequestException e) {
					e.printStackTrace();
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (CardException e) {
					e.printStackTrace();
				} catch (APIException e) {
					e.printStackTrace();
				}
				
				if(isSubscriptionDone)
				   response.sendRedirect("/register");
				else
					response.sendRedirect("/firmCreatedBeforeException");
			}

		}
		
		
		//System.out.println(charge.getCustomer());
	}
	
	
	
	private void createDefaultFirmSettings(DefFirm defFirm,String currency,String lang) {
		PtGlobal ptGlobal=new PtGlobal();
		ptGlobal.setFirmId(defFirm.getFirmId());
		ptGlobal.setPtCurrency(currency);
		ptGlobal.setPtDateFormat("%d/%m/%Y");
		ptGlobal.setPtStaticIp("127.0.0.1");
		ptGlobal.setPtTz("Europe/Istanbul");
		ptGlobal.setPtLang(lang);
		settingsService.createPtGlobal(ptGlobal);
		
		
		
		PtLock ptLock=new PtLock();
		ptLock.setBonusLock(BonusLockUtil.BONUS_LOCK_FLAG);
		ptLock.setFirmId(defFirm.getFirmId());						
		settingsService.createPtLock(ptLock);
		
		
		PtRules pt1=new PtRules();
		pt1.setRuleId(1);
		pt1.setRuleName("noClassBeforePayment");
		pt1.setRuleValue(0);
		pt1.setFirmId(defFirm.getFirmId());
		try {
			pt1=settingsService.createPtRules(pt1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		PtRules pt2=new PtRules();
		pt2.setRuleId(2);
		pt2.setRuleName("noChangeAfterBonusPayment");
		pt2.setRuleValue(1);
		pt2.setFirmId(defFirm.getFirmId());
		pt2.setPtrId(0);
		try {
			pt2=settingsService.createPtRules(pt2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		PtRules pt3=new PtRules();
		pt3.setRuleId(3);
		pt3.setRuleName("payBonusForConfirmedPayment");
		pt3.setRuleValue(1);
		pt3.setFirmId(defFirm.getFirmId());
		pt3.setPtrId(0);
		pt3=settingsService.createPtRules(pt3);
		System.out.println("pt3 :"+pt3.getRuleName());
		
		PtRules pt4=new PtRules();
		pt4.setRuleId(4);
		pt4.setRuleName("taxRule");
		pt4.setRuleValue(0);
		pt4.setFirmId(defFirm.getFirmId());
		pt4.setPtrId(0);
		settingsService.createPtRules(pt4);
		
		PtRules pt5=new PtRules();
		pt5.setRuleId(5);
		pt5.setRuleName("location");
		pt5.setRuleValue(0);
		pt5.setFirmId(defFirm.getFirmId());
		pt5.setPtrId(0);
		settingsService.createPtRules(pt5);
		
		PtRules pt6=new PtRules();
		pt6.setRuleId(6);
		pt6.setRuleName("notice");
		pt6.setRuleValue(0);
		pt6.setFirmId(defFirm.getFirmId());
		pt6.setPtrId(0);
		settingsService.createPtRules(pt6);
		
		PtRules pt7=new PtRules();
		pt7.setRuleId(7);
		pt7.setRuleName("creditCardCommission");
		pt7.setRuleValue(0);
		pt7.setFirmId(defFirm.getFirmId());
		pt7.setPtrId(0);
		settingsService.createPtRules(pt7);
		
		PtRules pt8=new PtRules();
		pt8.setRuleId(8);
		pt8.setRuleName("creditCardCommissionRate");
		pt8.setRuleValue(0);
		pt8.setFirmId(defFirm.getFirmId());
		pt8.setPtrId(0);
		settingsService.createPtRules(pt8);
		
		PtRules pt9=new PtRules();
		pt9.setRuleId(9);
		pt9.setRuleName("noSaleToPlanning");
		pt9.setRuleValue(1);
		pt9.setFirmId(defFirm.getFirmId());
		pt9.setPtrId(0);
		settingsService.createPtRules(pt9);
		
	}
	
	
	
}
