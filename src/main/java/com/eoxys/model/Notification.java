package com.eoxys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tbl_notification")
public class Notification {
	
	private Long notification_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String device_mac_id;
	private String notification_message;
	private String subject;
	private Date date_time;
	private Organization notification_receiver;
	private Users user_id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getNotification_id() {
		return notification_id;
	}
	public void setNotification_id(Long notification_id) {
		this.notification_id = notification_id;
	}
	
	@CreationTimestamp
	@Column(name = "created_at",nullable = false,updatable = false)
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	@Column(name = "created_by",columnDefinition = "LONGTEXT")
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
	@Column(name = "updated_by",columnDefinition = "LONGTEXT")
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	
	@Column(name = "device_mac_id",columnDefinition = "LONGTEXT")
	public String getDevice_mac_id() {
		return device_mac_id;
	}
	public void setDevice_mac_id(String device_mac_id) {
		this.device_mac_id = device_mac_id;
	}
	
	@Column(name = "notification_message",columnDefinition = "LONGTEXT")
	public String getNotification_message() {
		return notification_message;
	}
	public void setNotification_message(String notification_message) {
		this.notification_message = notification_message;
	}
	
	@CreationTimestamp
	@Column(name = "date_time")
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	
	@JoinColumn(name = "notification_receiver")
	@OneToOne
	public Organization getNotification_receiver() {
		return notification_receiver;
	}
	public void setNotification_receiver(Organization notification_receiver) {
		this.notification_receiver = notification_receiver;
	}
	
	@JoinColumn(name = "user_id")
	@OneToOne
	public Users getUser_id() {
		return user_id;
	}
	public void setUser_id(Users user_id) {
		this.user_id = user_id;
	}
	@Column(name = "subject",columnDefinition = "LONGTEXT")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "Notification [notification_id=" + notification_id + ", created_at=" + created_at + ", created_by="
				+ created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by + ", device_mac_id="
				+ device_mac_id + ", notification_message=" + notification_message + ", subject=" + subject
				+ ", date_time=" + date_time + ", notification_receiver=" + notification_receiver + ", user_id="
				+ user_id + "]";
	}
	
	
	

}
