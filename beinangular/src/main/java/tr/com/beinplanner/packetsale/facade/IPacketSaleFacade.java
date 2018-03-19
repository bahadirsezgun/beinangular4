package tr.com.beinplanner.packetsale.facade;

import java.util.Date;

import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.result.HmiResultObj;

public interface IPacketSaleFacade {

	public HmiResultObj canSaleDelete(PacketSaleFactory packetSaleFactory);
	
	public HmiResultObj canSale(long userId,Date startDate,long saleId);
	
	
}
