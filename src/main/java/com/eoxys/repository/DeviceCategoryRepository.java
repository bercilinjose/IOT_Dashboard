package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.DeviceCategory;

@Repository
public interface DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long> {
	
	@Query(nativeQuery = true , value = "select * from tbl_device_category where category_name=?" )
	Optional<DeviceCategory> findbyCategoryName(String cat_name);

}
