package tr.com.beinplanner.settings.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.settings.dao.PtGlobal;

@Repository
public interface PtGlobalRepository extends CrudRepository<PtGlobal, Long>{

	PtGlobal findByFirmId(int firmId);
	
}
