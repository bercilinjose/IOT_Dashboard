package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.OrganizationProfile;

@Repository
public interface OrganizationProfileRepository extends JpaRepository<OrganizationProfile, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_organization_profile where organization_profile_name=?")
	Optional<OrganizationProfile> findByOrgProfileName(String organization_profile_name);

}
