package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.DeletedOrganization;

@Repository
public interface DeletedOrganizationRepository extends JpaRepository<DeletedOrganization, Long> {
	@Query(nativeQuery=true,value="select * from tbl_deleted_organization where organization_name=?")
	Optional<DeletedOrganization> findByOrgName(String organization_name);
}
