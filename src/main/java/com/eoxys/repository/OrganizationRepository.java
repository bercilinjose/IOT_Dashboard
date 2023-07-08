package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_organization where organization_name=?")
	Optional<Organization> findByOrgName(String organization_name);

}
