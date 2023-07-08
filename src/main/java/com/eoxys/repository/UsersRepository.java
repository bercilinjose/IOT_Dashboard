package com.eoxys.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_users where email=?")
	Optional<Users> findByUserEmail(String email);
	
	@Query(nativeQuery=true,value="select * from tbl_users where organization=?")
	ArrayList<Users> findByUserOrg(Long id);

}
