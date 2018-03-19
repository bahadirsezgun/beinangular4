package tr.com.beinplanner.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.program.dao.ProgramPersonal;

@Repository
public interface ProgramPersonalRepository extends CrudRepository<ProgramPersonal, Long>{

	List<ProgramPersonal> findByFirmId(int firmId);
	
	
	@Query(value="SELECT a.*  " + 
			"		FROM program_personal a  " + 
			"				   WHERE a.PROG_ID =(SELECT PROG_ID FROM schedule_plan b, schedule_time_plan c"
			+ "                                   WHERE b.SCH_ID=c.SCH_ID AND c.SCHT_ID=:schtId ) ",nativeQuery=true)
	public ProgramPersonal findProgramPersonalByTimePlan(@Param("schtId")  long schtId);
	
	
	
	
}
