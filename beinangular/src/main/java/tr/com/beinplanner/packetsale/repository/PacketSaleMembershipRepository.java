package tr.com.beinplanner.packetsale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
@Repository
public interface PacketSaleMembershipRepository extends CrudRepository<PacketSaleMembership, Long> {

	@Query(value="SELECT b.* " + 
			"				 FROM packet_sale_membership b " + 
			"				 WHERE b.SALE_ID NOT IN (SELECT SALE_ID FROM packet_payment_membership a ) " + 
			"				 AND b.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)",nativeQuery=true )
	public List<PacketSaleMembership> findPacketSaleMembershipWithNoPayment(@Param("firmId") int firmId);
	
	
	@Query(value="SELECT b.* " + 
			"				 FROM packet_sale_membership b,packet_payment_membership c " + 
			"				 WHERE b.SALE_ID=c.SALE_ID"
			+ "                AND b.PACKET_PRICE>c.PAY_AMOUNT " + 
			"				 AND b.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)",nativeQuery=true )
	public List<PacketSaleMembership> findPacketSaleMembershipWithLeftPayment(@Param("firmId") int firmId);
	
	
	
	@Query(value="SELECT b.* " + 
			"				 FROM packet_sale_membership b " + 
			"				 WHERE b.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)"
			+ "              ORDER BY  SALES_DATE DESC "
			+ "              LIMIT 5 ",nativeQuery=true )
	public List<PacketSaleMembership> findLast5PacketSales(@Param("firmId") int firmId);

	
	public List<PacketSaleMembership> findByUserId(long userId);
	
	
}
