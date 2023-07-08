package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.DeletedRawData;

@Repository
public interface DeletedRawDataRepository extends JpaRepository<DeletedRawData, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_deleted_raw_data where device_mac_id=?")
	Optional<DeletedRawData> findByDeviceId(String dev_id);

}
