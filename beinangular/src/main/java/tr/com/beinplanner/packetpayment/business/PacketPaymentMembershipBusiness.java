package tr.com.beinplanner.packetpayment.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.dashboard.businessEntity.LeftPaymentInfo;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembership;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembershipDetail;
import tr.com.beinplanner.packetpayment.entity.PaymentConfirmQuery;
import tr.com.beinplanner.packetpayment.facade.IPacketPaymentFacade;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentMembershipDetailRepository;
import tr.com.beinplanner.packetpayment.repository.PacketPaymentMembershipRepository;
import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.packetsale.repository.PacketSaleMembershipRepository;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("packetPaymentMembershipBusiness")
public class PacketPaymentMembershipBusiness implements IPacketPayment {

	@Autowired
	PacketPaymentMembershipRepository packetPaymentMembershipRepository;
	
	@Autowired
	PacketPaymentMembershipDetailRepository packetPaymentMembershipDetailRepository;
	
	
	@Autowired
	PacketSaleMembershipRepository packetSaleMembershipRepository;
	
	@Autowired
	@Qualifier("packetPaymentMembershipFacade")
	IPacketPaymentFacade iPacketPaymentFacade;
	
	@Autowired
	UserService userService;
	
  
	
	
	@Override
	public List<PacketPaymentFactory> findPaymentsToConfirmInChain(PaymentConfirmQuery pcq,int firmId) {
		
		List<PacketPaymentFactory> packetPaymentFactories=new ArrayList<>();
		List<PacketPaymentMembership> packetPaymentMemberships=new ArrayList<>();
		
			if(pcq.getConfirmed()==0 && pcq.getUnConfirmed()==1){
				packetPaymentMemberships=packetPaymentMembershipRepository.findByPayConfirmAndUserNameAndUserSurnameAndFirmId(0, pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
			}else if(pcq.getConfirmed()==1 && pcq.getUnConfirmed()==0){
				packetPaymentMemberships=packetPaymentMembershipRepository.findByPayConfirmAndUserNameAndUserSurnameAndFirmId(1, pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
			}else {
				packetPaymentMemberships=packetPaymentMembershipRepository.findByUserNameAndUserSurnameAndFirmId(pcq.getUserName()+"%", pcq.getUserSurname()+"%", firmId);
			}
			
			
			packetPaymentMemberships.forEach(ppf->{
				ppf.setPacketSaleFactory(packetSaleMembershipRepository.findOne(ppf.getSaleId()));
			});
			
			packetPaymentFactories.addAll(packetPaymentMemberships);
			
		return packetPaymentFactories;
	}




	@Override
	public List<PacketPaymentDetailFactory> findIncomePaymentDetailsInDatesInChain(Date startDate, Date endDate, int firmId) {
		List<PacketPaymentMembershipDetail> packetPaymentMembershipDetails=packetPaymentMembershipDetailRepository.findIncomePaymentDetailsInDates(startDate, endDate, firmId);
		
		packetPaymentMembershipDetails.forEach(pppd->{
			pppd.setUser(userService.findUserByMembershipPayId(pppd.getPayId()));
			
		});
		
		List<PacketPaymentDetailFactory> packetPaymentDetailFactories=new ArrayList<PacketPaymentDetailFactory>();
		packetPaymentDetailFactories.addAll(packetPaymentMembershipDetails);
		return packetPaymentDetailFactories;
	}


	@Override
	public HmiResultObj confirmIt(PacketPaymentFactory packetPaymentFactory) {
		PacketPaymentMembership packetPaymentMembership=(PacketPaymentMembership)packetPaymentFactory;
		PacketPaymentMembership ppf=packetPaymentMembershipRepository.findOne(packetPaymentMembership.getPayId());
		
		ppf.setPayConfirm(packetPaymentMembership.getPayConfirm());
		ppf=  packetPaymentMembershipRepository.save(ppf);
		
		List<PacketPaymentMembershipDetail> ppcds=packetPaymentMembershipDetailRepository.findByPayId(ppf.getPayId());
		ppf.setPacketPaymentDetailFactories(ppcds);
		ppcds.forEach(ppcd->{
			ppcd.setPayConfirm(packetPaymentMembership.getPayConfirm());
			packetPaymentMembershipDetailRepository.save(ppcd);
		});
		
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(ppf);
		return hmiResultObj;
	}

	@Override
	public HmiResultObj saveIt(PacketPaymentFactory packetPaymentFactory) {
		
		PacketPaymentMembership packetPaymentMembership=(PacketPaymentMembership)packetPaymentFactory;
		PacketPaymentMembership ppc=null;
		
		
		PacketPaymentMembershipDetail packetPaymentMembershipDetail=new PacketPaymentMembershipDetail();
		packetPaymentMembershipDetail.setPayAmount(packetPaymentMembership.getPayAmount());
		packetPaymentMembershipDetail.setPayComment(packetPaymentMembership.getPayComment());
		packetPaymentMembershipDetail.setPayDate(packetPaymentMembership.getPayDate());
		packetPaymentMembershipDetail.setPayType(packetPaymentMembership.getPayType());
		
		if(packetPaymentMembership.getPayId()!=0) {
		
			PacketPaymentMembership ppf=packetPaymentMembershipRepository.findOne(packetPaymentMembership.getPayId());
			if(ppf!=null) {
				
				List<PacketPaymentMembershipDetail> pppd=packetPaymentMembershipDetailRepository.findByPayId(ppf.getPayId());
				ppf.setPacketPaymentDetailFactories(pppd);
				
				double totalPayment=   ppf.getPacketPaymentDetailFactories().stream().mapToDouble(ppdf->{return ppdf.getPayAmount();}).sum();
				packetPaymentMembership.setPayAmount(packetPaymentMembership.getPayAmount()+totalPayment);
			}else {
				packetPaymentMembership.setPayId(0);
			}
			
			ppc=  packetPaymentMembershipRepository.save(packetPaymentMembership);
			packetPaymentMembershipDetail.setPayId(ppc.getPayId());
			packetPaymentMembershipDetailRepository.save(packetPaymentMembershipDetail);
		
		}else {
			
			ppc=  packetPaymentMembershipRepository.save(packetPaymentMembership);
			packetPaymentMembershipDetail.setPayId(ppc.getPayId());
			packetPaymentMembershipDetailRepository.save(packetPaymentMembershipDetail);
			
		}
		
		PacketPaymentMembership resultObj=packetPaymentMembershipRepository.findOne(ppc.getPayId());
		List<PacketPaymentMembershipDetail> pppd=packetPaymentMembershipDetailRepository.findByPayId(resultObj.getPayId());
		resultObj.setPacketPaymentDetailFactories(pppd);
		
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		hmiResultObj.setResultObj(resultObj);
		
		return hmiResultObj;
	}
	
	
	
	
	@Override
	public HmiResultObj deleteAll(PacketPaymentFactory packetPaymentFactory) {
		HmiResultObj hmiResult= iPacketPaymentFacade.canPaymentDelete(packetPaymentFactory);
		
		if(hmiResult.resultStatu.equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetPaymentMembershipRepository.delete((PacketPaymentMembership)packetPaymentFactory);
			hmiResult.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResult.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		return hmiResult;
	}


	@Override
	public HmiResultObj deleteDetail(PacketPaymentDetailFactory ppdf) {
		HmiResultObj hmiResult= iPacketPaymentFacade.canPaymentDetailDelete(ppdf);
		PacketPaymentMembership ppp=null;
		if(hmiResult.resultStatu.equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetPaymentMembershipDetailRepository.delete((PacketPaymentMembershipDetail)ppdf);
			
			List<PacketPaymentMembershipDetail> packetPaymentMembershipDetails=packetPaymentMembershipDetailRepository.findByPayId(((PacketPaymentMembershipDetail)ppdf).getPayId());
			ppp=(PacketPaymentMembership)packetPaymentMembershipRepository.findByPayId(((PacketPaymentMembershipDetail)ppdf).getPayId());
			
			if(packetPaymentMembershipDetails==null || packetPaymentMembershipDetails.size()==0) {
				
				packetPaymentMembershipRepository.delete(ppp);
			}else {
				ppp.setPacketPaymentDetailFactories(packetPaymentMembershipDetails);
				
				double totalPayment=   ppp.getPacketPaymentDetailFactories().stream().mapToDouble(ppdfl->{return ppdfl.getPayAmount();}).sum();
				ppp.setPayAmount(totalPayment);
				ppp=  packetPaymentMembershipRepository.save(ppp);
				
			}
			
			hmiResult.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResult.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		return hmiResult;
	}








	@Override
	public PacketPaymentFactory findPacketPaymentById(long id) {
		PacketPaymentMembership ppf=packetPaymentMembershipRepository.findOne(id);
		if(ppf!=null) {
			List<PacketPaymentMembershipDetail> pppd=packetPaymentMembershipDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
		}
		return ppf;
	}


	@Override
	public PacketPaymentFactory findPacketPaymentBySaleId(long saleId) {
		PacketPaymentMembership ppf=packetPaymentMembershipRepository.findBySaleId(saleId);
		if(ppf!=null) {
			List<PacketPaymentMembershipDetail> pppd=packetPaymentMembershipDetailRepository.findByPayId(ppf.getPayId());
			ppf.setPacketPaymentDetailFactories(pppd);
		}
		return ppf;
	}


	@Override
	public double findTotalIncomePaymentInDate(Date startDate, Date endDate, int firmId) {
		  List<PacketPaymentMembership> packetPaymentMemberships=packetPaymentMembershipRepository.findPacketPaymentMembershipForDate(startDate,endDate,firmId);
		  double totalPaymentForMembership=packetPaymentMemberships.stream().mapToDouble(ppp->ppp.getPayAmount()).sum();
		  return totalPaymentForMembership;
	}


	@Override
	public List<PacketPaymentFactory> findLast5packetPaymentsInChain(int firmId) {
		List<PacketPaymentFactory> packetPaymentFactories=new ArrayList<PacketPaymentFactory>();
		packetPaymentFactories.addAll(packetPaymentMembershipRepository.findLast5packetPayments(firmId));
		return packetPaymentFactories;
	}


	@Override
	public LeftPaymentInfo findLeftPacketPaymentsInChain(int firmId) {
		LeftPaymentInfo leftPaymentInfo=new LeftPaymentInfo();
		
		List<PacketPaymentMembership> packetPaymentMemberships= packetPaymentMembershipRepository.findLeftPacketPaymentMembership(firmId);
		double leftPP=packetPaymentMemberships
				.stream()
				.mapToDouble(ppp->{
					             	PacketSaleMembership packetSaleMembership=packetSaleMembershipRepository.findOne(ppp.getSaleId());              
					             	double payment= packetSaleMembership.getPacketPrice()-ppp.getPayAmount();
					             	return payment;
							    }
				).sum();
		
		leftPaymentInfo.setLeftPayment(leftPP);
		leftPaymentInfo.setLeftPaymentCount(leftPaymentInfo.getLeftPaymentCount());

		List<PacketSaleMembership> packetSaleMemberships=packetSaleMembershipRepository.findPacketSaleMembershipWithNoPayment(firmId);
		
		double leftPPN=packetSaleMemberships
				.stream()
				.mapToDouble(psp->psp.getPacketPrice()
				).sum();
		
		
		leftPaymentInfo.setNoPayment(leftPPN);
		leftPaymentInfo.setNoPaymentCount(packetSaleMemberships.size());
		
		return leftPaymentInfo;
	}

}
