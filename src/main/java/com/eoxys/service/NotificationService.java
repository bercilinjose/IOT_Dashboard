package com.eoxys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoxys.dto.UpdatedBy;
import com.eoxys.model.AlarmEventList;
import com.eoxys.model.Notification;
import com.eoxys.repository.AlarmEventListRepository;
import com.eoxys.repository.NotificationRepository;

@Service
public class NotificationService {
	
	
	@Autowired
	private NotificationRepository notifyRepo;

	public Long notificationCount() {
		return notifyRepo.count();
	}

	public List<Notification> allNotification() {
		return notifyRepo.findAll();
	}

	public Notification getSingleNotification(Long id) {
		Optional<Notification> notification = notifyRepo.findById(id);
		if (notification.isPresent()) {
			return notification.get();
		}
		throw new RuntimeException("Notification is not present for this id ==>" + id);
	}
	
	public Notification saveNotify(Notification notify) {
		return notifyRepo.save(notify);
	}
	
	
	@Autowired
	private AlarmEventListRepository alRepo;
	
	public Long alarmCount() {
		return notifyRepo.count();
	}

	public List<AlarmEventList> allAlarm() {
		return alRepo.findAll();
	}

	public AlarmEventList getSingleAlarm(Long id) {
		Optional<AlarmEventList> notification = alRepo.findById(id);
		if (notification.isPresent()) {
			return notification.get();
		}
		throw new RuntimeException("Alarm is not present for this id ==>" + id);
	}
	
	
	public List<AlarmEventList> getSingleAlarmByMacId(String id) {
		ArrayList<AlarmEventList> notification = alRepo.findByDeviceId(id);
		return notification;
	}
	
	
	
	public AlarmEventList changeStatus(Long id,UpdatedBy updated_by) {
		Optional<AlarmEventList> optEn = alRepo.findById(id);
		AlarmEventList update = optEn.get();
		update.setView_status(true);
		update.setUpdated_by(updated_by.getUpdated_by());
		update = alRepo.save(update);
		return update;
	}
	
	public AlarmEventList saveAlarm(AlarmEventList alarm) {
		return alRepo.save(alarm);
	}
	

}
