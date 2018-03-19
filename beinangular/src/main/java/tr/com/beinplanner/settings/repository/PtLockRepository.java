package tr.com.beinplanner.settings.repository;

import org.springframework.data.repository.CrudRepository;

import tr.com.beinplanner.settings.dao.PtLock;

public interface PtLockRepository extends CrudRepository<PtLock, Integer>{

}
