package tr.com.beinplanner.packetsale.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.packetpayment.business.IPacketPayment;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentClass;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.business.ISchedulePersonalClass;
import tr.com.beinplanner.schedule.dao.SchedulePlan;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetSaleClassFacade")
public class PacketSaleClassFacade implements IPacketSaleFacade {

	@Autowired
	@Qualifier("packetPaymentClassBusiness")
	IPacketPayment iPacketPayment;
	
	
	@Autowired
	@Qualifier("scheduleClass")
	ISchedulePersonalClass iSchedulePersonalClass;
	
	@Override
	public synchronized HmiResultObj canSale(long userId,Date startDate,long saleId) {
		
		return null;
	}
	
	@Override
	public synchronized HmiResultObj canSaleDelete(PacketSaleFactory packetSaleFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		PacketSaleClass psc=(PacketSaleClass)packetSaleFactory;
		PacketPaymentClass packetPaymentClass=(PacketPaymentClass)iPacketPayment.findPacketPaymentBySaleId(psc.getSaleId());
		if(packetPaymentClass==null) {
			
			SchedulePlan schedulePlan= iSchedulePersonalClass.findSchedulePlanBySaleId(psc.getSaleId());
			if(schedulePlan!=null) {
				hmiResultObj.setResultMessage("saledPacketHaveBooking");
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			}
		}else {
			hmiResultObj.setResultMessage("saledPacketHavePayment");
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		}
		
		return hmiResultObj;
	}

}
