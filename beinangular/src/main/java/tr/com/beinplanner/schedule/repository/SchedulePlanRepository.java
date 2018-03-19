package tr.com.beinplanner.schedule.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.schedule.dao.SchedulePlan;

@Repository
public interface SchedulePlanRepository extends CrudRepository<SchedulePlan, Long> {

	@Query(value="SELECT a.*" + 
			"		FROM schedule_plan a"
			+ "     WHERE a.SCH_ID= (SELECT b.sch_id FROM "
			+ "                 schedule_time_plan b, schedule_users_personal_plan c" + 
			"					 WHERE b.SCHT_ID=c.SCHT_ID" + 
			"					 AND c.SALE_ID=:saleId "
			+ "                  GROUP BY b.sch_id) ",nativeQuery=true )
	public SchedulePlan findSchedulePlanPersonalBySaleId(@Param("saleId") long saleId);
	
	@Query(value="SELECT a.*" + 
			"		FROM schedule_plan a"
			+ "     WHERE a.SCH_ID= (SELECT b.sch_id FROM "
			+ "                 schedule_time_plan b, schedule_users_class_plan c" + 
			"					 WHERE b.SCHT_ID=c.SCHT_ID" + 
			"					 AND c.SALE_ID=:saleId "
			+ "                  GROUP BY b.sch_id) ",nativeQuery=true )
	public SchedulePlan findSchedulePlanClassBySaleId(@Param("saleId") long saleId);
	
	
}
