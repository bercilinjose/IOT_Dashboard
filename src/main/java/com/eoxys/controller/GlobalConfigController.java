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

import com.eoxys.model.AlarmRule;
import com.eoxys.model.DeviceCategory;
import com.eoxys.model.DeviceDataProfile;
import com.eoxys.model.DeviceProfile;
import com.eoxys.model.NotificationEmailConfig;
import com.eoxys.model.NotificationSmsConfig;
import com.eoxys.model.OrganizationProfile;
import com.eoxys.model.UserProfiles;
import com.eoxys.service.GlobalConfigService;

@RestController
//@PreAuthorize("hasAuthority('ADMIN_GROUP')")
public class GlobalConfigController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalConfigController.class);

	@Autowired
	private GlobalConfigService globalConfigService;

	// DEVICE-CATEGORY

	@GetMapping("/device_category")
	public ResponseEntity<List<DeviceCategory>> getDeviceCategory() {
		System.out.println("GetDeviceCategory");
		logger.info("GetDeviceCategory");
		return new ResponseEntity<List<DeviceCategory>>(globalConfigService.allDeviceCategory(), HttpStatus.OK);
	}

	@GetMapping("/device_category_count")
	public ResponseEntity<Long> deviceCategorycount() {
		System.out.println("DeviceCategorycount");
		logger.info("DeviceCategorycount");
		return new ResponseEntity<Long>(globalConfigService.deviceCategoryCount(), HttpStatus.OK);
	}

	@GetMapping("/device_category/{id}")
	public ResponseEntity<DeviceCategory> getDeviceCategoryById(@PathVariable Long id) {
		System.out.println("GetDeviceCategoryById ==>" + id);
		logger.info("GetDeviceCategoryById ==>" + id);
		return new ResponseEntity<DeviceCategory>(globalConfigService.getSingleDeviceCategory(id), HttpStatus.OK);
	}

	@PostMapping("/device_category")
	public ResponseEntity<DeviceCategory> saveDeviceCategory(@RequestBody DeviceCategory device_category) {
		System.out.println("SaveDeviceCategory ==>" + device_category);
		logger.info("SaveDeviceCategory ==>" + device_category);
		return new ResponseEntity<DeviceCategory>(globalConfigService.saveDeviceCategory(device_category),
				HttpStatus.CREATED);
	}

	@PatchMapping("/edit_device_category/{id}")
	public ResponseEntity<DeviceCategory> editdevice(@PathVariable Long id,
			@RequestBody DeviceCategory devicecategory) {
		System.out.println("editDevicecategory ==>" + devicecategory);
		logger.info("editDevicecategory ==>" + devicecategory);
		return new ResponseEntity<DeviceCategory>(globalConfigService.editDeviceCategory(id, devicecategory),
				HttpStatus.OK);
	}

	@DeleteMapping("/delete_device_category/{id}")
	public void deletedeviceCategory(@PathVariable Long id) {
		System.out.println("Delete device category id ==> " + id);
		logger.info("Delete device category id ==> " + id);
		globalConfigService.deleteDeviceCategory(id);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// DEVICE-DATA-PROFILE

	@GetMapping("/device_data_profile")
	public ResponseEntity<List<DeviceDataProfile>> getDeviceDataProfile() {
		System.out.println("GetDeviceDataProfile");
		logger.info("GetDeviceDataProfile");
		return new ResponseEntity<List<DeviceDataProfile>>(globalConfigService.allDeviceDataProfile(), HttpStatus.OK);
	}

	@GetMapping("/device_data_profile_count")
	public ResponseEntity<Long> deviceDataProfileCount() {
		System.out.println("DeviceDataProfilecount");
		logger.info("DeviceDataProfilecount");
		return new ResponseEntity<Long>(globalConfigService.deviceDataProfilecount(), HttpStatus.OK);
	}

	@GetMapping("/device_data_profile/{id}")
	public ResponseEntity<DeviceDataProfile> getDeviceDataProfileById(@PathVariable Long id) {
		System.out.println("GetDeviceDataProfileById ==>" + id);
		logger.info("GetDeviceDataProfileById ==>" + id);
		return new ResponseEntity<DeviceDataProfile>(globalConfigService.getSingleDeviceDataProfile(id), HttpStatus.OK);
	}

	@PostMapping("/device_data_profile")
	public ResponseEntity<DeviceDataProfile> saveDeviceDataProfile(@RequestBody DeviceDataProfile device_data_profile) {
		System.out.println("SaveDeviceDataProfile ==>" + device_data_profile);
		logger.info("SaveDeviceDataProfile ==>" + device_data_profile);
		return new ResponseEntity<DeviceDataProfile>(globalConfigService.saveDeviceDataProfile(device_data_profile),
				HttpStatus.CREATED);
	}

	@PatchMapping("/edit_device_data_profile/{id}")
	public ResponseEntity<DeviceDataProfile> editDeviceDataProfile(@PathVariable Long id,
			@RequestBody DeviceDataProfile devicedataprofile) {
		System.out.println("editDevicedataprofile==>" + devicedataprofile);
		logger.info("editDevicedataprofile==>" + devicedataprofile);
		return new ResponseEntity<DeviceDataProfile>(globalConfigService.editDeviceDataProfile(id, devicedataprofile),
				HttpStatus.OK);
	}

	@DeleteMapping("/delete_device_data_profile/{id}")
	public void deletedevicedataProfile(@PathVariable Long id) {
		System.out.println("Delete device device data profile ==> "+id);
		logger.info("Delete device device data profile ==> "+id);
		globalConfigService.deleteDeviceDataProfile(id);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// DEVICE-PROFILE

	@GetMapping("/device_profile")
	public ResponseEntity<List<DeviceProfile>> getDeviceProfile() {
		System.out.println("getDeviceProfile");
		logger.info("getDeviceProfile");
		return new ResponseEntity<List<DeviceProfile>>(globalConfigService.allDeviceProfile(), HttpStatus.OK);
	}

	@GetMapping("/device_profile_count")
	public ResponseEntity<Long> deviceProfileCount() {
		System.out.println("deviceProfileCount");
		logger.info("deviceProfileCount");
		return new ResponseEntity<Long>(globalConfigService.deviceProfileCount(), HttpStatus.OK);
	}

	@GetMapping("/device_profile/{id}")
	public ResponseEntity<DeviceProfile> getDeviceProfileById(@PathVariable Long id) {
		System.out.println("getDeviceProfileById ==>" + id);
		logger.info("getDeviceProfileById ==>" + id);
		return new ResponseEntity<DeviceProfile>(globalConfigService.getSingleDeviceProfile(id), HttpStatus.OK);
	}

	@PostMapping("/device_profile")
	public ResponseEntity<DeviceProfile> saveDeviceProfile(@RequestBody DeviceProfile device_profile) {
		System.out.println("SaveDeviceProfile ==>" + device_profile);
		logger.info("SaveDeviceProfile ==>" + device_profile);
		return new ResponseEntity<DeviceProfile>(globalConfigService.saveDeviceProfile(device_profile),
				HttpStatus.CREATED);
	}

	@PatchMapping("/edit_device_profile/{id}")
	public ResponseEntity<DeviceProfile> editDeviceProfile(@PathVariable Long id,
			@RequestBody DeviceProfile deviceprofile) {
		System.out.println("editDeviceProfile ==>" + deviceprofile);
		logger.info("editDeviceProfile ==>" + deviceprofile);
		return new ResponseEntity<DeviceProfile>(globalConfigService.editDeviceProfile(id, deviceprofile),
				HttpStatus.OK);
	}

	@DeleteMapping("/delete_device_profile/{id}")
	public void deletedeviceProfile(@PathVariable Long id) {
		System.out.println("Delete device device profile ==> "+id);
		logger.info("Delete device device profile ==> "+id);
		globalConfigService.deleteDeviceProfile(id);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// ALARM-RULE

	@GetMapping("/alarm_rule")
	public ResponseEntity<List<AlarmRule>> getAlarmRule() {
		System.out.println("GetAlarmRule");
		logger.info("GetAlarmRule");
		return new ResponseEntity<List<AlarmRule>>(globalConfigService.allAlarmRule(), HttpStatus.OK);
	}

	@GetMapping("/alarm_rule_count")
	public ResponseEntity<Long> alarmRuleCount() {
		System.out.println("AlarmRulecount");
		logger.info("AlarmRulecount");
		return new ResponseEntity<Long>(globalConfigService.alarmRulecount(), HttpStatus.OK);
	}

	@GetMapping("/alarm_rule/{id}")
	public ResponseEntity<AlarmRule> getAlarmRuleById(@PathVariable Long id) {
		System.out.println("GetAlarmRuleById ==>" + id);
		logger.info("GetAlarmRuleById ==>" + id);
		return new ResponseEntity<AlarmRule>(globalConfigService.getSingleAlarmRule(id), HttpStatus.OK);
	}
	
	@GetMapping("/data_profile_alarm/{data_profile}")
	public ResponseEntity<List<AlarmRule>> getAlarmRuleById(@PathVariable String data_profile) {
		System.out.println("Get data profile alarms ==>" + data_profile);
		logger.info("Get data profile alarms ==>" + data_profile);
		return new ResponseEntity<List<AlarmRule>>(globalConfigService.getByDataProfile(data_profile), HttpStatus.OK);
	}

	@PostMapping("/alarm_rule")
	public ResponseEntity<AlarmRule> saveAlarmRule(@RequestBody AlarmRule alarm_rule) {
		System.out.println("SaveAlarmRule ==>" + alarm_rule);
		logger.info("SaveAlarmRule ==>" + alarm_rule);
		return new ResponseEntity<AlarmRule>(globalConfigService.saveAlarmRule(alarm_rule), HttpStatus.CREATED);
	}

	@PatchMapping("/edit_alarm_rule/{id}")
	public ResponseEntity<AlarmRule> editAlarmRule(@PathVariable Long id, @RequestBody AlarmRule alarmrule) {
		System.out.println("editalarm rules ==>" + alarmrule);
		logger.info("editalarm rules ==>" + alarmrule);
		return new ResponseEntity<AlarmRule>(globalConfigService.editAlarmRule(id, alarmrule), HttpStatus.OK);
	}

	@DeleteMapping("/delete_alarm_rule/{id}")
	public void deleteAlarmRules(@PathVariable Long id) {
		System.out.println("Delete alarm rule ==> "+id);
		logger.info("Delete device alarm rule ==> "+id);
		globalConfigService.deleteAlarmrule(id);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// ORGANIZATION-PROFILE

//	@CrossOrigin(origins = "http://192.162.0.138:4700")
	@GetMapping("/organization_profile")
	public ResponseEntity<List<OrganizationProfile>> getOrganizationProfile() {
		System.out.println("GetOrganizationProfile");
		logger.info("GetOrganizationProfile");
		return new ResponseEntity<List<OrganizationProfile>>(globalConfigService.allOrganizationProfile(),
				HttpStatus.OK);
	}

	@GetMapping("/organization_profile_count")
	public ResponseEntity<Long> organizationProfileCount() {
		System.out.println("OrganizationProfilecount");
		logger.info("OrganizationProfilecount");
		return new ResponseEntity<Long>(globalConfigService.organizationProfileCount(), HttpStatus.OK);
	}

	@GetMapping("/organization_profile/{id}")
	public ResponseEntity<OrganizationProfile> getOrganizationProfileById(@PathVariable Long id) {
		System.out.println("GetOrganizationProfileeById ==>" + id);
		logger.info("GetOrganizationProfileeById ==>" + id);
		return new ResponseEntity<OrganizationProfile>(globalConfigService.getSingleOrganizationProfile(id),
				HttpStatus.OK);
	}

	@PostMapping("/organization_profile")
	public ResponseEntity<OrganizationProfile> saveOrganizationProfile(
			@RequestBody OrganizationProfile organization_profile) {
		System.out.println("SaveOrganizationProfile ==>" + organization_profile);
		logger.info("SaveOrganizationProfile ==>" + organization_profile);
		return new ResponseEntity<OrganizationProfile>(
				globalConfigService.saveOrganizationProfile(organization_profile), HttpStatus.CREATED);
	}

	@PatchMapping("/organization_profile_edit/{id}")
	public ResponseEntity<OrganizationProfile> organizationUpdate(@PathVariable Long id,
			@RequestBody OrganizationProfile organization_profile) {
		System.out.println("Edit organization profile ==> "+organization_profile);
		logger.info("Edit organization profile ==> "+organization_profile);
		return new ResponseEntity<OrganizationProfile>(
				globalConfigService.editOrganizationProfile(id, organization_profile), HttpStatus.OK);
	}

	@DeleteMapping("/organization_profile_delete/{id}")
	public void deleteOrganizationProfile(@PathVariable Long id) {
		System.out.println("Delete organization profile ==> "+id);
		logger.info("Delete device organization profile ==> "+id);
		globalConfigService.deleteOrganizationProfile(id);

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// USER-PROFILE

	@GetMapping("/user_profile")
	public ResponseEntity<List<UserProfiles>> getUserProfiles() {
		System.out.println("GetUserProfiles");
		logger.info("GetUserProfiles");
		return new ResponseEntity<List<UserProfiles>>(globalConfigService.allUserProfiles(), HttpStatus.OK);
	}

	@GetMapping("/user_profile_count")
	public ResponseEntity<Long> userProfilesCount() {
		logger.info("UserProfilescount");
		System.out.println("UserProfilescount");
		return new ResponseEntity<Long>(globalConfigService.userProfilescount(), HttpStatus.OK);
	}

	@GetMapping("/user_profile/{id}")
	public ResponseEntity<UserProfiles> getUserProfilesById(@PathVariable Long id) {
		logger.info("GetUserProfilesById ==>" + id);
		System.out.println("GetUserProfilesById ==>" + id);
		return new ResponseEntity<UserProfiles>(globalConfigService.getSingleUserProfiles(id), HttpStatus.OK);
	}

	@PostMapping("/user_profile")
	public ResponseEntity<UserProfiles> saveUserProfiles(@RequestBody UserProfiles user_profile) {
		logger.info("SaveUserProfiles ==>" + user_profile);
		System.out.println("SaveUserProfiles ==>" + user_profile);
		return new ResponseEntity<UserProfiles>(globalConfigService.saveUserProfiles(user_profile), HttpStatus.CREATED);
	}

	@PatchMapping("/edit_user_profile/{id}")
	public ResponseEntity<UserProfiles> updateUserProfile(@PathVariable Long id,
			@RequestBody UserProfiles user_profile) {
		System.out.println("edit user profile ==> "+user_profile);
		logger.info("edit user profile ==> "+user_profile);
		return new ResponseEntity<UserProfiles>(globalConfigService.updateUserProfiles(id, user_profile),
				HttpStatus.OK);
	}

	@DeleteMapping("/delete_user_profile/{id}")
	public void deleteUserProfile(@PathVariable Long id) {
		System.out.println("Delete user profile ==> "+id);
		logger.info("Delete  user profile ==> "+id);
		globalConfigService.deleteUserProfile(id);

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// NOTIFICATION-EMAIL-CONFIG

	@GetMapping("/email_config")
	public ResponseEntity<List<NotificationEmailConfig>> getNotificationEmailConfig() {
		System.out.println("GetNotificationEmailConfig");
		logger.info("GetNotificationEmailConfig");
		return new ResponseEntity<List<NotificationEmailConfig>>(globalConfigService.allNotificationEmailConfig(),
				HttpStatus.OK);
	}

	@GetMapping("/email_config_count")
	public ResponseEntity<Long> notificationEmailConfigCount() {
		System.out.println("NotificationEmailConfigcount");
		logger.info("NotificationEmailConfigcount");
		return new ResponseEntity<Long>(globalConfigService.notificationEmailConfigCount(), HttpStatus.OK);
	}

	@GetMapping("/email_config/{id}")
	public ResponseEntity<NotificationEmailConfig> getNotificationEmailConfigById(@PathVariable Long id) {
		System.out.println("GetNotificationEmailConfigById ==>" + id);
		logger.info("GetNotificationEmailConfigById ==>" + id);
		return new ResponseEntity<NotificationEmailConfig>(globalConfigService.getSingleNotificationEmailConfig(id),
				HttpStatus.OK);
	}

	@PostMapping("/email_config")
	public ResponseEntity<NotificationEmailConfig> saveNotificationEmailConfig(
			@RequestBody NotificationEmailConfig notification_email_config) {
		System.out.println("SaveNotificationEmailConfig ==>" + notification_email_config);
		logger.info("SaveNotificationEmailConfig ==>" + notification_email_config);
		return new ResponseEntity<NotificationEmailConfig>(
				globalConfigService.saveNotificationEmailConfig(notification_email_config), HttpStatus.CREATED);
	}

	@PatchMapping("/edit_email_config/{id}")
	public ResponseEntity<NotificationEmailConfig> updateNotificationEmailConfig(@PathVariable Long id,
			@RequestBody NotificationEmailConfig notification_email_config) {
		System.out.println("Edit email config ==> "+notification_email_config);
		logger.info("Edit email config ==> "+notification_email_config);
		return new ResponseEntity<NotificationEmailConfig>(
				globalConfigService.updateNotificationEmailConfig(id, notification_email_config), HttpStatus.OK);
	}

	@DeleteMapping("/delete_email_config/{id}")
	public void deleteNotificationEmailConfig(@PathVariable Long id) {
		System.out.println("Delete email config ==> "+id);
		logger.info("Delete email config ==> "+id);
		globalConfigService.deleteNotificationEmailConfig(id);

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

	// NOTIFICATION-SMS-CONFIG

	@GetMapping("/sms_config")
	public ResponseEntity<List<NotificationSmsConfig>> getNotificationSmsConfig() {
		logger.info("GetNotificationSmsConfig");
		System.out.println("GetNotificationSmsConfig");
		return new ResponseEntity<List<NotificationSmsConfig>>(globalConfigService.allNotificationSmsConfig(),
				HttpStatus.OK);
	}

	@GetMapping("/sms_config_count")
	public ResponseEntity<Long> notificationSmsConfigCount() {
		logger.info("NotificationSmsConfigcount");
		System.out.println("NotificationSmsConfigcount");
		return new ResponseEntity<Long>(globalConfigService.notificationSmsConfigCount(), HttpStatus.OK);
	}

	@GetMapping("/sms_config/{id}")
	public ResponseEntity<NotificationSmsConfig> getNotificationSmsConfigById(@PathVariable Long id) {
		System.out.println("GetNotificationSmsConfigById ==>" + id);
		logger.info("GetNotificationSmsConfigById ==>" + id);
		return new ResponseEntity<NotificationSmsConfig>(globalConfigService.getSingleNotificationSmsConfig(id),
				HttpStatus.OK);
	}

	@PostMapping("/sms_config")
	public ResponseEntity<NotificationSmsConfig> saveNotificationSmsConfig(
			@RequestBody NotificationSmsConfig notification_sms_config) {
		System.out.println("SaveNotificationSmsConfig ==>" + notification_sms_config);
		logger.info("SaveNotificationSmsConfig ==>" + notification_sms_config);
		return new ResponseEntity<NotificationSmsConfig>(
				globalConfigService.saveNotificationSmsConfig(notification_sms_config), HttpStatus.CREATED);
	}

	@PatchMapping("/edit_sms_config/{id}")
	public ResponseEntity<NotificationSmsConfig> updateNotificationSmsConfig(@PathVariable Long id,
			@RequestBody NotificationSmsConfig notification_sms_config) {
		System.out.println("Edit sms config ==> "+notification_sms_config);
		logger.info("Edit sms config ==> "+notification_sms_config);
		return new ResponseEntity<NotificationSmsConfig>(
				globalConfigService.updateNotificationSmsConfig(id, notification_sms_config), HttpStatus.OK);
	}

	@DeleteMapping("/delete_sms_config/{id}")
	public void deleteNotificationSmsConfig(@PathVariable Long id) {
		System.out.println("Delete sms config ==> "+id);
		logger.info("Delete sms config ==> "+id);
		globalConfigService.deleteNotificationSmsConfig(id);

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------------------------

}
