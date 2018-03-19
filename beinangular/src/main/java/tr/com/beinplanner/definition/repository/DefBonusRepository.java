package tr.com.beinplanner.definition.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.definition.dao.DefBonus;

@Repository
public interface DefBonusRepository extends CrudRepository<DefBonus, Long>{

	public List<DefBonus> findByUserIdAndBonusTypeAndBonusIsType(long userId,int bonusType,int bonusIsType);
	
	
	
}
