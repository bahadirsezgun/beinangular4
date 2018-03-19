package tr.com.beinplanner.packetpayment.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.dashboard.businessEntity.LeftPaymentInfo;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentClass;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentClassDetail;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonal;
import tr.com.beinplanner.packetpayment.entity.PaymentConfirmQuery;
import tr.com.beinplanner.packetpayment.facade.IPacketPaymentFacade;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentClassDetailRepository;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentClassRepository;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.repository.PacketSaleClassRepository;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetPaymentClassBusiness")
public class PacketPaymentClassBusiness implements IPacketPayment {


	@Autowired
	PacketPaymentClassRepository packetPaymentClassRepository;
	
	@Autowired
	PacketPaymentClassDetailRepository packetPaymentClassDetailRepository;
	
	

	@Autowired
	PacketSaleClassRepository packetSaleClassRepository;
	
	@Autowired
	@Qualifier("packetPaymentMembershipBusiness")
	IPacketPayment iPacketPayment;
	
	@Autowired
	@Qualifier("packetPaymentClassFacade")
	IPacketPaymentFacade iPacketPaymentFacade;
	
	@Autowired
	UserService userService;
	
  
	@Override
	public List<PacketPaymentFactory> findPaymentsToConfirmInChain(PaymentConfirmQuery pcq,int firmId) {
		
		List<PacketPaymentFactory> packetPaymentFactories= iPacketPayment.findPaymentsToConfirmInChain(pcq, firmId);
		List<PacketPaymentClass> packetPaymentClasses=new ArrayList<>();
		
		if(pcq.getConfirmed()==0 && pcq.getUnConfirmed()==1){
			packetPaymentClasses=packetPaymentClassRepository.findByPayConfirmAndUserNameAndUserSurnameAndFirmId(0, pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
		}else if(pcq.getConfirmed()==1 && pcq.getUnConfirmed()==0){
			packetPaymentClasses=packetPaymentClassRepository.findByPayConfirmAndUserNameAndUserSurnameAndFirmId(1, pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
		}else {
			packetPaymentClasses=packetPaymentClassRepository.findByUserNameAndUserSurnameAndFirmId(pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
		}
		
		
		packetPaymentClasses.forEach(ppf->{
			ppf.setPacketSaleFactory(packetSaleClassRepository.findOne(ppf.getSaleId()));
		});
		
		packetPaymentFactories.addAll(packetPaymentClasses);
		
	    return packetPaymentFactories;
		
		
	}
	
	@Override
	public List<PacketPaymentDetailFactory> findIncomePaymentDetailsInDatesInChain(Date startDate, Date endDate, int firmId) {
		
		List<PacketPaymentDetailFactory> packetPaymentDetailFactories= iPacketPayment.findIncomePaymentDetailsInDatesInChain(startDate, endDate, firmId);
		List<PacketPaymentClassDetail> packetPaymentClassDetails=packetPaymentClassDetailRepository.findIncomePaymentDetailsInDates(startDate, endDate, firmId);
		packetPaymentClassDetails.forEach(pppd->{
			pppd.setUser(userService.findUserByClassPayId(pppd.getPayId()));
			
		});
		packetPaymentDetailFactories.addAll(packetPaymentClassDetails);
		return packetPaymentDetailFactories;
	}
	
	
	
	@Override
	public HmiResultObj confirmIt(PacketPaymentFactory packetPaymentFactory) {
		PacketPaymentClass packetPaymentClass=(PacketPaymentClass)packetPaymentFactory;
		PacketPaymentClass ppf=packetPaymentClassRepository.findOne(packetPaymentClass.getPayId());
		
		ppf.setPayConfirm(packetPaymentClass.getPayConfirm());
		ppf=  packetPaymentClassRepository.save(ppf);
		
		List<PacketPaymentClassDetail> ppcds=packetPaymentClassDetailRepository.findByPayId(ppf.getPayId());
		ppf.setPacketPaymentDetailFactories(ppcds);
		ppcds.forEach(ppcd->{
			ppcd.setPayConfirm(packetPaymentClass.getPayConfirm());
			packetPaymentClassDetailRepository.save(ppcd);
		});
		
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(ppf);
		return hmiResultObj;
	}

	@Override
	public HmiResultObj saveIt(PacketPaymentFactory packetPaymentFactory) {
		
		PacketPaymentClass packetPaymentClass=(PacketPaymentClass)packetPaymentFactory;
		PacketPaymentClass ppc=null;
		
		
		PacketPaymentClassDetail packetPaymentClassDetail=new PacketPaymentClassDetail();
		packetPaymentClassDetail.setPayAmount(packetPaymentClass.getPayAmount());
		packetPaymentClassDetail.setPayComment(packetPaymentClass.getPayComment());
		packetPaymentClassDetail.setPayDate(packetPaymentClass.getPayDate());
		packetPaymentClassDetail.setPayType(packetPaymentClass.getPayType());
		
		if(packetPaymentClass.getPayId()!=0) {
		
			PacketPaymentClass ppf=packetPaymentClassRepository.findOne(packetPaymentClass.getPayId());
			if(ppf!=null) {
				
				List<PacketPaymentClassDetail> ppcd=packetPaymentClassDetailRepository.findByPayId(ppf.getPayId());
				ppf.setPacketPaymentDetailFactories(ppcd);
				
				
				double totalPayment=   ppf.getPacketPaymentDetailFactories().stream().mapToDouble(ppdf->{return ppdf.getPayAmount();}).sum();
				packetPaymentClass.setPayAmount(packetPaymentClass.getPayAmount()+totalPayment);
			}else {
				packetPaymentClass.setPayId(0);
			}
			
			ppc=  packetPaymentClassRepository.save(packetPaymentClass);
			packetPaymentClassDetail.setPayId(ppc.getPayId());
			packetPaymentClassDetailRepository.save(packetPaymentClassDetail);
		
		}else {
			
			ppc=  packetPaymentClassRepository.save(packetPaymentClass);
			packetPaymentClassDetail.setPayId(ppc.getPayId());
			packetPaymentClassDetailRepository.save(packetPaymentClassDetail);
			
		}
		
		ppc=packetPaymentClassRepository.findOne(ppc.getPayId());
		
		List<PacketPaymentClassDetail> pppd=packetPaymentClassDetailRepository.findByPayId(ppc.getPayId());
		ppc.setPacketPaymentDetailFactories(pppd);
		
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		hmiResultObj.setResultObj(ppc);
		
		return hmiResultObj;
	}


	@Override
	public HmiResultObj deleteAll(PacketPaymentFactory packetPaymentFactory) {
		HmiResultObj hmiResult= iPacketPaymentFacade.canPaymentDelete(packetPaymentFactory);
		
		if(hmiResult.resultStatu.equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetPaymentClassRepository.delete((PacketPaymentClass)packetPaymentFactory);
			hmiResult.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResult.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		return hmiResult;
	}


	@Override
	public HmiResultObj deleteDetail(PacketPaymentDetailFactory ppdf) {
		
		HmiResultObj hmiResult= iPacketPaymentFacade.canPaymentDetailDelete(ppdf);
		PacketPaymentClass ppp=null;
		if(hmiResult.resultStatu.equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetPaymentClassDetailRepository.delete((PacketPaymentClassDetail)ppdf);
			
			List<PacketPaymentClassDetail> packetPaymentClassDetails=packetPaymentClassDetailRepository.findByPayId(((PacketPaymentClassDetail)ppdf).getPayId());
			ppp=(PacketPaymentClass)packetPaymentClassRepository.findByPayId(((PacketPaymentClassDetail)ppdf).getPayId());
			
			if(packetPaymentClassDetails==null || packetPaymentClassDetails.size()==0) {
				
				packetPaymentClassRepository.delete(ppp);
			}else {
				ppp.setPacketPaymentDetailFactories(packetPaymentClassDetails);
				
				double totalPayment=   ppp.getPacketPaymentDetailFactories().stream().mapToDouble(ppdfl->{return ppdfl.getPayAmount();}).sum();
				ppp.setPayAmount(totalPayment);
				ppp=  packetPaymentClassRepository.save(ppp);
				
			}
			
			hmiResult.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResult.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		return hmiResult;
		
		
		
		
		
	}


	@Override
	public PacketPaymentFactory findPacketPaymentById(long id) {
		
		PacketPaymentClass ppf=packetPaymentClassRepository.findBySaleId(id);
		if(ppf!=null) {
			List<PacketPaymentClassDetail> pppd=packetPaymentClassDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
		}
		return ppf;
		
		
	}


	@Override
	public PacketPaymentFactory findPacketPaymentBySaleId(long saleId) {
		PacketPaymentClass ppf=packetPaymentClassRepository.findBySaleId(saleId);
		if(ppf!=null) {
			List<PacketPaymentClassDetail> pppd=packetPaymentClassDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
		}
		return ppf;
	}


	@Override
	public double findTotalIncomePaymentInDate(Date startDate, Date endDate, int firmId) {
		 double totalPayment=iPacketPayment.findTotalIncomePaymentInDate(startDate, endDate, firmId);
		 List<PacketPaymentClass> packetPaymentClasses=packetPaymentClassRepository.findPacketPaymentClassForDate(startDate,endDate,firmId);
		 double totalPaymentForClass=packetPaymentClasses.stream().mapToDouble(ppp->ppp.getPayAmount()).sum();
		 return totalPayment+totalPaymentForClass;
	}


	@Override
	public List<PacketPaymentFactory> findLast5packetPaymentsInChain(int firmId) {
		List<PacketPaymentFactory> packetPaymentFactories=iPacketPayment.findLast5packetPaymentsInChain(firmId);
		packetPaymentFactories.addAll(packetPaymentClassRepository.findLast5packetPayments(firmId));
		return packetPaymentFactories;
	}


	
	
	

	@Override
	public LeftPaymentInfo findLeftPacketPaymentsInChain(int firmId) {
		LeftPaymentInfo leftPaymentInfo=iPacketPayment.findLeftPacketPaymentsInChain(firmId);
		
		List<PacketPaymentClass> packetPaymentClasss= packetPaymentClassRepository.findLeftPacketPaymentClass(firmId);
		double leftPP=packetPaymentClasss
				.stream()
				.mapToDouble(ppp->{
					             	PacketSaleClass packetSaleClass=packetSaleClassRepository.findOne(ppp.getSaleId());              
					             	double payment= packetSaleClass.getPacketPrice()-ppp.getPayAmount();
					             	return payment;
							    }
				).sum();
		
		leftPaymentInfo.setLeftPayment(leftPaymentInfo.getLeftPayment()+leftPP);
		leftPaymentInfo.setLeftPaymentCount(packetPaymentClasss.size()
											+leftPaymentInfo.getLeftPaymentCount());

		List<PacketSaleClass> packetSaleClasss=packetSaleClassRepository.findPacketSaleClassWithNoPayment(firmId);
		
		double leftPPN=packetSaleClasss
				.stream()
				.mapToDouble(psp->psp.getPacketPrice()
				).sum();
		
		
		leftPaymentInfo.setNoPayment(leftPPN+leftPaymentInfo.getNoPayment());
		leftPaymentInfo.setNoPaymentCount(leftPaymentInfo.getNoPaymentCount()
				+packetSaleClasss.size());
		
		return leftPaymentInfo;
	}

}
