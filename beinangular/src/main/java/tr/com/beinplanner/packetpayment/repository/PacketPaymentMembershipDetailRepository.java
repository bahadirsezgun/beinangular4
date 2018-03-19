package tr.com.beinplanner.packetpayment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentMembershipDetail;
@Repository
public interface PacketPaymentMembershipDetailRepository  extends CrudRepository<PacketPaymentMembershipDetail, Long> {

	public List<PacketPaymentMembershipDetail> findByPayId(long payId);
	
	@Query(value="SELECT a.* " + 
			"				FROM packet_payment_membership_detail a,packet_payment_membership b " + 
			"				 WHERE a.PAY_DATE>=:payStartDate  " + 
			"			    AND a.PAY_DATE<:payEndDate"
			+ "             AND a.PAY_ID=b.PAY_ID " + 
			"				 AND  b.SALE_ID IN (SELECT SALE_ID FROM packet_sale_membership WHERE USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId))"
			+ " ORDER BY PAY_DATE DESC ",nativeQuery=true )
	public List<PacketPaymentMembershipDetail> findIncomePaymentDetailsInDates(@Param("payStartDate") Date payStartDate,@Param("payEndDate") Date payEndDate,@Param("firmId") int firmId);
	
}
