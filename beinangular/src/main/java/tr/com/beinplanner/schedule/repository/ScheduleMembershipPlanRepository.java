package tr.com.beinplanner.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;

@Repository
public interface ScheduleMembershipPlanRepository extends CrudRepository<ScheduleMembershipPlan, Long> {

	@Query(value="SELECT a.* "
			+ "  FROM schedule_membership_plan a " + 
			"						 WHERE a.SMP_END_DATE>=:startDate " + 
			"						and a.SMP_END_DATE<:endDate "
			+ "                     AND a.USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId)",nativeQuery=true)
	List<ScheduleMembershipPlan> findLastOfClasses(@Param ("startDate") Date startDate,@Param ("endDate") Date endDate ,@Param ("firmId") int firmId );
	
	
	public ScheduleMembershipPlan findBySaleId(long saleId);	
	
	public List<ScheduleMembershipPlan> findByUserId(long userId);	
	
	
}
