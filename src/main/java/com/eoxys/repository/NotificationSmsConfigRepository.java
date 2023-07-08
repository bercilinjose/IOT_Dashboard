package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.NotificationSmsConfig;

@Repository
public interface NotificationSmsConfigRepository extends JpaRepository<NotificationSmsConfig, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_sms_config where content_name=?")
	Optional<NotificationSmsConfig> findByContentName(String content_name);

}
