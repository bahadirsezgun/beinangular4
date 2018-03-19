package tr.com.beinplanner.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.dao.ScheduleUsersClassPlan;

@Repository
public interface ScheduleUsersClassPlanRepository  extends CrudRepository<ScheduleUsersClassPlan, Long> {

	public List<ScheduleUsersClassPlan> findBySchtId(long schtId);
	
	public List<ScheduleUsersClassPlan> findBySaleId(long saleId);
	
	
	@Query(value="SELECT SUM(0) SUCP_ID,SUM(0) SCHT_ID,b.USER_ID,b.SALE_ID" + 
			"				  FROM 	schedule_time_plan a,  " + 
			"						schedule_users_class_plan b, " + 
			"				        schedule_plan c  " + 
			"					 WHERE a.SCHT_ID=b.SCHT_ID " + 
			"					   AND c.SCH_ID=a.SCH_ID  " + 
			"					   AND c.SCH_ID=:schId  " + 
			"					 GROUP BY b.USER_ID,b.SALE_ID ",nativeQuery=true)
	public List<ScheduleUsersClassPlan> findScheduleUsersPlanBySchId(@Param("schId") long schId);
	
	
	
}
