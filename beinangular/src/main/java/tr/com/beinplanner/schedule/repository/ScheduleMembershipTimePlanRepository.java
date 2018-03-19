package tr.com.beinplanner.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.schedule.dao.ScheduleMembershipTimePlan;

@Repository
public interface ScheduleMembershipTimePlanRepository  extends CrudRepository<ScheduleMembershipTimePlan, Long> {

	
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_membership_time_plan a " + 
			"						 WHERE a.SMP_START_DATE>=:startDate " + 
			"						and a.SMP_START_DATE<:endDate AND a.SMP_ID IN "
			+ "                      (SELECT b.SMP_ID FROM schedule_membership_plan b "
			+ "                         WHERE b.USER_ID IN (SELECT c.USER_ID FROM user c WHERE c.FIRM_ID=:firmId))",nativeQuery=true)
	public List<ScheduleMembershipTimePlan> findScheduleMembershipTimePlan(@Param ("startDate") Date startDate,@Param ("endDate") Date endDate,@Param ("firmId") int firmId );

	
	public List<ScheduleMembershipTimePlan> findBySmpId(long smpId);
	
}
