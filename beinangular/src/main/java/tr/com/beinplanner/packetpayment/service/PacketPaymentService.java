package tr.com.beinplanner.packetpayment.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.dashboard.businessEntity.LeftPaymentInfo;
import tr.com.beinplanner.packetpayment.business.IPacketPayment;
import tr.com.beinplanner.packetpayment.business.PacketPaymentClassBusiness;
import tr.com.beinplanner.packetpayment.business.PacketPaymentMembershipBusiness;
import tr.com.beinplanner.packetpayment.business.PacketPaymentPersonalBusiness;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetsale.service.PacketSaleService;
import tr.com.beinplanner.result.HmiResultObj;

@Service
@Qualifier("packetPaymentService")
public class PacketPaymentService {

	@Autowired
	PacketPaymentClassBusiness packetPaymentClassBusiness;
	
	@Autowired
	PacketPaymentPersonalBusiness packetPaymentPersonalBusiness;
	
	@Autowired
	PacketPaymentMembershipBusiness packetPaymentMembershipBusiness;
	
	@Autowired
	PacketSaleService packetSaleService;
	
	
	
	
	public HmiResultObj saveIt(PacketPaymentFactory ppf,IPacketPayment iPacketPayment) {
		return iPacketPayment.saveIt(ppf);
	}
	
	public HmiResultObj confirmIt(PacketPaymentFactory ppf,IPacketPayment iPacketPayment) {
		return iPacketPayment.confirmIt(ppf);
	}
	
	public HmiResultObj deleteDetail(PacketPaymentDetailFactory ppdf,IPacketPayment iPacketPayment) {
		return iPacketPayment.deleteDetail(ppdf);
	}
	
	public HmiResultObj deleteAll(PacketPaymentFactory ppf,IPacketPayment iPacketPayment) {
		return iPacketPayment.deleteAll(ppf);
	}
	
	
	public PacketPaymentFactory findPacketPaymentBySaleId(long saleId,IPacketPayment iPacketPayment) {
		return iPacketPayment.findPacketPaymentBySaleId(saleId);
	}
	
	public PacketPaymentFactory findPacketPaymentById(long Id,IPacketPayment iPacketPayment) {
		return iPacketPayment.findPacketPaymentById(Id);
	}
	
	
	
	
	public List<PacketPaymentDetailFactory> findIncomePaymentInMonth(Date startDate,Date endDate,int firmId) {
		IPacketPayment iPacketPayment=packetPaymentPersonalBusiness;
		return iPacketPayment.findIncomePaymentDetailsInDatesInChain(startDate, endDate, firmId);
	}
	
	
	public double findTotalIncomePaymentInDate(Date startDate,Date endDate,int firmId) {
		IPacketPayment iPacketPayment=packetPaymentPersonalBusiness;
		return iPacketPayment.findTotalIncomePaymentInDate(startDate, endDate, firmId);
	}
	
	public List<PacketPaymentFactory> findLast5packetPayments(int firmId){
		IPacketPayment iPacketPayment=packetPaymentPersonalBusiness;
		return iPacketPayment.findLast5packetPaymentsInChain(firmId);
	}
	
	public LeftPaymentInfo findLeftPacketPayments(int firmId){
		IPacketPayment iPacketPayment=packetPaymentPersonalBusiness;
		return iPacketPayment.findLeftPacketPaymentsInChain(firmId);
	}
	
}
