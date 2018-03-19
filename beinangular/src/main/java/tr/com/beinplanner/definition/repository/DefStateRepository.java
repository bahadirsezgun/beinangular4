package tr.com.beinplanner.definition.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.definition.dao.DefState;

@Repository
public interface DefStateRepository extends CrudRepository<DefState, Long> {

}
