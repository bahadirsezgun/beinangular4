package tr.com.beinplanner.settings.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.settings.dao.DbMailTbl;

@Repository
public interface DbMailTblRepository extends CrudRepository<DbMailTbl, Long> {

}
