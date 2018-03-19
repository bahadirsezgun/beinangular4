package tr.com.beinplanner.settings.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.settings.dao.PtAction;

@Repository
public interface PtActionRepository extends CrudRepository<PtAction, Long> {

}
