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
import com.eoxys.utils.ObjectMapConverter;

@Entity
@Table(name = "tbl_device_category")
public class DeviceCategory {
	
	private Long device_category_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String category_name;
	private String device_image;
	private List<Map<String, Object>> device_profile;
	private List<Map<String, Object>> device_data_profile;
	private List<Map<String, Object>> global_alarm_rule;
	private Map<String, Object> layout;
	private Boolean isDeleted;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getDevice_category_id() {
		return device_category_id;
	}
	public void setDevice_category_id(Long device_category_id) {
		this.device_category_id = device_category_id;
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
	
	@Column(name = "category_name",columnDefinition = "LONGTEXT")
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	@Column(name = "device_image",columnDefinition = "LONGBLOB")
	public String getDevice_image() {
		return device_image;
	}
	public void setDevice_image(String device_image) {
		this.device_image = device_image;
	}
	@Column(name = "device_profile",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getDevice_profile() {
		return device_profile;
	}
	public void setDevice_profile(List<Map<String, Object>> device_profile) {
		this.device_profile = device_profile;
	}
	@Column(name = "device_data_profile",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getDevice_data_profile() {
		return device_data_profile;
	}
	public void setDevice_data_profile(List<Map<String, Object>> device_data_profile) {
		this.device_data_profile = device_data_profile;
	}
	@Column(name = "global_alarm_rule",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getGlobal_alarm_rule() {
		return global_alarm_rule;
	}
	public void setGlobal_alarm_rule(List<Map<String, Object>> global_alarm_rule) {
		this.global_alarm_rule = global_alarm_rule;
	}
	@Column(name = "isDeleted",columnDefinition = "BOOLEAN")
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	} 
	@Column(name = "layout",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	public Map<String, Object> getLayout() {
		return layout;
	}
	public void setLayout(Map<String, Object> layout) {
		this.layout = layout;
	}
	@Override
	public String toString() {
		return "DeviceCategory [device_category_id=" + device_category_id + ", created_at=" + created_at
				+ ", created_by=" + created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by
				+ ", category_name=" + category_name + ", device_image=" + device_image + ", device_profile="
				+ device_profile + ", device_data_profile=" + device_data_profile + ", global_alarm_rule="
				+ global_alarm_rule + ", layout=" + layout + ", isDeleted=" + isDeleted + "]";
	}
	
	
	

}
