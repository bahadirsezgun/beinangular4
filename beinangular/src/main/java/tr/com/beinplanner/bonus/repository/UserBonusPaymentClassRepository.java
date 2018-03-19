package tr.com.beinplanner.bonus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentClass;

@Repository
public interface UserBonusPaymentClassRepository  extends CrudRepository<UserBonusPaymentClass, Long> {

	@Query(value="SELECT a.* FROM user_bonus_payment_class a " + 
			"				 WHERE a.BON_START_DATE>=:startDate  " + 
			"				   AND a.BON_START_DATE<:endDate " + 
			"				   AND a.USER_ID=:schStaffId  " + 
			"				 ORDER BY a.BON_START_DATE",nativeQuery=true)
	 public List<UserBonusPaymentClass>	findUserBonusPaymentClassByDate(@Param("schStaffId") long schStaffId,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate);

	@Query(value="SELECT a.* FROM user_bonus_payment_class a " + 
			"				 WHERE a.BON_START_DATE>=:startDate  " + 
			"				   AND a.BON_START_DATE<:endDate " + 
			"				   AND a.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)  " + 
			"				 ORDER BY a.BON_START_DATE",nativeQuery=true)
	 public List<UserBonusPaymentClass>	findTotalOfDateBonusPaymentClass(@Param("firmId") int firmId,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate);

	
	@Query(value="SELECT a.* FROM user_bonus_payment_class a " + 
			"				 WHERE a.BON_END_DATE>=:startDate  " + 
			"				   AND a.USER_ID=:schStaffId  " + 
			"				 ORDER BY a.BON_START_DATE",nativeQuery=true)
	 public List<UserBonusPaymentClass>	controlUserBonusPaymentClassByDate(@Param("schStaffId") long schStaffId,@Param("startDate")  Date startDate);

	
	
	public List<UserBonusPaymentClass> findByFirmIdAndBonMonthAndBonYear(int firmId, int month ,int year);

	public List<UserBonusPaymentClass> findByUserIdAndBonMonthAndBonYear(long userId, int month ,int year);
	
	
	
	
}
