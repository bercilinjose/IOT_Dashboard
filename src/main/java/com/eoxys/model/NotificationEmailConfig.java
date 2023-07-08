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
@Table(name = "tbl_email_config")
public class NotificationEmailConfig {
	
	private Long email_config_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String content_name;
	private String email_content;
	private String subject;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getEmail_config_id() {
		return email_config_id;
	}
	public void setEmail_config_id(Long email_config_id) {
		this.email_config_id = email_config_id;
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
	
	
	@Column(name = "content_name",columnDefinition = "LONGTEXT")
	public String getContent_name() {
		return content_name;
	}
	public void setContent_name(String content_name) {
		this.content_name = content_name;
	}
	@Column(name = "email_content",columnDefinition = "LONGTEXT")
	public String getEmail_content() {
		return email_content;
	}
	public void setEmail_content(String email_content) {
		this.email_content = email_content;
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
		return "NotificationEmailConfig [email_config_id=" + email_config_id + ", created_at=" + created_at
				+ ", created_by=" + created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by
				+ ", content_name=" + content_name + ", email_content=" + email_content + ", subject=" + subject + "]";
	}

}
