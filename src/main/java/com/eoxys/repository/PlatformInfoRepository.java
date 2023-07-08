package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.PlatformInfo;

@Repository
public interface PlatformInfoRepository extends JpaRepository<PlatformInfo, Integer> {
	
	Optional<PlatformInfo> findByName(String username);
	
	@Query(nativeQuery=true,value="select * from tbl_platform_credentials where email=?")
	Optional<PlatformInfo> findByEmail(String email);

}
