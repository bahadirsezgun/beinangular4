package tr.com.beinplanner.definition.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.definition.dao.DefCity;

@Repository
public interface DefCityRepository extends CrudRepository<DefCity, Long> {

}
