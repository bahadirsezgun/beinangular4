package tr.com.beinplanner.packetpayment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonalDetail;
@Repository
public interface PacketPaymentPersonalDetailRepository  extends CrudRepository<PacketPaymentPersonalDetail, Long> {

	public List<PacketPaymentPersonalDetail> findByPayId(long payId);
	
	
	@Query(value="SELECT a.* " + 
			"				FROM packet_payment_personal_detail a,packet_payment_personal b " + 
			"				 WHERE a.PAY_DATE>=:payStartDate  " + 
			"			    AND a.PAY_DATE<:payEndDate"
			+ "             AND a.PAY_ID=b.PAY_ID " + 
			"				 AND b.SALE_ID IN (SELECT SALE_ID FROM packet_sale_personal WHERE USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId))"
			+ " ORDER BY PAY_DATE DESC ",nativeQuery=true )
	public List<PacketPaymentPersonalDetail> findIncomePaymentDetailsInDates(@Param("payStartDate") Date payStartDate,@Param("payEndDate") Date payEndDate,@Param("firmId") int firmId);
	
	
}
