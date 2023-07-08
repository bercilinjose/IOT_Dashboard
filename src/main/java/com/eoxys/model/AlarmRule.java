package com.eoxys.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.eoxys.utils.ArrayMapConverter;

@Entity
@Table(name = "tbl_alarm_rule")
public class AlarmRule {
	
	private Long global_alarm_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String alarm_name;
	private String alarm_notify;
	private String data_profile;
	private List<Map<String, Object>> alarm_rules;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getGlobal_alarm_id() {
		return global_alarm_id;
	}
	public void setGlobal_alarm_id(Long global_alarm_id) {
		this.global_alarm_id = global_alarm_id;
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
	
	@Column(name = "alarm_name",columnDefinition = "LONGTEXT")
	public String getAlarm_name() {
		return alarm_name;
	}
	public void setAlarm_name(String alarm_name) {
		this.alarm_name = alarm_name;
	}
	@Column(name = "alarm_notify",columnDefinition = "LONGTEXT")
	public String getAlarm_notify() {
		return alarm_notify;
	}
	public void setAlarm_notify(String alarm_notify) {
		this.alarm_notify = alarm_notify;
	}
	
	
	
	@Column(name = "alarm_rules",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getAlarm_rules() {
		return alarm_rules;
	}
	public void setAlarm_rules(List<Map<String, Object>> alarm_rules) {
		this.alarm_rules = alarm_rules;
	}
	@Column(name = "data_profile",columnDefinition = "LONGTEXT")
	public String getData_profile() {
		return data_profile;
	}
	public void setData_profile(String data_profile) {
		this.data_profile = data_profile;
	}
	@Override
	public String toString() {
		return "AlarmRule [global_alarm_id=" + global_alarm_id + ", created_at=" + created_at + ", created_by="
				+ created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by + ", alarm_name="
				+ alarm_name + ", alarm_notify=" + alarm_notify + ", data_profile=" + data_profile + ", alarm_rules="
				+ alarm_rules + "]";
	}
	
	

}
