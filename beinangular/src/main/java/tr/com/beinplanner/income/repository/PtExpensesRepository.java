package tr.com.beinplanner.income.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.income.dao.PtExpenses;

@Repository
public interface  PtExpensesRepository  extends CrudRepository<PtExpenses, Long>  {

	@Query(value="SELECT * " + 
			"				  FROM pt_expenses " + 
			"				  WHERE PE_DATE>=:startDate " + 
			"				  AND PE_DATE<:endDate " + 
			"				  AND FIRM_ID=:firmId " + 
			"				  ORDER BY PE_DATE ",nativeQuery=true)
	List<PtExpenses> findPtExpensesForDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("firmId") int firmId );
	
}
