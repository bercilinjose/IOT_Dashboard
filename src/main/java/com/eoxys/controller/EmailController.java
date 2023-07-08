package com.eoxys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eoxys.dto.MailRequest;
import com.eoxys.dto.MailResponse;
import com.eoxys.dto.UpdatedBy;
import com.eoxys.model.AlarmEventList;
//import com.eoxys.lib.common.model.APIResponse;
import com.eoxys.model.Notification;
import com.eoxys.service.MailService;
import com.eoxys.service.NotificationService;
import com.eoxys.service.ServiceEmail;
import com.eoxys.service.UserService;

import freemarker.template.TemplateException;

@RestController
public class EmailController {

	
	@Autowired
	private NotificationService notificationService;


	

	@GetMapping("/notifications")
	public ResponseEntity<List<Notification>> getNotification() {
		return new ResponseEntity<List<Notification>>(notificationService.allNotification(), HttpStatus.OK);
	}
	
	@GetMapping("/notification/{id}")
	public ResponseEntity<Notification> getSingleNotification(@PathVariable Long id ) {
		return new ResponseEntity<Notification>(notificationService.getSingleNotification(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/alarm_count")
	public ResponseEntity<Long> deviceCount(){
		System.out.println("device count");
		return new ResponseEntity<Long>(notificationService.alarmCount(),HttpStatus.OK);
	}
	
	@GetMapping("/alarm_event_lists")
	public ResponseEntity<List<AlarmEventList>> getAlarmEventLists() {
		return new ResponseEntity<List<AlarmEventList>>(notificationService.allAlarm(), HttpStatus.OK);
	}
	
	@GetMapping("/alarm_event_list/{id}")
	public ResponseEntity<AlarmEventList> getSingleAlarm(@PathVariable Long id ) {
		return new ResponseEntity<AlarmEventList>(notificationService.getSingleAlarm(id), HttpStatus.OK);
	}
	
	@GetMapping("/alarm_device_id/{id}")
	public ResponseEntity<List<AlarmEventList>> getSingleByMacId(@PathVariable String id ) {
		return new ResponseEntity<List<AlarmEventList>>(notificationService.getSingleAlarmByMacId(id), HttpStatus.OK);
	}
	
	
	@PatchMapping("/alarm_seen/{id}")
	public ResponseEntity<AlarmEventList> changeStatus(@PathVariable Long id ,@RequestBody UpdatedBy updated_by) {
		return new ResponseEntity<AlarmEventList>(notificationService.changeStatus(id,updated_by), HttpStatus.OK);
	}
	


	@Autowired
	private ServiceEmail service;

	@PostMapping("/sendingEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		Map<String, Object> model = new HashMap<>();
		model.put("name", request.getName());
		return service.sendEmail(request, model);
	}

}
