package tr.com.beinplanner.user.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.user.dao.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findAllByFirmId(int firmId);

	@Query(value="SELECT a.* " + 
			"				 FROM user a" + 
			"				 WHERE a.USER_TYPE IN (2,3,4,5,7) AND a.FIRM_ID=:firmId",nativeQuery=true)
	public List<User> findAllStaffByFirmId(@Param("firmId") int firmId);

	
	List<User> findAllByFirmIdAndUserType(int firmId,int userType);
	
	Optional<User> findByUserEmail(String userEmail);
	
	@Query(value="SELECT a.* " + 
			"				 FROM user a" + 
			"				 WHERE a.USER_ID IN (SELECT b.USER_ID FROM schedule_users_personal_plan b, schedule_time_plan c,user d " + 
			"				                      WHERE b.SCHT_ID=c.SCHT_ID " + 
			"				  					AND d.USER_ID=b.USER_ID " + 
			"										AND d.FIRM_ID=:firmId " + 
			"				                       AND c.PLAN_START_DATE>(SELECT CURDATE()) " + 
			"				                       GROUP BY b.USER_ID)",nativeQuery=true)
	List<User> findActiveMemberInPersonalPlanning(@Param("firmId") int firmId);
	
	@Query(value="SELECT a.* " + 
			"				 FROM user a" + 
			"				 WHERE a.USER_ID IN (SELECT b.USER_ID FROM schedule_users_class_plan b, schedule_time_plan c,user d " + 
			"				                      WHERE b.SCHT_ID=c.SCHT_ID " + 
			"				  					AND d.USER_ID=b.USER_ID " + 
			"										AND d.FIRM_ID=:firmId " + 
			"				                       AND c.PLAN_START_DATE>(SELECT CURDATE()) " + 
			"				                       GROUP BY b.USER_ID)",nativeQuery=true)
	List<User> findActiveMemberInClassPlanning(@Param("firmId") int firmId);
	
	
	
	
	@Query(value="SELECT a.*" + 
		"	 FROM user a" + 
		"	 WHERE a.USER_ID IN (SELECT d.USER_ID FROM schedule_membership_plan b,user d " + 
		"	                      WHERE d.USER_ID=b.USER_ID" + 
		"							AND d.FIRM_ID=:firmId " + 
		"	                       AND b.SMP_END_DATE>(SELECT CURDATE())" + 
		"	                       GROUP BY b.USER_ID)",nativeQuery=true)
	List<User> findActiveMemberInMembershipPlanning(@Param("firmId") int firmId);


	List<User> findByUserNameStartingWithAndUserSurnameStartingWithAndFirmIdAndUserType(String userName,String userSurname,int firmId,int userType);
	
	
	
	@Query(value="SELECT a.* " + 
			"				 FROM user a " + 
			"				 WHERE a.USER_ID=(SELECT USER_ID FROM packet_sale_personal "
			+ "                                WHERE SALE_ID=(SELECT SALE_ID FROM packet_payment_personal WHERE PAY_ID=:payId)) ",nativeQuery=true)
	public User findUserByPersonalPayId(@Param("payId") long payId);

	@Query(value="SELECT a.* " + 
			"				 FROM user a " + 
			"				 WHERE a.USER_ID=(SELECT USER_ID FROM packet_sale_class "
			+ "                                WHERE SALE_ID=(SELECT SALE_ID FROM packet_payment_class WHERE PAY_ID=:payId)) ",nativeQuery=true)
	public User findUserByClassPayId(@Param("payId") long payId);

	@Query(value="SELECT a.* " + 
			"				 FROM user a " + 
			"				 WHERE a.USER_ID=(SELECT USER_ID FROM packet_sale_membership "
			+ "                                WHERE SALE_ID=(SELECT SALE_ID FROM packet_payment_membership WHERE PAY_ID=:payId)) ",nativeQuery=true)
	public User findUserByMembershipPayId(@Param("payId") long payId);

	
}
