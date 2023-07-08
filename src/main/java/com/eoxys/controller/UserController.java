package com.eoxys.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eoxys.model.Organization;
import com.eoxys.model.Users;
import com.eoxys.service.UserService;

@RestController
//@PreAuthorize("hasAnyAuthority('ADMIN_GROUP','GENERAL_GROUP')")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/organization")
	public ResponseEntity<List<Organization>> getOrgaization() {
		logger.info("GetOrgaization");
		System.out.println("GetOrgaization");
		return new ResponseEntity<List<Organization>>(userService.allOrganization(), HttpStatus.OK);
	}

	@GetMapping("/organization_count")
	public ResponseEntity<Long> orgCount() {
		logger.info("OrgCount");
		System.out.println("OrgCount");
		return new ResponseEntity<Long>(userService.organizationCount(), HttpStatus.OK);
	}

	@GetMapping("/organization/{id}")
	public ResponseEntity<Organization> getOrgById(@PathVariable Long id) {
		logger.info("GetOrgById ==>" + id);
		System.out.println("GetOrgById ==>" + id);
		return new ResponseEntity<Organization>(userService.getSingleOrganization(id), HttpStatus.OK);
	}

	@PostMapping("/organization")
//	@PreAuthorize("hasAnyAuthority('ADMIN_GROUP')")
	public ResponseEntity<Organization> saveOrg(@RequestBody Organization org) {
		logger.info("SaveOrg data ==>" + org);
		System.out.println("SaveOrg data ==>" + org);
		return new ResponseEntity<Organization>(userService.saveOrganization(org), HttpStatus.CREATED);
	}

	@PatchMapping("/edit_organization/{id}")
	public ResponseEntity<Organization> updateOrg(@PathVariable Long id, @RequestBody Organization org) {
		logger.info("updateOrg data ==>" + org);
		System.out.println("updateOrg data ==>" + org);
		return new ResponseEntity<Organization>(userService.updateOrganization(id, org), HttpStatus.OK);
	}

	@PatchMapping("/add_email_content/{id}")
	public ResponseEntity<Organization> addEmailContent(@PathVariable Long id, @RequestBody Organization org) {
		logger.info("addEmailContent ==> " + org);
		System.out.println("addEmailContent ==> " + org);
		return new ResponseEntity<Organization>(userService.updateEmailContent(id, org), HttpStatus.OK);
	}

	@PatchMapping("/add_sms_content/{id}")
	public ResponseEntity<Organization> addSmsContent(@PathVariable Long id, @RequestBody Organization org) {
		logger.info("addSmsContent ==> " + org);
		System.out.println("addSmsContent ==> " + org);
		return new ResponseEntity<Organization>(userService.updateSmsContent(id, org), HttpStatus.OK);
	}
	
	@PatchMapping("/email_configuration/{id}")
	public ResponseEntity<Organization> emailConfig(@PathVariable Long id, @RequestBody Organization org) {
		logger.info("add email config ==> " + org);
		System.out.println("add email congif ==> " + org);
		return new ResponseEntity<Organization>(userService.emailConfig(id, org), HttpStatus.OK);
	}
	
	@PatchMapping("/update_user_roles/{id}")
	public ResponseEntity<Organization> updateUserRoles(@PathVariable Long id, @RequestBody Organization org) {
		logger.info("add user roles ==> " + org);
		System.out.println("add user roles ==> " + org);
		return new ResponseEntity<Organization>(userService.updateUserRoles(id, org), HttpStatus.OK);
	}

	@DeleteMapping("/delete_organization/{id}")
	public ResponseEntity<Organization> deleteOrganization(@PathVariable Long id) {
		logger.info("Delete organization ==> " + id);
		System.out.println("Delete organization ==> " + id);
		
		return new ResponseEntity<Organization>(userService.deleteOrganization(id), HttpStatus.OK);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	

	@GetMapping("/user")
	public ResponseEntity<List<Users>> getUser() {
		logger.info("GetUser");
		System.out.println("GetUser");
		return new ResponseEntity<List<Users>>(userService.allUsers(), HttpStatus.OK);
	}

	@GetMapping("/user_count")
	public ResponseEntity<Long> userCount() {
		logger.info("UserCount");
		System.out.println("UserCount");
		return new ResponseEntity<Long>(userService.userCount(), HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable Long id) {
		logger.info("GetOrgaization");
		System.out.println("GetUserById ==>" + id);
		return new ResponseEntity<Users>(userService.getSingleUser(id), HttpStatus.OK);
	}


	@PatchMapping("/edit_user_feilds/{id}")
	public ResponseEntity<Users> updateFeilds(@PathVariable Long id, @RequestBody Users user) {
		logger.info("updateFeilds ==>" + user);
		System.out.println("updateFeilds ==>" + user);
		return new ResponseEntity<Users>(userService.updateUserFeilds(id, user), HttpStatus.OK);
	}

	@PatchMapping("/edit_user_organization_role/{id}")
	public ResponseEntity<Users> updateOrganizationAndRole(@PathVariable Long id, @RequestBody Users user) {
		logger.info("updateOrganizationAndRole ==>" + user);
		System.out.println("updateOrganizationAndRole ==>" + user);
		return new ResponseEntity<Users>(userService.userOrganizationAndRoles(id, user), HttpStatus.OK);
	}

	@PatchMapping("/edit_user_activity/{id}")
	public ResponseEntity<Users> updateActivity(@PathVariable Long id, @RequestBody Users user) {
		logger.info("updateActivity ==>" + user);
		System.out.println("updateActivity ==>" + user);
		return new ResponseEntity<Users>(userService.userActivity(id, user), HttpStatus.OK);
	}

	@DeleteMapping("/delete_user/{id}")
	public void deleteUser(@PathVariable Long id) {
		logger.info("deleteUser ==>" + id);
		System.out.println("deleteUser ==>" + id);
		userService.deleteUser(id);
	}

	

}
