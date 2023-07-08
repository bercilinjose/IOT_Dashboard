package com.eoxys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_platform_credentials")
public class PlatformInfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(name = "name",columnDefinition = "LONGTEXT")
    private String name;
	@Column(name = "email",columnDefinition = "LONGTEXT")
    private String email;
	@Column(name = "password",columnDefinition = "LONGTEXT")
    private String password;
	@Column(name = "roles",columnDefinition = "LONGTEXT")
    private String roles;
	@Column(name = "success_login")
    private int success_login;
	@Column(name = "failed_login")
    private int failed_login;
	@Column(name = "last_login",columnDefinition = "LONGTEXT")
    private String last_login;

}
