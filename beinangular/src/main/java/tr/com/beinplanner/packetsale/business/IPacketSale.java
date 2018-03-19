package tr.com.beinplanner.packetsale.business;

import java.util.List;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.result.HmiResultObj;

public interface IPacketSale {


	
	public HmiResultObj saleIt(PacketSaleFactory packetSaleFactory);
	
	public HmiResultObj deleteIt(PacketSaleFactory packetSaleFactory);
	
	public PacketSaleFactory findPacketSaleById(long saleId);
	
	public PacketSaleFactory findPacketSaleBySchIdAndUserId(long schId,long userId);
	
	public List<PacketSaleFactory> findPacketSaleWithNoPayment(int firmId);
	
	public List<PacketSaleFactory> findAllSalesForUserInChain(long userId);
	
	public List<PacketSaleFactory> findAllSalesForCalendarUserInChain(long userId);
	
	public List<PacketSaleFactory> findLast5PacketSalesInChain(int firmId);
	
	public List<PacketSaleFactory> findLeftPaymentsInChain(int firmId);
	
	
	public List<PacketSaleFactory> findFreeSalesForUserByProgId(long userId,long progId);

	
	
	
}
