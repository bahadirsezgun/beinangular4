package tr.com.beinplanner.menu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tr.com.beinplanner.menu.dao.MenuRoleEmbededId;
import tr.com.beinplanner.menu.dao.MenuRoleTbl;

public interface MenuRoleRepository extends CrudRepository<MenuRoleTbl, MenuRoleEmbededId> {

	@Query(value="SELECT a.* FROM menu_role_tbl a  "+
			" WHERE a.ROLE_ID=:roleId " + 
			"  AND a.FIRM_ID=:firmId "
			+ " AND a.MENU_ID IN (800,801,802,803,804,805,806)" + 
			"  LIMIT 1 ",nativeQuery=true)
	public MenuRoleTbl findDashboardMenuRole(@Param("roleId") int roleId,@Param("firmId") int firmId);
}
