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
@Table(name = "tbl_device_data_profile")
public class DeviceDataProfile {
	
	private Long device_data_profile_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String data_profile_name;
	private String data_format;
	private List<Map<String, Object>> icon_image;
	private List<Map<String, Object>> custom_data;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getDevice_data_profile_id() {
		return device_data_profile_id;
	}
	public void setDevice_data_profile_id(Long device_data_profile_id) {
		this.device_data_profile_id = device_data_profile_id;
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
	
	@Column(name = "data_profile_name",columnDefinition = "LONGTEXT")
	public String getData_profile_name() {
		return data_profile_name;
	}
	public void setData_profile_name(String data_profile_name) {
		this.data_profile_name = data_profile_name;
	}
	
	@Column(name = "custom_data",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getCustom_data() {
		return custom_data;
	}
	public void setCustom_data(List<Map<String, Object>> custom_data) {
		this.custom_data = custom_data;
	}
	
	@Column(name = "icon_image",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getIcon_image() {
		return icon_image;
	}
	public void setIcon_image(List<Map<String, Object>> icon_image) {
		this.icon_image = icon_image;
	}
	
	@Column(name = "data_format",columnDefinition = "LONGTEXT")
	public String getData_format() {
		return data_format;
	}
	public void setData_format(String data_format) {
		this.data_format = data_format;
	}
	
	
	@Override
	public String toString() {
		return "DeviceDataProfile [device_data_profile_id=" + device_data_profile_id + ", created_at=" + created_at
				+ ", created_by=" + created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by
				+ ", data_profile_name=" + data_profile_name + ", data_format=" + data_format + ", icon_image="
				+ icon_image + ", custom_data=" + custom_data + "]";
	}
	
	
	
	
	
	

}
