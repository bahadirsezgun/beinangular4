package tr.com.beinplanner.packetpayment.comparator;

import java.util.Comparator;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;

public class PacketPaymentComparator implements Comparator<PacketPaymentFactory> {

	@Override
    public int compare(PacketPaymentFactory ps1, PacketPaymentFactory ps2) {
        return ps2.getPayDate().compareTo(ps1.getPayDate());
    }

}
