package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.DeviceDataProfile;

@Repository
public interface DeviceDataProfileRepository extends JpaRepository<DeviceDataProfile, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_device_data_profile where data_profile_name=?")
	Optional<DeviceDataProfile> findByDataProfileName(String data_profile_name);

}
