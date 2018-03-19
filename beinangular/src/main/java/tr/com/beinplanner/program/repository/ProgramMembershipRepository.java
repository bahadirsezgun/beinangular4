package tr.com.beinplanner.program.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.program.dao.ProgramPersonal;

@Repository
public interface ProgramMembershipRepository extends CrudRepository<ProgramMembership, Long>{

	List<ProgramMembership> findByFirmId(int firmId);
	
}
