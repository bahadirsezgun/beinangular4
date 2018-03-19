package tr.com.beinplanner.definition.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.definition.dao.DefFirm;

@Repository
public interface DefFirmRepository  extends CrudRepository<DefFirm, Integer>{

	
	public DefFirm findByFirmEmail(String email);
}
