package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.UserProfiles;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_user_profiles where user_profile_name=?")
	Optional<UserProfiles> findByUserProfileName(String user_profile_name);

}
