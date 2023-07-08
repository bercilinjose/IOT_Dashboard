package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.DeviceProfile;

@Repository
public interface DeviceProfileRepository extends JpaRepository<DeviceProfile, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_device_profile where device_profile_name=?")
	Optional<DeviceProfile> findByDeviceProfileName(String device_profile_name);

}
