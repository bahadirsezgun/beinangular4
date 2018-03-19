package tr.com.beinplanner.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tr.com.beinplanner.user.dao.UserPotential;

public interface PotentialUserRepository  extends CrudRepository<UserPotential, Long>{

	List<UserPotential> findByUserNameStartingWithAndUserSurnameStartingWithAndFirmId(String userName,String userSurname,int firmId);
	
}
