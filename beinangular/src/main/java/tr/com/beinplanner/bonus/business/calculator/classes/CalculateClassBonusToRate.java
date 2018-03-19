package tr.com.beinplanner.bonus.business.calculator.classes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.bonus.business.calculator.CalculateService;
import tr.com.beinplanner.bonus.businessDao.UserBonusDetailObj;
import tr.com.beinplanner.bonus.businessDao.UserBonusObj;
import tr.com.beinplanner.definition.dao.DefBonus;
import tr.com.beinplanner.definition.service.DefinitionService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetpayment.business.PacketPaymentClassBusiness;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentClass;
import tr.com.beinplanner.packetpayment.service.PacketPaymentService;
import tr.com.beinplanner.packetsale.business.PacketSaleClassBusiness;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.service.PacketSaleService;
import tr.com.beinplanner.program.dao.ProgramClass;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.dao.ScheduleUsersClassPlan;
import tr.com.beinplanner.schedule.service.ScheduleClassService;
import tr.com.beinplanner.settings.dao.PtRules;
import tr.com.beinplanner.util.BonusTypes;
import tr.com.beinplanner.util.PayTypeUtil;
import tr.com.beinplanner.util.PaymentConfirmUtil;
import tr.com.beinplanner.util.RuleUtil;
import tr.com.beinplanner.util.StatuTypes;

@Service
@Scope("prototype")
public class CalculateClassBonusToRate implements CalculateService {

	@Autowired
	ScheduleClassService scheduleClassService;
	
	@Autowired
	LoginSession loginSession;
	
	@Autowired
	DefinitionService definitionService;
	
	@Autowired
	PacketSaleService packetSaleService;
	
	@Autowired
	PacketSaleClassBusiness packetSaleClassBusiness;
	
	@Autowired
	ProgramService programService;
	
	@Autowired
	PacketPaymentService packetPaymentService;
	
	@Autowired
	PacketPaymentClassBusiness packetPaymentClassBusiness;
	
