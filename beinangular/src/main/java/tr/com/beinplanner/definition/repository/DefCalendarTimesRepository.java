package tr.com.beinplanner.definition.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.definition.dao.DefCalendarTimes;

@Repository
public interface DefCalendarTimesRepository extends CrudRepository<DefCalendarTimes, Long> {

	@Query(value="SELECT b.* " + 
			"				 FROM def_calendar_times b " + 
			"				 WHERE b.FIRM_ID=:firmId "
			+ "              LIMIT 1 ",nativeQuery=true )
	public DefCalendarTimes findDCTByFirmId(@Param("firmId") int firmId);
	
}
