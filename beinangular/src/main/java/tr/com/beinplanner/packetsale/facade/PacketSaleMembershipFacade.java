package tr.com.beinplanner.packetsale.facade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.packetpayment.business.IPacketPayment;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembership;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.business.IScheduleMembership;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.util.ResultStatuObj;
@Service
@Qualifier("packetSaleMembershipFacade")
public class PacketSaleMembershipFacade implements IPacketSaleFacade {

	@Autowired
	@Qualifier("packetPaymentMembershipBusiness")
	IPacketPayment iPacketPayment;
	
	
	@Autowired
	@Qualifier("scheduleMembership")
	IScheduleMembership iScheduleMembership;
	
	
	
	
	@Override
	public synchronized HmiResultObj canSale(long userId, Date startDate,long saleId) {
		List<ScheduleMembershipPlan> scheduleFactories= iScheduleMembership.findSchedulePlanByUserId(userId);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		for (ScheduleMembershipPlan scheduleFactory : scheduleFactories) {
		       if(startDate.before(scheduleFactory.getSmpEndDate()) && saleId!=scheduleFactory.getSaleId()){
					hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
					hmiResultObj.setResultMessage("canNotStartBeforeFinishedPrevious");
					return hmiResultObj;
				}
		}
		
		return hmiResultObj;
	}




	@Override
	public synchronized HmiResultObj canSaleDelete(PacketSaleFactory packetSaleFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		PacketSaleMembership psc=(PacketSaleMembership)packetSaleFactory;
		PacketPaymentMembership packetPaymentMembership=(PacketPaymentMembership)iPacketPayment.findPacketPaymentBySaleId(psc.getSaleId());
		if(packetPaymentMembership!=null) {
			
			hmiResultObj.setResultMessage("saledPacketHavePayment");
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		}
		
		return hmiResultObj;
	}

}