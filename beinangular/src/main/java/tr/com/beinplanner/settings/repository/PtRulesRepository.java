package tr.com.beinplanner.settings.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.settings.dao.PtRules;

@Repository
public interface PtRulesRepository extends CrudRepository<PtRules, Long> {

	public PtRules findByRuleIdAndFirmId(int ruleId,int firmId);
	
	public List<PtRules> findByFirmId(int firmId);
	
	
}
