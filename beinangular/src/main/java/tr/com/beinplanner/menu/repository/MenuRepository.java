package tr.com.beinplanner.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tr.com.beinplanner.menu.dao.MenuTbl;

public interface MenuRepository extends CrudRepository<MenuTbl, Long> {

	@Query(value="SELECT a.* FROM menu_tbl a,menu_role_tbl b  "+
			" WHERE a.MENU_ID=b.MENU_ID " + 
			"  AND b.ROLE_ID=:userType " + 
			"  AND b.FIRM_ID=:firmId " + 
			"  AND a.MENU_SIDE=1 "
			+ "ORDER BY a.MENU_ID ",nativeQuery=true)
	List<MenuTbl> findTopMenuByUserType(@Param("userType") int userType,@Param("firmId")  long firmId);
	
	
	@Query(value="SELECT a.* FROM menu_tbl a,menu_role_tbl b  "+
			" WHERE a.MENU_ID=b.MENU_ID " + 
			"	 AND b.ROLE_ID=:userType " + 
			"	 AND a.UPPER_MENU_ID=0 " + 
			"	 AND a.MENU_SIDE=0 " + 
			"	 AND b.FIRM_ID=:firmId " + 
			"	 ORDER BY a.MENU_ID ",nativeQuery=true)
	 List<MenuTbl> findSideUpperMenuByUserType(@Param("userType") int userType,@Param("firmId")  long firmId) ;
	
	
	
	@Query(value="SELECT a.* FROM menu_tbl a,menu_role_tbl b  "+
			" WHERE a.MENU_ID=b.MENU_ID " + 
			"  AND b.ROLE_ID=:userType " + 
			"  AND b.FIRM_ID=:firmId " + 
			"  AND a.UPPER_MENU_ID=4 LIMIT 1 ",nativeQuery=true)
	MenuTbl findMenuDashboardByUserTypeAndFirmId(@Param("userType") int userType,@Param("firmId") long firmId);
	
	
	
	
}
