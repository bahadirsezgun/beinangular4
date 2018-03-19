package tr.com.beinplanner.packetsale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetpayment.service.PacketPaymentService;
import tr.com.beinplanner.packetsale.business.IPacketSale;
import tr.com.beinplanner.packetsale.business.PacketSaleClassBusiness;
import tr.com.beinplanner.packetsale.business.PacketSaleMembershipBusiness;
import tr.com.beinplanner.packetsale.business.PacketSalePersonalBusiness;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.result.HmiResultObj;

@Service
@Qualifier("packetSaleService")
public class PacketSaleService {

	@Autowired
	LoginSession loginSession;
	
	@Autowired
	PacketSaleClassBusiness packetSaleClassBusiness;

	@Autowired
	PacketSalePersonalBusiness packetSalePersonalBusiness;
	
	@Autowired
	PacketSaleMembershipBusiness packetSaleMembershipBusiness;
	
	@Autowired
	PacketPaymentService packetPaymentService;
	
	
	
	
	public synchronized List<PacketSaleFactory> findUserBoughtPackets(long userId){
		IPacketSale iPacketSale=packetSalePersonalBusiness;
		return iPacketSale.findAllSalesForUserInChain(userId);
	}
	
	public synchronized List<PacketSaleFactory> findUserBoughtPacketsForCalendar(long userId){
		IPacketSale iPacketSale=packetSalePersonalBusiness;
		return iPacketSale.findAllSalesForCalendarUserInChain(userId);
	}
	
	
	public synchronized PacketSaleFactory findPacketSaleById(long saleId,IPacketSale iPacketSale) {
		return iPacketSale.findPacketSaleById(saleId);
	}
	
	public synchronized PacketSaleFactory findPacketSaleBySchIdAndUserId(long schId,long userId,IPacketSale iPacketSale) {
		return iPacketSale.findPacketSaleBySchIdAndUserId(schId,userId);
	}
	
	
	
	public synchronized List<PacketSaleFactory> findPacketSaleWithNoPayment(int firmId,IPacketSale iPacketSale){
		return iPacketSale.findPacketSaleWithNoPayment(firmId);
	}
	
	public synchronized List<PacketSaleFactory> findLeftPaymentsInChain(int firmId){
		IPacketSale iPacketSale=packetSalePersonalBusiness;
		return iPacketSale.findLeftPaymentsInChain(firmId);
	}
	
	public List<PacketSaleFactory> findLast5PacketSales(int firmId){
		IPacketSale iPacketSale=packetSalePersonalBusiness;
		return iPacketSale.findLast5PacketSalesInChain(firmId);
	}
	
	public synchronized HmiResultObj sale(PacketSaleFactory psf){
		IPacketSale iPacketSale=null;
		if(psf instanceof PacketSalePersonal)
			iPacketSale=packetSalePersonalBusiness;
		else if (psf instanceof PacketSaleClass)
			iPacketSale=packetSaleClassBusiness;
		else if (psf instanceof PacketSaleMembership)
			iPacketSale=packetSaleMembershipBusiness;
		
		return iPacketSale.saleIt(psf);
	}
	
	public synchronized HmiResultObj deletePacketSale(PacketSaleFactory psf){
		IPacketSale iPacketSale=null;
		if(psf instanceof PacketSalePersonal)
			iPacketSale=packetSalePersonalBusiness;
		else if (psf instanceof PacketSaleClass)
			iPacketSale=packetSaleClassBusiness;
		else if (psf instanceof PacketSaleMembership)
			iPacketSale=packetSaleMembershipBusiness;
		
		return iPacketSale.deleteIt(psf);
	}
	
}
