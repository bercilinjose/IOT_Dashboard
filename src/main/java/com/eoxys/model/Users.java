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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_users")
public class Users {
	
	private Long user_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String username;
	private String email;
	private String phone_number;
	private String access_token;
	private String address;
	private String gender;
	private List<Map<String, Object>> additional_feilds;
	private Organization organization;
	private String roles;
	private Boolean isActive;
	private String auth_code;
	private String code_challenge;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
	
	@Column(name = "username",columnDefinition = "LONGTEXT")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "email",columnDefinition = "LONGTEXT")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "phone_number",columnDefinition = "LONGTEXT")
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	@Column(name = "access_token",columnDefinition = "VARCHAR(200)")
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	@Column(name = "address",columnDefinition = "VARCHAR(200)")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "gender",columnDefinition = "LONGTEXT")
	public String getGender() {
		return gender; 
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name = "additional_feilds",columnDefinition = "LONGBLOB")
	@Convert(converter = ArrayMapConverter.class)
	public List<Map<String, Object>> getAdditional_feilds() {
		return additional_feilds;
	}
	public void setAdditional_feilds(List<Map<String, Object>> additional_feilds) {
		this.additional_feilds = additional_feilds;
	}
	
	@JoinColumn(name = "organization")
	@OneToOne
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Column(name = "roles",columnDefinition = "LONGTEXT")
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	@Column(name = "isActive",columnDefinition = "Boolean")
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	@JsonIgnore
	@Column(name = "auth_code",columnDefinition = "LONGTEXT")
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	@JsonIgnore
	@Column(name = "code_challenge",columnDefinition = "LONGTEXT")
	public String getCode_challenge() {
		return code_challenge;
	}
	public void setCode_challenge(String code_challenge) {
		this.code_challenge = code_challenge;
	}
	@Override
	public String toString() {
		return "Users [user_id=" + user_id + ", created_at=" + created_at + ", created_by=" + created_by
				+ ", updated_at=" + updated_at + ", updated_by=" + updated_by + ", username=" + username + ", email="
				+ email + ", phone_number=" + phone_number + ", access_token=" + access_token + ", address=" + address
				+ ", gender=" + gender + ", additional_feilds=" + additional_feilds + ", organization=" + organization
				+ ", roles=" + roles + ", isActive=" + isActive + ", auth_code=" + auth_code + ", code_challenge="
				+ code_challenge + "]";
	}
	
	
	
	
	
	
	

}
