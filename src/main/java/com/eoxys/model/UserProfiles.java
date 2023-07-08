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
@Table(name = "tbl_user_profiles")
public class UserProfiles {

	private Long user_profile_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String user_profile_name;
	private List<Map<String, Object>> custom_data ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUser_profile_id() {
		return user_profile_id;
	}
	public void setUser_profile_id(Long user_profile_id) {
		this.user_profile_id = user_profile_id;
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
	@Column(name = "user_profile_name",columnDefinition = "LONGTEXT")
	public String getUser_profile_name() {
		return user_profile_name;
	}
	public void setUser_profile_name(String user_profile_name) {
		this.user_profile_name = user_profile_name;
	}
	
	@Column(name = "custom_data",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getCustom_data() {
		return custom_data;
	}
	public void setCustom_data(List<Map<String, Object>> custom_data) {
		this.custom_data = custom_data;
	}
	@Override
	public String toString() {
		return "UserProfiles [user_profile_id=" + user_profile_id + ", created_at=" + created_at + ", created_by="
				+ created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by + ", user_profile_name="
				+ user_profile_name + ", custom_data=" + custom_data + "]";
	}
	
	
	
	
	
}
