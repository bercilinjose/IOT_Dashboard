package com.eoxys.model;

import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "tbl_raw_data")
public class RawData {
	
	private Long raw_data_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private String device_name;
	private ArrayList<String> raw_data = new ArrayList<>();
	private String device_mac_id;
	private String bpid;
	private ArrayList<Map<String, Object>> parsed_data  = new ArrayList<>();
	private ArrayList<String> mismatch_dataset = new ArrayList<>();
	private Boolean isDeleted;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRaw_data_id() {
		return raw_data_id;
	}
	public void setRaw_data_id(Long raw_data_id) {
		this.raw_data_id = raw_data_id;
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
	
	@Column(name = "raw_data",columnDefinition = "LONGBLOB")
	public ArrayList<String> getRaw_data() {
		return raw_data;
	}
	public void setRaw_data(ArrayList<String> raw_data) {
		this.raw_data = raw_data; 
	}
	
	
	
	@Column(name = "device_mac_id",columnDefinition = "LONGTEXT")
	public String getDevice_mac_id() {
		return device_mac_id;
	}
	public void setDevice_mac_id(String device_mac_id) {
		this.device_mac_id = device_mac_id;
	}
	
	@Column(name = "parsed_data",columnDefinition = "LONGBLOB")
	@Convert(converter = ArrayMapConverter.class)
	public ArrayList<Map<String, Object>> getParsed_data() {
		return parsed_data;
	}
	public void setParsed_data(ArrayList<Map<String, Object>> parsed_data) {
		this.parsed_data = parsed_data;
	}
	
	
	@Column(name = "bpid",columnDefinition = "LONGTEXT")
	public String getBpid() {
		return bpid;
	}
	public void setBpid(String bpid) {
		this.bpid = bpid;
	}
	
	@Column(name = "mismatch_dataset",columnDefinition = "LONGBLOB")
	public ArrayList<String> getMismatch_dataset() {
		return mismatch_dataset;
	}
	public void setMismatch_dataset(ArrayList<String> mismatch_dataset) {
		this.mismatch_dataset = mismatch_dataset;
	}
	
	@Column(name = "isDeleted",columnDefinition = "BOOLEAN")
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Column(name = "device_name",columnDefinition = "LONGTEXT")
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	@Override
	public String toString() {
		return "RawData [raw_data_id=" + raw_data_id + ", created_at=" + created_at + ", created_by=" + created_by
				+ ", updated_at=" + updated_at + ", updated_by=" + updated_by + ", device_name=" + device_name
				+ ", raw_data=" + raw_data + ", device_mac_id=" + device_mac_id + ", bpid=" + bpid + ", parsed_data="
				+ parsed_data + ", mismatch_dataset=" + mismatch_dataset + ", isDeleted=" + isDeleted + "]";
	}
	
	
	
	
	
	
	
	
	
	 
	
	

}
