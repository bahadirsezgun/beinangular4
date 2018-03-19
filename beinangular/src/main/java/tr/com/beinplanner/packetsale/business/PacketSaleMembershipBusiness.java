package tr.com.beinplanner.packetsale.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import tr.com.beinplanner.packetpayment.business.PacketPaymentMembershipBusiness;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembership;
import tr.com.beinplanner.packetpayment.service.PacketPaymentService;
import tr.com.beinplanner.packetsale.comparator.PacketSaleComparator;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.packetsale.facade.IPacketSaleFacade;
import tr.com.beinplanner.packetsale.repository.PacketSaleMembershipRepository;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipTimePlan;
import tr.com.beinplanner.schedule.service.ScheduleMembershipService;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.SaleStatus;
import tr.com.beinplanner.util.StatuTypes;

@Component
@Qualifier("packetSaleMembershipBusiness")
public class PacketSaleMembershipBusiness implements IPacketSale {

	@Autowired
	PacketSaleMembershipRepository packetSaleMembershipRepository;
		
	@Autowired
	PacketPaymentService packetPaymentService;
	
	@Autowired
	PacketPaymentMembershipBusiness packetPaymentMembershipBusiness;
	
	
	@Autowired
	@Qualifier("packetSaleMembershipFacade")
	IPacketSaleFacade iPacketSaleFacade;
	
	
	@Autowired
	ScheduleMembershipService scheduleMembershipService;
	
	
	@Override
	public HmiResultObj saleIt(PacketSaleFactory packetSaleFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		PacketSaleMembership psm=(PacketSaleMembership)packetSaleFactory;
		
			Date smpStartDate=(Date)psm.getSmpStartDate().clone();
			hmiResultObj= iPacketSaleFacade.canSale(psm.getUserId(), smpStartDate,psm.getSaleId());
		
			if(hmiResultObj.getResultStatu()==ResultStatuObj.RESULT_STATU_SUCCESS_STR){
				psm.setChangeDate(new Date());
				try {
					psm=packetSaleMembershipRepository.save(psm);
					
					hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
					hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
					hmiResultObj.setResultObj(psm);

					
					
					ScheduleMembershipPlan scheduleFactory=(ScheduleMembershipPlan)scheduleMembershipService.findScheduleFactoryPlanBySaleId(psm.getSaleId());
					if(scheduleFactory==null)
						scheduleFactory=new ScheduleMembershipPlan();
					
					scheduleFactory.setSmpComment(psm.getSalesComment());
					scheduleFactory.setProgId(psm.getProgId());
					scheduleFactory.setSaleId(psm.getSaleId());
					scheduleFactory.setUserId(psm.getUserId());
					scheduleFactory.setSmpStartDate(smpStartDate);
					scheduleFactory.setSmpFreezeCount(0);
					scheduleFactory.setSmpPrice(psm.getPacketPrice());
					scheduleFactory.setSmpStatus(StatuTypes.ACTIVE);
					
					hmiResultObj=scheduleMembershipService.createPlan(scheduleFactory);
				} catch (Exception e) {
					hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
					hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
				}
		    }
			
			return hmiResultObj;
	}
	
	@Override
	public HmiResultObj deleteIt(PacketSaleFactory packetSaleFactory) {
		HmiResultObj hmiResultObj= iPacketSaleFacade.canSaleDelete(packetSaleFactory);
		if(hmiResultObj.getResultStatu().equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
			packetSaleMembershipRepository.delete((PacketSaleMembership)packetSaleFactory);
		}
		return hmiResultObj;
	}
	
	@Override
	public List<PacketSaleFactory> findPacketSaleWithNoPayment(int firmId) {
		List<PacketSaleFactory> packetSaleFactories=new ArrayList<PacketSaleFactory>();
		List<PacketSaleMembership> packetSaleMemberships=packetSaleMembershipRepository.findPacketSaleMembershipWithNoPayment(firmId);
		packetSaleFactories.addAll(packetSaleMemberships);
		
		Collections.sort(packetSaleFactories,new PacketSaleComparator());
		
		return packetSaleFactories;
	}



