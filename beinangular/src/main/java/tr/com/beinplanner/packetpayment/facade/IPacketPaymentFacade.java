package tr.com.beinplanner.packetpayment.facade;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.result.HmiResultObj;

public interface IPacketPaymentFacade {

	public HmiResultObj canPaymentDelete(PacketPaymentFactory packetPaymentFactory);
	
	public HmiResultObj canPaymentDetailDelete(PacketPaymentDetailFactory ppdf);
	
	
	
}
