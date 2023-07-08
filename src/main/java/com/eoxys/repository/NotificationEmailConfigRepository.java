package com.eoxys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.NotificationEmailConfig;

@Repository
public interface NotificationEmailConfigRepository extends JpaRepository<NotificationEmailConfig, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_email_config where content_name=?")
	Optional<NotificationEmailConfig> findByContentName(String content_name);

}


