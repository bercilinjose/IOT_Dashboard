package com.eoxys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eoxys.controller.GlobalConfigController;
import com.eoxys.model.DeletedOrganization;
import com.eoxys.model.Device;
import com.eoxys.model.Notification;
import com.eoxys.model.Organization;
import com.eoxys.model.Users;
import com.eoxys.repository.DeletedOrganizationRepository;
import com.eoxys.repository.DeviceRepository;
import com.eoxys.repository.NotificationRepository;
import com.eoxys.repository.OrganizationRepository;
import com.eoxys.repository.UsersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UsersRepository usersRepository;
	
	public Long userCount() {
		return usersRepository.count();
	}
	
	
	public List<Users> allUsers(){
		return usersRepository.findAll();
	}
	
	
	public Users getSingleUser(Long id) {
		Optional<Users> users = usersRepository.findById(id);
		if(users.isPresent()) {
			return users.get();
			
		}
		
		throw new RuntimeException("User is not present for this id ==>"+id);
	}
	
	
	
	public Users updateUserFeilds(Long id, Users users) {
		Optional<Users> optEn = usersRepository.findById(id);
		Users update = optEn.get();
		update.setUpdated_by(users.getUpdated_by());
		update.setGender(users.getGender());
		update.setAddress(users.getAddress());
		update.setAdditional_feilds(users.getAdditional_feilds());
		update = usersRepository.save(update);
		return update;
	}
	
	public Users userOrganizationAndRoles(Long id, Users users) {
		Optional<Users> optEn = usersRepository.findById(id);
		Users update = optEn.get();
		update.setUpdated_by(users.getUpdated_by());
		update.setOrganization(users.getOrganization());
		update.setRoles(users.getRoles());
		update = usersRepository.save(update);
		
		List<Map<String, Object>> primary_users;
		List<Map<String, Object>> general_users;
		
		Long org_id = users.getOrganization().getOrganization_id();
		
		Optional<Organization> optOrganization = organizationRepository.findById(org_id);
		if(optOrganization.isPresent()) {
			ObjectMapper mapper = new ObjectMapper();
			Organization organization = optOrganization.get();
			if(users.getRoles().equals("PRIMARY")) {
				Boolean isPresent = false;
				primary_users = organization.getPrimary_users();
				for(int i = 0;i<=primary_users.size()-1;i++) {
					if(primary_users.get(i).get("user_id").toString().equals(id.toString())) {
						isPresent = true;
						break;
					}
				}
				if(!isPresent) {
					Map<String, Object> map = 
						    mapper.convertValue(update, new TypeReference<Map<String, Object>>() {});
					
					primary_users.add(map);
					organization.setPrimary_users(primary_users);
					organizationRepository.save(organization);
				}else {
					logger.info("User present for this organization");
				}
				
				
			}else if(users.getRoles().equals("GENERAL")) {
				
				
				Boolean isPresent = false;
				general_users = organization.getGeneral_users();
				for(int i = 0;i<=general_users.size()-1;i++) {
					if(general_users.get(i).get("user_id").toString().equals(id.toString())) {
						isPresent = true;
						break;
					}
				}
				if(!isPresent) {
					Map<String, Object> map = 
						    mapper.convertValue(update, new TypeReference<Map<String, Object>>() {});
					
					general_users.add(map);
					organization.setPrimary_users(general_users);
					organizationRepository.save(organization);
				}else {
					logger.info("User present for this organization");
				}
			}
			
		}
		
		
		return update;
	}
	
	public Users userActivity(Long id, Users users) {
		Optional<Users> optEn = usersRepository.findById(id);
		Users update = optEn.get();
		update.setIsActive(users.getIsActive());
		update = usersRepository.save(update);
		return update;
	}
	
	
	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
	
	
	public Users addUser(Users users) {
		users.setIsActive(true);
		return usersRepository.save(users);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private DeletedOrganizationRepository delOrgrepository;
	
	
	
	public Long organizationCount() {
		return organizationRepository.count();
	}
	
	
	public List<Organization> allOrganization(){
		return organizationRepository.findAll();
	}
	
	
	public Organization getSingleOrganization(Long id) {
		Optional<Organization> organization = organizationRepository.findById(id);
		if(organization.isPresent()) {
			return organization.get();
		}
		throw new RuntimeException("Organization is not present for this id ==>"+id);
	}
	
	
	
	public Organization saveOrganization(Organization org) {
		
		Optional<Organization> optOrg = organizationRepository.findByOrgName(org.getOrganization_name());
		if(optOrg.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "organization already exists !");
		}else {
			org.setIsDeleted(false);
			return organizationRepository.save(org);
		}
		
		
	}
	
	
	public Organization updateOrganization(Long id, Organization organization) {
		Optional<Organization> optEn = organizationRepository.findById(id);
		Organization update = optEn.get();
		
		Optional<Organization> optOrgname = organizationRepository.findByOrgName(organization.getOrganization_name());
		Organization orgName = null;
		if(optOrgname.isPresent()) {
			orgName = optOrgname.get();
			if(id.equals(orgName.getOrganization_id())) {
				
				update.setUpdated_by(organization.getUpdated_by());
				update.setAddress(organization.getAddress());
				update.setOrganization_logo(organization.getOrganization_logo());
				update.setOrganization_name(organization.getOrganization_name());
				update.setContact_person(organization.getContact_person());
				update.setNotification_profile(organization.getNotification_profile());
				update.setAdditional_contacts(organization.getAdditional_contacts());
				update.setGeneral_users(organization.getGeneral_users());
				update.setPrimary_users(organization.getPrimary_users());
				update.setSelected_email(organization.getSelected_email());
				update.setSelected_sms(organization.getSelected_sms());
				update = organizationRepository.save(update);
				return update;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "organization name matches with other organization name !");
			}
		}else {
			update.setUpdated_by(organization.getUpdated_by());
			update.setAddress(organization.getAddress());
			update.setOrganization_logo(organization.getOrganization_logo());
			update.setOrganization_name(organization.getOrganization_name());
			update.setContact_person(organization.getContact_person());
			update.setNotification_profile(organization.getNotification_profile());
			update.setAdditional_contacts(organization.getAdditional_contacts());
			update.setGeneral_users(organization.getGeneral_users());
			update.setPrimary_users(organization.getPrimary_users());
			update.setSelected_email(organization.getSelected_email());
			update.setSelected_sms(organization.getSelected_sms());
			update = organizationRepository.save(update);
			return update;
		}
		
		
	}
	
	public Organization updateEmailContent(Long id, Organization organization) {
		Optional<Organization> optEn = organizationRepository.findById(id);
		Organization update = optEn.get();
		update.setSelected_email(organization.getSelected_email());
		update = organizationRepository.save(update);
		return update;
	}
	
	public Organization updateSmsContent(Long id, Organization organization) {
		Optional<Organization> optEn = organizationRepository.findById(id);
		Organization update = optEn.get();
		update.setSelected_sms(organization.getSelected_sms());
		update = organizationRepository.save(update);
		return update;
	}
	
	public Organization emailConfig(Long id, Organization organization) {
		Optional<Organization> optEn = organizationRepository.findById(id);
		Organization update = optEn.get();
		update.setEmail_configuration(organization.getEmail_configuration());
		update = organizationRepository.save(update);
		return update;
	}
	
	public Organization updateUserRoles(Long id, Organization organization) {
		Optional<Organization> optEn = organizationRepository.findById(id);
		Organization update = optEn.get();
		update.setPrimary_users(organization.getPrimary_users());
		update.setGeneral_users(organization.getGeneral_users());
		update = organizationRepository.save(update);
		return update;
	}
	
	
	public Organization deleteOrganization(Long id) {
		
		
		
		
		ArrayList<Users> delUser = usersRepository.findByUserOrg(id);
		if(delUser.size() > 0) {
			delUser.forEach(del -> del.setOrganization(null));
			delUser.forEach(del -> usersRepository.save(del));
			System.out.println("organization matched users ==> "+delUser);
			logger.info("organization matched users ==> "+delUser);
		}
		
		
		ArrayList<Notification> delNot = notificationRepository.findByNotifyOrg(id);
		if(delNot.size() > 0) {
			delNot.forEach(del -> del.setNotification_receiver(null));
			delNot.forEach(del -> notificationRepository.save(del));
			System.out.println("organization matched notification ==> "+delNot);
			logger.info("organization matched notification ==> "+delNot);
		}
		
		
		Optional<Organization> optEn = organizationRepository.findById(id);
		Organization org = optEn.get();
		org.setIsDeleted(true);
		org.setPrimary_users(null);
		org.setGeneral_users(null);
		ObjectMapper mapper = new ObjectMapper();
		DeletedOrganization deleted_organization = mapper.convertValue(org, new TypeReference<DeletedOrganization>() {});
		
		delOrgrepository.save(deleted_organization);
		
		organizationRepository.deleteById(id);
		
		return org;
		
		
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
    
	
	
	
}
