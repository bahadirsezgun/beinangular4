package tr.com.beinplanner.packetpayment.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonal;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonalDetail;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentPersonalRepository;
import tr.com.beinplanner.packetsale.business.PacketSalePersonalBusiness;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.packetsale.service.PacketSaleService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.BonusPayedUtil;
import tr.com.beinplanner.util.PaymentConfirmUtil;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetPaymentPersonalFacade")
public class PacketPaymentPersonalFacade  implements IPacketPaymentFacade{

	@Autowired
	PacketSaleService packetSaleService;
	
	@Autowired
	PacketSalePersonalBusiness packetSalePersonalBusiness;
	
	@Autowired
	PacketPaymentPersonalRepository packetPaymentPersonalRepository;
	
	
	@Override
	public HmiResultObj canPaymentDelete(PacketPaymentFactory ppf) {
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		if(((PacketPaymentPersonal)ppf).getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("packetPaymentConfirmed");
		}else {
			PacketSalePersonal packetSalePersonal=(PacketSalePersonal)packetSaleService.findPacketSaleById(((PacketPaymentPersonal)ppf).getSaleId(), packetSalePersonalBusiness);
			if(packetSalePersonal.getBonusPayedFlag()==BonusPayedUtil.BONUS_PAYED_YES) {
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
		
		
		if(((PacketPaymentPersonalDetail)ppdf).getPayConfirm()==PaymentConfirmUtil.PAYMENT_CONFIRM) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("packetPaymentConfirmed");
		}else {
			
			PacketPaymentPersonal ppf=packetPaymentPersonalRepository.findOne(((PacketPaymentPersonalDetail)ppdf).getPayId());
			PacketSalePersonal packetSalePersonal=(PacketSalePersonal)packetSaleService.findPacketSaleById(((PacketPaymentPersonal)ppf).getSaleId(), packetSalePersonalBusiness);
			if(packetSalePersonal.getBonusPayedFlag()==BonusPayedUtil.BONUS_PAYED_YES) {
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
				hmiResultObj.setResultMessage("bonusPayed");
			}
		}
		
		return hmiResultObj;
	}

}
