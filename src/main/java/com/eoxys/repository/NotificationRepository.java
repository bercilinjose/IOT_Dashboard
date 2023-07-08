package com.eoxys.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoxys.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	@Query(nativeQuery=true,value="select * from tbl_notification where notification_receiver=?")
	ArrayList<Notification> findByNotifyOrg(Long id);

}
