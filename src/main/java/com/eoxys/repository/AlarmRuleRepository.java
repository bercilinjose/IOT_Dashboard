package com.eoxys.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.AlarmRule;

@Repository
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {

	@Query(nativeQuery=true,value="select * from tbl_alarm_rule where alarm_name=?")
	Optional<AlarmRule> findAlarmName(String alarm_name);
	
	@Query(nativeQuery=true,value="select * from tbl_alarm_rule where data_profile=?")
	List<AlarmRule> findByDataProfile(String data_profile);
	
	
}
