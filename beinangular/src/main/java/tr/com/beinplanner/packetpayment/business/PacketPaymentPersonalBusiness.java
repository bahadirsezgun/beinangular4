package tr.com.beinplanner.packetpayment.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.dashboard.businessEntity.LeftPaymentInfo;
import tr.com.beinplanner.packetpayment.comparator.PacketPaymentComparator;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonal;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonalDetail;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonal;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonalDetail;
import tr.com.beinplanner.packetpayment.entity.PaymentConfirmQuery;
import tr.com.beinplanner.packetpayment.facade.IPacketPaymentFacade;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentPersonalDetailRepository;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentPersonalRepository;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.packetsale.repository.PacketSalePersonalRepository;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetPaymentPersonalBusiness")
public class PacketPaymentPersonalBusiness implements IPacketPayment {

	
	@Autowired
	PacketPaymentPersonalRepository packetPaymentPersonalRepository;
	
	@Autowired
	PacketPaymentPersonalDetailRepository packetPaymentPersonalDetailRepository;
	
	
	@Autowired
	PacketSalePersonalRepository packetSalePersonalRepository;
	
	@Autowired
	@Qualifier("packetPaymentClassBusiness")
	IPacketPayment iPacketPayment;
	
	@Autowired
	@Qualifier("packetPaymentPersonalFacade")
	IPacketPaymentFacade iPacketPaymentFacade;
	
	@Autowired
	UserService userService;
	
  
	
