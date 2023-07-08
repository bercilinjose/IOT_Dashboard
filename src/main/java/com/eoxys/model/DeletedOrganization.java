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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_deleted_organization")

public class DeletedOrganization {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	Long organization_id;
	
	@CreationTimestamp
	@Column(name = "created_at",nullable = false,updatable = false)
	private Date created_at;
	
	@Column(name = "created_by",columnDefinition = "LONGTEXT")
	private String created_by;
	
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updated_at;
	
	@Column(name = "updated_by",columnDefinition = "LONGTEXT")
	private String updated_by;
	//length = 100000000,
	@Column(name = "organization_logo",columnDefinition = "LONGTEXT")
	private String organization_logo;
	
	@Column(name = "organization_name",columnDefinition = "LONGTEXT")
	private String organization_name;
	
	@Column(name = "address",columnDefinition = "LONGTEXT")
	private String address;

	@Column(name = "contact_person",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	private Map<String, Object> contact_person;
	
	@Column(name = "notification_profile",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	private Map<String, Object> notification_profile;
	
	@Column(name = "additional_contacts",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> additional_contacts;
	
	@Column(name = "organization_profile",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> organization_profile;
	
	@Column(name = "user_profile",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> user_profile;
	
	@Column(name = "email_notification",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> email_notifications;
	
	@Column(name = "sms_notification",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> sms_notifications;
	
	@Column(name = "selected_email",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	private Map<String, Object> selected_email;
	
	@Column(name = "selected_sms",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	private Map<String, Object> selected_sms;
	
	@Column(name = "primary_users",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> primary_users;
	
	@Column(name = "general_users",columnDefinition = "LONGTEXT")
	@Convert(converter = ArrayMapConverter.class)
	private List<Map<String, Object>> general_users;
	
	
	@Column(name = "isDeleted",columnDefinition = "BOOLEAN")
	private Boolean isDeleted;
	
	
	@Column(name = "email_configuration",columnDefinition = "LONGTEXT")
	@Convert(converter = ObjectMapConverter.class)
	private Map<String, Object> email_configuration;
	
	

}

