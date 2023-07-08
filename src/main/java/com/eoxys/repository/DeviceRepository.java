package com.eoxys.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_device where device_mac_id=?")
	Optional<Device> findByDeviceId(String dev_id);
	
	@Query(nativeQuery=true,value="select * from tbl_device where device_name=?")
	Optional<Device> findByDevicename(String dev_name);
	
	@Query(nativeQuery=true,value="select * from tbl_device where bpid=?")
	Optional<Device> findByDeviceBpid(String bpid);
	
//	@Query(nativeQuery=true,value="select * from tbl_device where organization_id=?")
//	ArrayList<Device> findByDevOrg(Long id);
	
	@Query(nativeQuery=true,value="select * from tbl_device where user_id=?")
	ArrayList<Device> findByDevUser(Long id);
 
	
//	@Query(nativeQuery=true,value="select * from tbl_device where device_id=?")
//	Optional<Device> findByIdD(Long dev_id);
}