	@Override
	public UserBonusObj calculateIt(List<ScheduleTimePlan> scheduleTimePlans,long staffId,int firmId) {
		UserBonusObj userBonusObj=new UserBonusObj();
		
		PtRules ptRulesBonusForConfirmedPayment=loginSession.getPtRules()
				 								.stream()
				 								.filter(ptr->ptr.getRuleId()==RuleUtil.rulePayBonusForConfirmedPayment)
				 								.findFirst().get();
		PtRules ptRulesCreditCardCommissionRate=loginSession.getPtRules()
					.stream()
					.filter(ptr->ptr.getRuleId()==RuleUtil.ruleCreditCardCommissionRate)
					.findFirst().get();

		PtRules ptRulesCreditCardCommission=loginSession.getPtRules()
					.stream()
					.filter(ptr->ptr.getRuleId()==RuleUtil.ruleCreditCardCommission)
					.findFirst().get();


		
		
		int bonusPaymentRule=ptRulesBonusForConfirmedPayment.getRuleValue();
		int creditCardCommissionRate=ptRulesCreditCardCommissionRate.getRuleValue();
		int creditCardCommission=ptRulesCreditCardCommission.getRuleValue();
		
		
		userBonusObj.setBonusPaymentRule(bonusPaymentRule);
		userBonusObj.setCreditCardCommissionRate(creditCardCommissionRate);
		userBonusObj.setCreditCardCommissionRule(creditCardCommission);
		
		
		List<DefBonus> defBonuses= definitionService.findByUserIdAndBonusTypeAndBonusIsType(staffId, BonusTypes.BONUS_TYPE_CLASS, BonusTypes.BONUS_IS_TYPE_RATE);
		int i=1;
		
		double willPayAmount=0;
		
		List<UserBonusDetailObj> userBonusDetailObjs=new ArrayList<UserBonusDetailObj>();
		
		for (ScheduleTimePlan scheduleTimePlan : scheduleTimePlans) {
			if(scheduleTimePlan.getStatuTp()!=StatuTypes.TIMEPLAN_POSTPONE){
				
				ProgramClass programClass=programService.findProgramClassByTimePlan(scheduleTimePlan.getSchtId());
				
				
			UserBonusDetailObj userBonusDetailObj=new UserBonusDetailObj();
			
			double bonusRate=0;
			for (DefBonus defBonus : defBonuses) {
				if(defBonus.getBonusCount()>=i){
					bonusRate=defBonus.getBonusValue();
					break;
				}
			}
			
			
			
			List<ScheduleFactory> usersInTimePlan=scheduleClassService.findScheduleUsersPlanBySchtId(scheduleTimePlan.getSchtId());
			userBonusDetailObj.setScheduleFactories(usersInTimePlan);
			double totalTimePlanPayment=0;
			
			for (ScheduleFactory scheduleFactory : usersInTimePlan) {
				PacketSaleClass packetSaleClass=(PacketSaleClass)(packetSaleService.findPacketSaleById(((ScheduleUsersClassPlan)scheduleFactory).getSaleId(),packetSaleClassBusiness));
				PacketPaymentClass packetPaymentClass=(PacketPaymentClass)packetPaymentService.findPacketPaymentBySaleId(packetSaleClass.getSaleId(),packetPaymentClassBusiness);
				
				
				((ScheduleUsersClassPlan)scheduleFactory).setProgramFactory(programClass);
				((ScheduleUsersClassPlan)scheduleFactory).setPacketSaleFactory(packetSaleClass);
				((ScheduleUsersClassPlan)scheduleFactory).setPacketPaymentFactory(packetPaymentClass);
				
				double unitPrice=0;
				int saleCount=0;
				
				if(packetPaymentClass!=null){
					
					if(bonusPaymentRule==RuleUtil.RULE_OK){
						if(packetPaymentClass.getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM){
							unitPrice=packetPaymentClass.getPayAmount()/packetSaleClass.getProgCount();
						}
					}else{
						unitPrice=packetPaymentClass.getPayAmount()/packetSaleClass.getProgCount();
					}
					
					if(userBonusObj.getCreditCardCommissionRule()==RuleUtil.RULE_OK){
						
						if(packetPaymentClass.getPayType()==PayTypeUtil.PAY_TYPE_CREDIT_CARD){
							double commissionRate=((100d- Double.parseDouble(""+userBonusObj.getCreditCardCommissionRate()))/100);
							
							unitPrice=unitPrice*commissionRate;
						}
					}
					
					totalTimePlanPayment+=unitPrice;
				}
				((ScheduleUsersClassPlan)scheduleFactory).setUnitPrice(unitPrice);
				((ScheduleUsersClassPlan)scheduleFactory).setSaleCount(packetSaleClass.getProgCount());
			}
			
			userBonusDetailObj.setSchCount(scheduleTimePlan.getSchCount());
			userBonusDetailObj.setPlanStartDate(scheduleTimePlan.getPlanStartDate());
			userBonusDetailObj.setClassCount(i);
			userBonusDetailObj.setProgName(programClass.getProgName());
			userBonusDetailObj.setBonusValue(bonusRate);
			userBonusDetailObj.setPacketUnitPrice(totalTimePlanPayment);
			userBonusDetailObj.setStaffPaymentAmount(totalTimePlanPayment*(bonusRate/100));
			userBonusDetailObj.setSchtId(scheduleTimePlan.getSchtId());
			
			willPayAmount+=userBonusDetailObj.getStaffPaymentAmount();
			
			userBonusDetailObjs.add(userBonusDetailObj);
		   i++;	
		}
		}
		
		
		userBonusObj.setWillPayAmount(willPayAmount);
		userBonusObj.setSchStaffId(staffId);
		userBonusObj.setUserBonusDetailObjs(userBonusDetailObjs);
		return userBonusObj;
	}

	
}