	@Override
	public List<PacketSaleFactory> findLeftPaymentsInChain(int firmId) {
		List<PacketSaleFactory> packetSaleFactories=new ArrayList<>();
		
		
		List<PacketSaleMembership> packetSaleMembershipsNoPayment=packetSaleMembershipRepository.findPacketSaleMembershipWithNoPayment(firmId);
		
		List<PacketSaleMembership> packetSaleMembershipsLeftPayment=packetSaleMembershipRepository.findPacketSaleMembershipWithLeftPayment(firmId);
		packetSaleMembershipsLeftPayment.forEach(pslp->{
			pslp.setPacketPaymentFactory((PacketPaymentMembership)packetPaymentService.findPacketPaymentBySaleId(pslp.getSaleId(), packetPaymentMembershipBusiness));
		});
		
		
		packetSaleFactories.addAll(packetSaleMembershipsLeftPayment);
		packetSaleFactories.addAll(packetSaleMembershipsNoPayment);
		return packetSaleFactories;
	}

	@Override
	public PacketSaleFactory findPacketSaleById(long saleId) {
		PacketSaleMembership psf=packetSaleMembershipRepository.findOne(saleId);
		psf.setSaleStatu(getSaleStatu(psf.getSaleId(),null));
		return psf;
	}


	private int getSaleStatu(long saleId,ScheduleMembershipPlan sf) {
		if(sf==null)
		  sf= (ScheduleMembershipPlan)scheduleMembershipService.findScheduleFactoryPlanBySaleId(saleId);
		
		
		if(sf.getSmpEndDate().before(OhbeUtil.getTodayDate())){
			return SaleStatus.SALE_COMPLETED_PLANNED;
		}else if(sf.getSmpStartDate().after(new Date())){
			return SaleStatus.SALE_NOT_STARTED_PLANNED;
		}else {
			return SaleStatus.SALE_CONTINUE_PLANNED;
		}
	}

	@Override
	public List<PacketSaleFactory> findLast5PacketSalesInChain(int firmId) {
		List<PacketSaleFactory> packetSaleFactories=new ArrayList<PacketSaleFactory>();
		List<PacketSaleMembership> packetSaleMemberships=packetSaleMembershipRepository.findLast5PacketSales(firmId);
		packetSaleFactories.addAll(packetSaleMemberships);
		return packetSaleFactories;
	}



	@Override
	public List<PacketSaleFactory> findAllSalesForUserInChain(long userId) {
		
		List<PacketSaleFactory> psfs=new ArrayList<>();
		
		packetSaleMembershipRepository.findByUserId(userId).forEach(psp->{
			psp.setPacketPaymentFactory((PacketPaymentMembership)packetPaymentService.findPacketPaymentBySaleId(psp.getSaleId(),packetPaymentMembershipBusiness));
			
			ScheduleMembershipPlan scheduleFactory= (ScheduleMembershipPlan)scheduleMembershipService.findScheduleFactoryPlanBySaleId(psp.getSaleId());
			if(scheduleFactory!=null) {
				if(psp.getScheduleFactory()==null) {
					psp.setScheduleFactory(new ArrayList<>());
				}
				psp.getScheduleFactory().add(scheduleFactory);
			}
			psp.setSmpStartDate((Date)scheduleFactory.getSmpStartDate().clone());
			psp.setSaleStatu(getSaleStatu(psp.getSaleId(),scheduleFactory));
			
			
			psfs.add((PacketSaleFactory)psp);
			
		});
		return psfs;
   }

	@Override
	public List<PacketSaleFactory> findAllSalesForCalendarUserInChain(long userId) {
		
		return null;
	}

	@Override
	public List<PacketSaleFactory> findFreeSalesForUserByProgId(long userId, long progId) {
		
		return null;
	}

	@Override
	public PacketSaleFactory findPacketSaleBySchIdAndUserId(long schId, long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
