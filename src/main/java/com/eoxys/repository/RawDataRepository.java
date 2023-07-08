package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.RawData;

@Repository
public interface RawDataRepository extends JpaRepository<RawData, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_raw_data where device_mac_id=?")
	Optional<RawData> findByDeviceId(String dev_id);

}