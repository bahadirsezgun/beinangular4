package tr.com.beinplanner.packetpayment.business;

import java.util.Date;
import java.util.List;

import tr.com.beinplanner.dashboard.businessEntity.LeftPaymentInfo;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.entity.PaymentConfirmQuery;
import tr.com.beinplanner.result.HmiResultObj;

public interface IPacketPayment {

	public PacketPaymentFactory findPacketPaymentBySaleId(long saleId);
	
	public PacketPaymentFactory findPacketPaymentById(long Id);
	
	
	
	public LeftPaymentInfo findLeftPacketPaymentsInChain(int firmId);
	
	
	
	public List<PacketPaymentFactory> findLast5packetPaymentsInChain(int firmId);
	
	public double findTotalIncomePaymentInDate(Date startDate,Date endDate,int firmId);
	
	public List<PacketPaymentFactory> findPaymentsToConfirmInChain(PaymentConfirmQuery pcq,int firmId);
	
	
	
	public HmiResultObj saveIt(PacketPaymentFactory packetPaymentFactory);
	
	public HmiResultObj confirmIt(PacketPaymentFactory packetPaymentFactory);
	
	public HmiResultObj deleteAll(PacketPaymentFactory packetPaymentFactory);
	
	public HmiResultObj deleteDetail(PacketPaymentDetailFactory packetPaymentDetailFactory);
	
	public List<PacketPaymentDetailFactory> findIncomePaymentDetailsInDatesInChain(Date startDate,Date endDate,int firmId);
	
	
}