	@Override
	public List<PacketPaymentFactory> findPaymentsToConfirmInChain(PaymentConfirmQuery pcq,int firmId) {
		List<PacketPaymentFactory> packetPaymentFactories= iPacketPayment.findPaymentsToConfirmInChain(pcq, firmId);
		
		List<PacketPaymentPersonal> paymentPersonalFactories=new ArrayList<PacketPaymentPersonal>();
		if(pcq.getConfirmed()==0 && pcq.getUnConfirmed()==1){
			paymentPersonalFactories=packetPaymentPersonalRepository.findByPayConfirmAndUserNameAndUserSurnameAndFirmId(0, pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
		}else if(pcq.getConfirmed()==1 && pcq.getUnConfirmed()==0){
			paymentPersonalFactories=packetPaymentPersonalRepository.findByPayConfirmAndUserNameAndUserSurnameAndFirmId(1, pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
		}else {
			paymentPersonalFactories=packetPaymentPersonalRepository.findByUserNameAndUserSurnameAndFirmId(pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
		}
		
		paymentPersonalFactories.forEach(ppf->{
			ppf.setPacketSaleFactory(packetSalePersonalRepository.findOne(ppf.getSaleId()));
		});
		
		packetPaymentFactories.addAll(paymentPersonalFactories);
		
		Collections.sort(packetPaymentFactories,new PacketPaymentComparator());
	    return packetPaymentFactories;
	}



	@Override
	public List<PacketPaymentDetailFactory> findIncomePaymentDetailsInDatesInChain(Date startDate, Date endDate, int firmId) {
		List<PacketPaymentDetailFactory> packetPaymentDetailFactories= iPacketPayment.findIncomePaymentDetailsInDatesInChain(startDate, endDate, firmId);
		List<PacketPaymentPersonalDetail> packetPaymentPersonalDetails=packetPaymentPersonalDetailRepository.findIncomePaymentDetailsInDates(startDate, endDate, firmId);
		packetPaymentPersonalDetails.forEach(pppd->{
			pppd.setUser(userService.findUserByPersonalPayId(pppd.getPayId()));
			
		});
		
		
		packetPaymentDetailFactories.addAll(packetPaymentPersonalDetails);
		return packetPaymentDetailFactories;
	}


	@Override
	public HmiResultObj confirmIt(PacketPaymentFactory packetPaymentFactory) {
		PacketPaymentPersonal packetPaymentPersonal=(PacketPaymentPersonal)packetPaymentFactory;
		PacketPaymentPersonal ppf=packetPaymentPersonalRepository.findOne(packetPaymentPersonal.getPayId());
		
		ppf.setPayConfirm(packetPaymentPersonal.getPayConfirm());
		ppf=  packetPaymentPersonalRepository.save(ppf);
		
		List<PacketPaymentPersonalDetail> ppcds=packetPaymentPersonalDetailRepository.findByPayId(ppf.getPayId());
		ppf.setPacketPaymentDetailFactories(ppcds);
		ppcds.forEach(ppcd->{
			ppcd.setPayConfirm(packetPaymentPersonal.getPayConfirm());
			packetPaymentPersonalDetailRepository.save(ppcd);
		});
		
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(ppf);
		return hmiResultObj;
	}

	@Override
	public HmiResultObj saveIt(PacketPaymentFactory packetPaymentFactory) {
		
		PacketPaymentPersonal packetPaymentPersonal=(PacketPaymentPersonal)packetPaymentFactory;
		PacketPaymentPersonal ppp=null;
		
		
		PacketPaymentPersonalDetail packetPaymentPersonalDetail=new PacketPaymentPersonalDetail();
		packetPaymentPersonalDetail.setPayAmount(packetPaymentPersonal.getPayAmount());
		packetPaymentPersonalDetail.setPayComment(packetPaymentPersonal.getPayComment());
		packetPaymentPersonalDetail.setPayDate(packetPaymentPersonal.getPayDate());
		packetPaymentPersonalDetail.setPayType(packetPaymentPersonal.getPayType());
		
		
		PacketPaymentPersonal ppf=packetPaymentPersonalRepository.findBySaleId(packetPaymentPersonal.getSaleId());
		
		
		if(ppf!=null) {
			
			List<PacketPaymentPersonalDetail> pppd=packetPaymentPersonalDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
			
			
			double totalPayment=   ppf.getPacketPaymentDetailFactories().stream().mapToDouble(ppdf->{return ppdf.getPayAmount();}).sum();
			packetPaymentPersonal.setPayAmount(packetPaymentPersonal.getPayAmount()+totalPayment);
			packetPaymentPersonal.setPayId(ppf.getPayId());
		}
			
		ppp=  packetPaymentPersonalRepository.save(packetPaymentPersonal);
		packetPaymentPersonalDetail.setPayId(ppp.getPayId());
		packetPaymentPersonalDetailRepository.save(packetPaymentPersonalDetail);
	
		List<PacketPaymentPersonalDetail> pppd=packetPaymentPersonalDetailRepository.findByPayId(ppp.getPayId());
		ppp.setPacketPaymentDetailFactories(pppd);
		   
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		hmiResultObj.setResultObj(ppp);
		
		return hmiResultObj;
	}
	
	
	
	@Override
	public HmiResultObj deleteAll(PacketPaymentFactory packetPaymentFactory) {
		HmiResultObj hmiResult= iPacketPaymentFacade.canPaymentDelete(packetPaymentFactory);
		
		if(hmiResult.resultStatu.equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetPaymentPersonalRepository.delete((PacketPaymentPersonal)packetPaymentFactory);
			hmiResult.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResult.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		return hmiResult;
	}


	@Override
	public HmiResultObj deleteDetail(PacketPaymentDetailFactory ppdf) {
		HmiResultObj hmiResult= iPacketPaymentFacade.canPaymentDetailDelete(ppdf);
		PacketPaymentPersonal ppp=null;
		if(hmiResult.resultStatu.equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetPaymentPersonalDetailRepository.delete((PacketPaymentPersonalDetail)ppdf);
			
			List<PacketPaymentPersonalDetail> packetPaymentPersonalDetails=packetPaymentPersonalDetailRepository.findByPayId(((PacketPaymentPersonalDetail)ppdf).getPayId());
			ppp=(PacketPaymentPersonal)packetPaymentPersonalRepository.findByPayId(((PacketPaymentPersonalDetail)ppdf).getPayId());
			
			if(packetPaymentPersonalDetails==null || packetPaymentPersonalDetails.size()==0) {
				
				packetPaymentPersonalRepository.delete(ppp);
			}else {
				ppp.setPacketPaymentDetailFactories(packetPaymentPersonalDetails);
				
				double totalPayment=   ppp.getPacketPaymentDetailFactories().stream().mapToDouble(ppdfl->{return ppdfl.getPayAmount();}).sum();
				ppp.setPayAmount(totalPayment);
				ppp=  packetPaymentPersonalRepository.save(ppp);
				
			}
			
			hmiResult.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResult.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		return hmiResult;
	}



	@Override
	public PacketPaymentFactory findPacketPaymentById(long id) {
		
		PacketPaymentPersonal ppf=packetPaymentPersonalRepository.findOne(id);
		if(ppf!=null) {
			List<PacketPaymentPersonalDetail> pppd=packetPaymentPersonalDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
		}
		return ppf;
	}




	@Override
	public PacketPaymentFactory findPacketPaymentBySaleId(long saleId) {
		
		PacketPaymentPersonal ppf=packetPaymentPersonalRepository.findBySaleId(saleId);
		if(ppf!=null) {
			List<PacketPaymentPersonalDetail> pppd=packetPaymentPersonalDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
		}
		return ppf;
	}




	@Override
	public double findTotalIncomePaymentInDate(Date startDate, Date endDate, int firmId) {
		 double totalPayment=iPacketPayment.findTotalIncomePaymentInDate(startDate, endDate, firmId);
		 List<PacketPaymentPersonal> packetPaymentPersonals=packetPaymentPersonalRepository.findPacketPaymentPersonalForDate(startDate,endDate,firmId);
		 double totalPaymentForPersonal=packetPaymentPersonals.stream().mapToDouble(ppp->ppp.getPayAmount()).sum();
		 return totalPayment+totalPaymentForPersonal;
	}




	@Override
	public List<PacketPaymentFactory> findLast5packetPaymentsInChain(int firmId) {
		List<PacketPaymentFactory> packetPaymentFactories=iPacketPayment.findLast5packetPaymentsInChain(firmId);
		packetPaymentFactories.addAll(packetPaymentPersonalRepository.findLast5packetPayments(firmId));
		Collections.sort(packetPaymentFactories, new PacketPaymentComparator());
		return packetPaymentFactories;
	}




	@Override
	public LeftPaymentInfo findLeftPacketPaymentsInChain(int firmId) {
		
		LeftPaymentInfo leftPaymentInfo=iPacketPayment.findLeftPacketPaymentsInChain(firmId);
		
		List<PacketPaymentPersonal> packetPaymentPersonals= packetPaymentPersonalRepository.findLeftPacketPaymentPersonal(firmId);
		double leftPP=packetPaymentPersonals
				.stream()
				.mapToDouble(ppp->{
					             	PacketSalePersonal packetSalePersonal=packetSalePersonalRepository.findOne(ppp.getSaleId());              
					             	double payment= packetSalePersonal.getPacketPrice()-ppp.getPayAmount();
					             	return payment;
							    }
				).sum();
		
		leftPaymentInfo.setLeftPayment(leftPaymentInfo.getLeftPayment()+leftPP);
		leftPaymentInfo.setLeftPaymentCount(packetPaymentPersonals.size()
											+leftPaymentInfo.getLeftPaymentCount());

		List<PacketSalePersonal> packetSalePersonals=packetSalePersonalRepository.findPacketSalePersonalWithNoPayment(firmId);
		
		double leftPPN=packetSalePersonals
				.stream()
				.mapToDouble(psp->psp.getPacketPrice()
				).sum();
		
		
		leftPaymentInfo.setNoPayment(leftPPN+leftPaymentInfo.getNoPayment());
		leftPaymentInfo.setNoPaymentCount(leftPaymentInfo.getNoPaymentCount()
				+packetSalePersonals.size());
		
		return leftPaymentInfo;
	}


}
