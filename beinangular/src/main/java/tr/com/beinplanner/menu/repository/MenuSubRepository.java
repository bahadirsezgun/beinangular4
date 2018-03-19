package tr.com.beinplanner.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tr.com.beinplanner.menu.dao.MenuSubTbl;

public interface MenuSubRepository extends CrudRepository<MenuSubTbl, Long> {

	

	@Query(value="SELECT a.* FROM menu_tbl a,menu_role_tbl b " + 
			"		 WHERE a.MENU_ID=b.MENU_ID " + 
			"		 AND b.ROLE_ID=:userType  " + 
			"		 AND a.UPPER_MENU_ID=:upperMenu  " + 
			"		 AND b.FIRM_ID=:firmId  " + 
			"		 AND a.MENU_SIDE=0  " + 
			"		  ORDER BY a.MENU_ID ",nativeQuery=true)
	List<MenuSubTbl> findSideSubMenuByUserType(@Param("userType") int userType,@Param("upperMenu")  long upperMenu,@Param("firmId") long firmId);
	
	
}
