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
public interface PacketSalePersonalRepository extends CrudRepository<PacketSalePersonal, Long> {

	@Query(value="SELECT b.* " + 
			"				 FROM packet_sale_personal b " + 
			"				 WHERE b.SALE_ID NOT IN (SELECT SALE_ID FROM packet_payment_personal a ) " + 
			"				 AND b.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)",nativeQuery=true )
	public List<PacketSalePersonal> findPacketSalePersonalWithNoPayment(@Param("firmId") int firmId);
	
	@Query(value="SELECT b.* " + 
			"				 FROM packet_sale_personal b,packet_payment_personal c " + 
			"				 WHERE b.SALE_ID=c.SALE_ID"
			+ "                AND b.PACKET_PRICE>c.PAY_AMOUNT " + 
			"				 AND b.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)",nativeQuery=true )
	public List<PacketSalePersonal> findPacketSalePersonalWithLeftPayment(@Param("firmId") int firmId);
	
	
	@Query(value="SELECT b.* " + 
			"				 FROM packet_sale_personal b " + 
			"				 WHERE b.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)"
			+ "              ORDER BY  SALES_DATE DESC "
			+ "              LIMIT 5 ",nativeQuery=true )
	public List<PacketSalePersonal> findLast5PacketSales(@Param("firmId") int firmId);
	
	
	public List<PacketSalePersonal> findByUserId(long userId);
	
	public List<PacketSalePersonal>  findByUserIdAndProgId(long userId,long progId);
	
	@Query(value="SELECT a.* " + 
			"				 FROM packet_sale_personal a " + 
			"				 WHERE a.SALE_ID =  (SELECT b.SALE_ID FROM schedule_users_personal_plan b,schedule_time_plan c"
			+ "                                    WHERE b.USER_ID=:userId "
			+ "                                      AND b.SALE_ID>0 "
			+ "                                      AND b.SCHT_ID=c.SCHT_ID"
			+ "                                      AND c.SCH_ID=:schId LIMIT 1 ) "
			+ "               " ,nativeQuery=true )
	public PacketSaleClass findPacketSaleBySchIdAndUserId(@Param("schId") long schId,@Param("userId") long userId);
	
}
