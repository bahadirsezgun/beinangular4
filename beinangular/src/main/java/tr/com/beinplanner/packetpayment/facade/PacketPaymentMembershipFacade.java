package tr.com.beinplanner.packetpayment.facade;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembership;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembershipDetail;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.PaymentConfirmUtil;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetPaymentMembershipFacade")
public class PacketPaymentMembershipFacade implements IPacketPaymentFacade{

	@Override
	public HmiResultObj canPaymentDelete(PacketPaymentFactory ppf) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(((PacketPaymentMembership)ppf).getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("packetPaymentConfirmed");
		}
		
		return hmiResultObj;
	}
	
	
	@Override
	public HmiResultObj canPaymentDetailDelete(PacketPaymentDetailFactory ppdf) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(((PacketPaymentMembershipDetail)ppdf).getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("packetPaymentConfirmed");
		}
		
		return hmiResultObj;
	}
	

}
