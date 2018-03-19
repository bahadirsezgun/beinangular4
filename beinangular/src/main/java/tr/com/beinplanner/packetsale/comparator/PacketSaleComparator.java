package tr.com.beinplanner.packetsale.comparator;

import java.util.Comparator;

import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;

public class PacketSaleComparator implements Comparator<PacketSaleFactory> {

	@Override
    public int compare(PacketSaleFactory ps1, PacketSaleFactory ps2) {
        return ps2.getSalesDate().compareTo(ps1.getSalesDate());
    }
}
