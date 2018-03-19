package tr.com.beinplanner.bonus.business.calculator.personal;

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
import tr.com.beinplanner.packetpayment.business.PacketPaymentPersonalBusiness;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonal;
import tr.com.beinplanner.packetpayment.service.PacketPaymentService;
import tr.com.beinplanner.packetsale.business.PacketSalePersonalBusiness;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.packetsale.service.PacketSaleService;
import tr.com.beinplanner.program.dao.ProgramPersonal;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.dao.ScheduleUsersPersonalPlan;
import tr.com.beinplanner.schedule.service.SchedulePersonalService;
import tr.com.beinplanner.settings.dao.PtRules;
import tr.com.beinplanner.util.BonusTypes;
import tr.com.beinplanner.util.PayTypeUtil;
import tr.com.beinplanner.util.PaymentConfirmUtil;
import tr.com.beinplanner.util.RuleUtil;
import tr.com.beinplanner.util.StatuTypes;
@Service
@Scope("prototype")
public class CalculatePersonalBonusToRate implements CalculateService {

	@Autowired
	SchedulePersonalService schedulePersonalService;
	
	
	@Autowired
	LoginSession loginSession;
	
	@Autowired
	DefinitionService definitionService;
	
	@Autowired
	PacketSaleService packetSaleService;
	
	@Autowired
	PacketPaymentService packetPaymentService;
	
	@Autowired
	PacketSalePersonalBusiness packetSalePersonalBusiness;
	
	@Autowired
	PacketPaymentPersonalBusiness packetPaymentPersonalBusiness;
	
	@Autowired
	ProgramService programService;
	
	
	
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

		List<DefBonus> defBonuses= definitionService.findByUserIdAndBonusTypeAndBonusIsType(staffId, BonusTypes.BONUS_TYPE_PERSONAL, BonusTypes.BONUS_IS_TYPE_RATE);
		
		int i=1;
		
		double willPayAmount=0;
		
		List<UserBonusDetailObj> userBonusDetailObjs=new ArrayList<UserBonusDetailObj>();
		
		for (ScheduleTimePlan scheduleTimePlan : scheduleTimePlans) {
			
			UserBonusDetailObj userBonusDetailObj=new UserBonusDetailObj();
			
			ProgramPersonal programPersonal=programService.findProgramPersonalByTimePlan(scheduleTimePlan.getSchtId());
			
			double bonusRate=0;
			for (DefBonus defBonus : defBonuses) {
				if(defBonus.getBonusCount()>=i){
					bonusRate=defBonus.getBonusValue();
					break;
				}
			}
			
			if(scheduleTimePlan.getStatuTp()!=StatuTypes.TIMEPLAN_POSTPONE){
			
			List<ScheduleFactory> usersInTimePlan=schedulePersonalService.findScheduleUsersPlanBySchtId(scheduleTimePlan.getSchtId());
			
			
			
			userBonusDetailObj.setScheduleFactories(usersInTimePlan);
			double totalTimePlanPayment=0;
			
			for (ScheduleFactory scheduleFactory : usersInTimePlan) {
				
				PacketSalePersonal packetSalePersonal=(PacketSalePersonal)packetSaleService.findPacketSaleById(((ScheduleUsersPersonalPlan)scheduleFactory).getSaleId(),packetSalePersonalBusiness);
				PacketPaymentPersonal packetPaymentPersonal=(PacketPaymentPersonal)packetPaymentService.findPacketPaymentBySaleId(packetSalePersonal.getSaleId(),packetPaymentPersonalBusiness);
				
				((ScheduleUsersPersonalPlan)scheduleFactory).setProgramFactory(programPersonal);
				((ScheduleUsersPersonalPlan)scheduleFactory).setPacketSaleFactory(packetSalePersonal);
				((ScheduleUsersPersonalPlan)scheduleFactory).setPacketPaymentFactory(packetPaymentPersonal);
				double unitPrice=0;
				int saleCount=0;
				
				if(packetPaymentPersonal!=null){
					
					if(bonusPaymentRule==RuleUtil.RULE_OK){
						if(packetPaymentPersonal.getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM){
							unitPrice=packetPaymentPersonal.getPayAmount()/packetSalePersonal.getProgCount();
						}
					}else{
						unitPrice=packetPaymentPersonal.getPayAmount()/packetSalePersonal.getProgCount();
					}
					
					if(userBonusObj.getCreditCardCommissionRule()==RuleUtil.RULE_OK){
						
						if(packetPaymentPersonal.getPayType()==PayTypeUtil.PAY_TYPE_CREDIT_CARD){
							double commissionRate=((100d- Double.parseDouble(""+userBonusObj.getCreditCardCommissionRate()))/100);
							
							unitPrice=unitPrice*commissionRate;
						}
					}
					
					totalTimePlanPayment+=unitPrice;
					
				}
				
				((ScheduleUsersPersonalPlan)scheduleFactory).setUnitPrice(unitPrice);
				((ScheduleUsersPersonalPlan)scheduleFactory).setSaleCount(packetSalePersonal.getProgCount());
			}
			
			userBonusDetailObj.setSchCount(scheduleTimePlan.getSchCount());
			userBonusDetailObj.setPlanStartDate(scheduleTimePlan.getPlanStartDate());
			userBonusDetailObj.setClassCount(i);
			userBonusDetailObj.setProgName(programPersonal.getProgName());
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
