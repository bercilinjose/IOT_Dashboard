package com.eoxys.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.AlarmEventList;
import java.util.ArrayList;

@Repository
public interface AlarmEventListRepository extends JpaRepository<AlarmEventList, Long> {
	

	@Query(nativeQuery=true,value="select * from tbl_alarm_event_list where device_mac_id=?")
	ArrayList<AlarmEventList> findByDeviceId(String dev_id);
}
