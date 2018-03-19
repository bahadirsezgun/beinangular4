package tr.com.beinplanner.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import tr.com.beinplanner.program.dao.ProgramMembershipDetail;

@Repository
public interface ProgramMembershipDetailRepository extends CrudRepository<ProgramMembershipDetail, Long>{

	List<ProgramMembershipDetail> findByProgId(long progId);
	
	
	
	
	
}
