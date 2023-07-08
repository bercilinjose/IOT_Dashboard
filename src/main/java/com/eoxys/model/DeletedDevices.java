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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.eoxys.utils.ArrayMapConverter;
import com.eoxys.utils.ObjectMapConverter;

@Entity
@Table(name = "tbl_deleted_device")
public class DeletedDevices {
	
	private Long device_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String device_mac_id;
	private String device_name;
	private List<Map<String, Object>> device_category;
	private List<Map<String, Object>> device_profile;
	private Users user_id;
	private String protocol_type;
	private Boolean isPlatform;
	private String platform;
	private Map<String, Object> notification;
	private String bpid;
	private Boolean isDeleted;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getDevice_id() {
		return device_id;
	}
	public void setDevice_id(Long device_id) {
		this.device_id = device_id;
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
	
	@Column(name = "device_name",columnDefinition = "LONGTEXT")
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	@Column(name = "device_category",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getDevice_category() {
		return device_category;
	}
	public void setDevice_category(List<Map<String, Object>> device_category) {
		this.device_category = device_category;
	}
	
	@JoinColumn(name = "user_id")
	@OneToOne
	public Users getUser_id() {
		return user_id;
	}
	public void setUser_id(Users user_id) {
		this.user_id = user_id;
	}
	
	
	@Column(name = "platform",columnDefinition = "LONGTEXT")
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	@Column(name = "protocol_type",columnDefinition = "LONGTEXT")
	public String getProtocol_type() {
		return protocol_type;
	}
	public void setProtocol_type(String protocol_type) {
		this.protocol_type = protocol_type;
	}
	
	@Column(name = "isPlatform", columnDefinition = "boolean default false")
	public Boolean getIsPlatform() {
		return isPlatform;
	}
	public void setIsPlatform(Boolean isPlatform) {
		this.isPlatform = isPlatform;
	}
	
	@Column(name = "notification",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	public Map<String, Object> getNotification() {
		return notification;
	}
	public void setNotification(Map<String, Object> notification) {
		this.notification = notification;
	}
	@Column(name = "bpid",columnDefinition = "LONGTEXT")
	public String getBpid() {
		return bpid;
	}
	public void setBpid(String bpid) {
		this.bpid = bpid;
	}
	@Column(name = "isDeleted",columnDefinition = "BOOLEAN")
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Column(name = "device_profile",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getDevice_profile() {
		return device_profile;
	}
	public void setDevice_profile(List<Map<String, Object>> device_profile) {
		this.device_profile = device_profile;
	}
	@Override
	public String toString() {
		return "Device [device_id=" + device_id + ", created_at=" + created_at + ", created_by=" + created_by
				+ ", updated_at=" + updated_at + ", updated_by=" + updated_by + ", device_mac_id=" + device_mac_id
				+ ", device_name=" + device_name + ", device_category=" + device_category + ", device_profile="
				+ device_profile + ", user_id=" + user_id + ", protocol_type=" + protocol_type + ", isPlatform="
				+ isPlatform + ", platform=" + platform + ", notification=" + notification + ", bpid=" + bpid
				+ ", isDeleted=" + isDeleted + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}

