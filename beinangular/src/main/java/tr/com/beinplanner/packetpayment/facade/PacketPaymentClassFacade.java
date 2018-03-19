package tr.com.beinplanner.packetpayment.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentClass;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentClassDetail;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentClassRepository;
import tr.com.beinplanner.packetsale.business.PacketSaleClassBusiness;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.service.PacketSaleService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.BonusPayedUtil;
import tr.com.beinplanner.util.PaymentConfirmUtil;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetPaymentClassFacade")
public class PacketPaymentClassFacade implements IPacketPaymentFacade{

	
	@Autowired
	PacketSaleService packetSaleService;
	
	@Autowired
	PacketSaleClassBusiness packetSaleClassBusiness;
	
	@Autowired
	PacketPaymentClassRepository packetPaymentClassRepository;
	
	
	@Override
	public HmiResultObj canPaymentDelete(PacketPaymentFactory ppf) {
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(((PacketPaymentClass)ppf).getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("packetPaymentConfirmed");
		}else {
			PacketSaleClass packetSaleClass=(PacketSaleClass)packetSaleService.findPacketSaleById(((PacketPaymentClass)ppf).getSaleId(), packetSaleClassBusiness);
			if(packetSaleClass.getBonusPayedFlag()==BonusPayedUtil.BONUS_PAYED_YES) {
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("bonusPayed");
			}
		}
		
		return hmiResultObj;
	}


	@Override
	public HmiResultObj canPaymentDetailDelete(PacketPaymentDetailFactory ppdf) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(((PacketPaymentClassDetail)ppdf).getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("packetPaymentConfirmed");
		}else {
			
			PacketPaymentClass ppf=packetPaymentClassRepository.findOne(((PacketPaymentClassDetail)ppdf).getPayId());
			PacketSaleClass packetSaleClass=(PacketSaleClass)packetSaleService.findPacketSaleById(((PacketPaymentClass)ppf).getSaleId(), packetSaleClassBusiness);
			if(packetSaleClass.getBonusPayedFlag()==BonusPayedUtil.BONUS_PAYED_YES) {
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("bonusPayed");
			}
		}
		
		return hmiResultObj;
	}

	
}
