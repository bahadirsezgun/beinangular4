package tr.com.beinplanner.settings.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.settings.dao.PtRestrictions;

@Repository
public interface PtRestrictionsRepository extends CrudRepository<PtRestrictions, Long> {

}
