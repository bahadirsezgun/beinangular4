package tr.com.beinplanner.bonus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentPersonal;

@Repository
public interface UserBonusPaymentPersonalRepository extends CrudRepository<UserBonusPaymentPersonal, Long> {

	@Query(value="SELECT a.* FROM user_bonus_payment_personal a " + 
			"				 WHERE a.BON_START_DATE>=:startDate  " + 
			"				   AND a.BON_START_DATE<:endDate " + 
			"				   AND a.USER_ID=:schStaffId  " + 
			"				 ORDER BY a.BON_START_DATE",nativeQuery=true)
	 public List<UserBonusPaymentPersonal>	findUserBonusPaymentPersonalByDate(@Param("schStaffId") long schStaffId,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate);

	@Query(value="SELECT a.* FROM user_bonus_payment_personal a " + 
			"				 WHERE a.BON_START_DATE>=:startDate  " + 
			"				   AND a.BON_START_DATE<:endDate " + 
			"				   AND a.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)  " + 
			"				 ORDER BY a.BON_START_DATE",nativeQuery=true)
	 public List<UserBonusPaymentPersonal>	findTotalOfDateBonusPaymentPersonal(@Param("firmId") long firmId,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate);


	@Query(value="SELECT a.* FROM user_bonus_payment_personal a " + 
			"				 WHERE a.BON_END_DATE>=:startDate  " + 
			"				   AND a.USER_ID=:schStaffId  " + 
			"				 ORDER BY a.BON_START_DATE",nativeQuery=true)
	 public List<UserBonusPaymentPersonal>	controlUserBonusPaymentPersonalByDate(@Param("schStaffId") long schStaffId,@Param("startDate")  Date startDate);

	
	
	public List<UserBonusPaymentPersonal> findByFirmIdAndBonMonthAndBonYear(int firmId, int month ,int year);
	
	public List<UserBonusPaymentPersonal> findByUserIdAndBonMonthAndBonYear(long userId, int month ,int year);
}
