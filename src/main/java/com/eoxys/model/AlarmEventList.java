package com.eoxys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tbl_alarm_event_list")
public class AlarmEventList {
	
	private Long alarm_event_list_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String device_name;
	private String device_mac_id;
	private Date date_time;
	private String alarm_name;
	private String alarm_parameter_name;
	private String alarm_condition;
	private String alarm_value;
	private String notification;
	private Boolean view_status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_event_list_id")
	public Long getAlarm_even_list_id() {
		return alarm_event_list_id;
	}
	public void setAlarm_even_list_id(Long alarm_event_list_id) {
		this.alarm_event_list_id = alarm_event_list_id;
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
	
	@Column(name = "device_name",columnDefinition = "LONGTEXT")
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	@Column(name = "device_mac_id",columnDefinition = "LONGTEXT")
	public String getDevice_mac_id() {
		return device_mac_id;
	}
	public void setDevice_mac_id(String device_mac_id) {
		this.device_mac_id = device_mac_id;
	}
	
	@CreationTimestamp
	@Column(name = "date_time",nullable = false)
	public Date getDate_time() {
		return date_time;
	}
	
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	
	@Column(name = "alarm_name",columnDefinition = "LONGTEXT")
	public String getAlarm_name() {
		return alarm_name;
	}
	public void setAlarm_name(String alarm_name) {
		this.alarm_name = alarm_name;
	}
	
	@Column(name = "alarm_parameter_name",columnDefinition = "LONGTEXT")
	public String getAlarm_parameter_name() {
		return alarm_parameter_name;
	}
	public void setAlarm_parameter_name(String alarm_parameter_name) {
		this.alarm_parameter_name = alarm_parameter_name;
	}
	@Column(name = "alarm_condition",columnDefinition = "LONGTEXT")
	public String getAlarm_condition() {
		return alarm_condition;
	}
	public void setAlarm_condition(String alarm_condition) {
		this.alarm_condition = alarm_condition;
	}
	@Column(name = "alarm_value",columnDefinition = "LONGTEXT") 
	public String getAlarm_value() {
		return alarm_value;
	}
	public void setAlarm_value(String alarm_value) {
		this.alarm_value = alarm_value;
	}
	@Column(name = "notification",columnDefinition = "LONGTEXT")
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	@Column(name = "view_status",columnDefinition = "LONGTEXT")
	public Boolean getView_status() {
		return view_status;
	}
	public void setView_status(Boolean view_status) {
		this.view_status = view_status;
	}
	
	
	@Override
	public String toString() {
		return "AlarmEventList [alarm_even_list_id=" + alarm_event_list_id + ", created_at=" + created_at
				+ ", created_by=" + created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by
				+ ", device_name=" + device_name + ", device_mac_id=" + device_mac_id + ", date_time=" + date_time
				+ ", alarm_name=" + alarm_name + ", alarm_parameter_name=" + alarm_parameter_name + ", alarm_condition="
				+ alarm_condition + ", alarm_value=" + alarm_value + ", notification=" + notification + ", view_status="
				+ view_status + "]";
	}

}
