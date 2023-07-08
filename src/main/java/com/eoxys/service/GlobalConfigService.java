package com.eoxys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eoxys.model.AlarmRule;
import com.eoxys.model.Device;
import com.eoxys.model.DeviceCategory;
import com.eoxys.model.DeviceDataProfile;
import com.eoxys.model.DeviceProfile;
import com.eoxys.model.NotificationEmailConfig;
import com.eoxys.model.NotificationSmsConfig;
import com.eoxys.model.OrganizationProfile;
import com.eoxys.model.UserProfiles;
import com.eoxys.repository.AlarmRuleRepository;
import com.eoxys.repository.DeviceCategoryRepository;
import com.eoxys.repository.DeviceDataProfileRepository;
import com.eoxys.repository.DeviceProfileRepository;
import com.eoxys.repository.DeviceRepository;
import com.eoxys.repository.NotificationEmailConfigRepository;
import com.eoxys.repository.NotificationSmsConfigRepository;
import com.eoxys.repository.OrganizationProfileRepository;
import com.eoxys.repository.UserProfilesRepository;

@Service
public class GlobalConfigService {
	
	@Autowired
	private DeviceCategoryRepository devicecategoryRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	public Long deviceCategoryCount() {
		return devicecategoryRepository.count();
	}
	
	
	public List<DeviceCategory> allDeviceCategory(){
		return devicecategoryRepository.findAll();
	}
	
	
	public DeviceCategory getSingleDeviceCategory(Long id) {
		Optional<DeviceCategory> device = devicecategoryRepository.findById(id);
		if(device.isPresent()) {
			return device.get();
		}
		throw new RuntimeException("device category is not present for this id ==>"+id);
	}
	
	
	public DeviceCategory saveDeviceCategory(DeviceCategory devicecategory) {
		Optional<DeviceCategory> optdevcat = devicecategoryRepository.findbyCategoryName(devicecategory.getCategory_name());
		if(optdevcat.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "category name already exists!");
		}else {
			devicecategory.setIsDeleted(false);
			return devicecategoryRepository.save(devicecategory);
		}
		
	}
	
	
	public DeviceCategory editDeviceCategory(Long id,DeviceCategory devicecategory) {
		Optional<DeviceCategory> optdevcat = devicecategoryRepository.findById(id);
		DeviceCategory devcat = optdevcat.get();
		
		Optional<DeviceCategory> optdevcatname = devicecategoryRepository.findbyCategoryName(devicecategory.getCategory_name());
		DeviceCategory devcatname = null;
		if(optdevcatname.isPresent()) {
			devcatname = optdevcatname.get();
			if(id.equals(devcatname.getDevice_category_id())) {
				
				devcat.setUpdated_by(devicecategory.getUpdated_by());
				devcat.setCategory_name(devicecategory.getCategory_name());
				devcat.setDevice_data_profile(devicecategory.getDevice_data_profile());
				devcat.setDevice_profile(devicecategory.getDevice_profile());
				devcat.setGlobal_alarm_rule(devicecategory.getGlobal_alarm_rule());
				devcat.setDevice_image(devicecategory.getDevice_image());
				devcat = devicecategoryRepository.save(devcat);
				return devcat;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "category name matches with other category name!");
			}
		}else {
			devcat.setUpdated_by(devicecategory.getUpdated_by());
			devcat.setCategory_name(devicecategory.getCategory_name());
			devcat.setDevice_data_profile(devicecategory.getDevice_data_profile());
			devcat.setDevice_profile(devicecategory.getDevice_profile());
			devcat.setGlobal_alarm_rule(devicecategory.getGlobal_alarm_rule());
			devcat.setDevice_image(devicecategory.getDevice_image());
			devcat = devicecategoryRepository.save(devcat);
			return devcat;
		}
		
		
		
		
		
	}
	
	public void deleteDeviceCategory(Long id) {
		
		devicecategoryRepository.deleteById(id);
		
		String cat_id = String.valueOf(id);
		
		List<Device> device_list = deviceRepository.findAll();
		for(int i=0 ;i<=device_list.size()-1 ; i++) {
			String dev_cat_id = String.valueOf(device_list.get(i).getDevice_category().get(0).get("device_category_id"));
			if(cat_id.equals(dev_cat_id)) {
				List<Map<String,Object>> category = new ArrayList<Map<String,Object>>();
				Device device = device_list.get(i);
				Map<String,Object> device_category = device_list.get(i).getDevice_category().get(0);
				device_category.replace("isDeleted", true);
				category.add(device_category);
				device.setDevice_category(category);
				deviceRepository.save(device);
				
			}
			
		}
		
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	//DEVICE-DATA-PROFILE
	
	@Autowired
	private DeviceDataProfileRepository devicedataprofileRepository;
	
	
	public Long deviceDataProfilecount() {
		return devicedataprofileRepository.count();
	}
	
	
	public List<DeviceDataProfile> allDeviceDataProfile(){
		return devicedataprofileRepository.findAll();
	}
	
	
	public DeviceDataProfile getSingleDeviceDataProfile(Long id) {
		Optional<DeviceDataProfile> device_data_profile = devicedataprofileRepository.findById(id);
		if(device_data_profile.isPresent()) {
			return device_data_profile.get();
		}
		throw new RuntimeException("device data profile is not present for this id ==>"+id);
	}
	
	
	public DeviceDataProfile saveDeviceDataProfile(DeviceDataProfile devicedataprofile) {
		Optional<DeviceDataProfile> optdataprof = devicedataprofileRepository.findByDataProfileName(devicedataprofile.getData_profile_name());
		if(optdataprof.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data profile name already exists !");
		}else {
			return devicedataprofileRepository.save(devicedataprofile);
		}
		
	}
	
	
	public DeviceDataProfile editDeviceDataProfile(Long id,DeviceDataProfile devicedataprofile) {
		Optional<DeviceDataProfile> optdevdatapro = devicedataprofileRepository.findById(id);
		DeviceDataProfile dev_data_pro = optdevdatapro.get();
		
		
		Optional<DeviceDataProfile> optdataprofname = devicedataprofileRepository.findByDataProfileName(devicedataprofile.getData_profile_name());
		DeviceDataProfile devdataname = null;
		if(optdataprofname.isPresent()) {
			devdataname = optdataprofname.get();
			if(id.equals(devdataname.getDevice_data_profile_id())) {
				
				dev_data_pro.setUpdated_by(devicedataprofile.getUpdated_by());
				dev_data_pro.setData_format(devicedataprofile.getData_format());
				dev_data_pro.setData_profile_name(devicedataprofile.getData_profile_name());
				dev_data_pro.setCustom_data(devicedataprofile.getCustom_data());
				dev_data_pro.setIcon_image(devicedataprofile.getIcon_image());
				dev_data_pro = devicedataprofileRepository.save(dev_data_pro);
				return dev_data_pro;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data profile name matches with other data profile name!");
			}
		}else {
			dev_data_pro.setUpdated_by(devicedataprofile.getUpdated_by());
			dev_data_pro.setData_format(devicedataprofile.getData_format());
			dev_data_pro.setData_profile_name(devicedataprofile.getData_profile_name());
			dev_data_pro.setCustom_data(devicedataprofile.getCustom_data());
			dev_data_pro.setIcon_image(devicedataprofile.getIcon_image());
			dev_data_pro = devicedataprofileRepository.save(dev_data_pro);
			return dev_data_pro;
		}
		
		
	}
	
	public void deleteDeviceDataProfile(Long id) {
		devicedataprofileRepository.deleteById(id);
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	//DEVICE-PROFILE
	
	
	@Autowired
	private DeviceProfileRepository deviceprofileRepository;
	
	
	public Long deviceProfileCount() {
		return deviceprofileRepository.count();
	}
	
	
	public List<DeviceProfile> allDeviceProfile(){
		return deviceprofileRepository.findAll();
	}
	
	
	public DeviceProfile getSingleDeviceProfile(Long id) {
		Optional<DeviceProfile> device_profile = deviceprofileRepository.findById(id);
		if(device_profile.isPresent()) {
			return device_profile.get();
		}
		throw new RuntimeException("device profile is not present for this id ==>"+id);
	}
	
	
	public DeviceProfile saveDeviceProfile(DeviceProfile deviceprofile) {
		
		Optional<DeviceProfile> optdevprof = deviceprofileRepository.findByDeviceProfileName(deviceprofile.getDevice_profile_name());
		if(optdevprof.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device profile name already exists !");
		}else {
			return deviceprofileRepository.save(deviceprofile);
		}
		
		
	}
	
	
	public DeviceProfile editDeviceProfile(Long id,DeviceProfile deviceprofile) {
		Optional<DeviceProfile> optdevpro = deviceprofileRepository.findById(id);
		DeviceProfile dev_pro = optdevpro.get();
		
		
		
		Optional<DeviceProfile> optdevprofname = deviceprofileRepository.findByDeviceProfileName(deviceprofile.getDevice_profile_name());
		DeviceProfile devprofname = null;
		if(optdevprofname.isPresent()) {
			devprofname = optdevprofname.get();
			if(id.equals(devprofname.getDevice_profile_id())) {
				
				dev_pro.setUpdated_by(deviceprofile.getUpdated_by());
				dev_pro.setDevice_profile_name(deviceprofile.getDevice_profile_name());
				dev_pro.setCustom_data(deviceprofile.getCustom_data());
				dev_pro = deviceprofileRepository.save(dev_pro);
				return dev_pro;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device profile name matches with other device profile name!");
			}
		}else {
			dev_pro.setUpdated_by(deviceprofile.getUpdated_by());
			dev_pro.setDevice_profile_name(deviceprofile.getDevice_profile_name());
			dev_pro.setCustom_data(deviceprofile.getCustom_data());
			dev_pro = deviceprofileRepository.save(dev_pro);
			return dev_pro;
		}
		
		
	}
	
	public void deleteDeviceProfile(Long id) {
		deviceprofileRepository.deleteById(id);
	}
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	//ALARM-RULE
	
	
	@Autowired
	private AlarmRuleRepository alarmruleRepository;
	
	public Long alarmRulecount() {
		return alarmruleRepository.count();
	}
	
	
	public List<AlarmRule> allAlarmRule(){
		return alarmruleRepository.findAll();
	}
	
	public List<AlarmRule> getByDataProfile(String data_profile){
		return alarmruleRepository.findByDataProfile(data_profile);
	}
	
	
	public AlarmRule getSingleAlarmRule(Long id) {
		Optional<AlarmRule> alarm_rule = alarmruleRepository.findById(id);
		if(alarm_rule.isPresent()) {
			return alarm_rule.get();
		}
		throw new RuntimeException("alarm rule is not present for this id ==>"+id);
	}
	
	
	public AlarmRule saveAlarmRule(AlarmRule alarmrule) {
		
		Optional<AlarmRule> alarm_rule = alarmruleRepository.findAlarmName(alarmrule.getAlarm_name());
		if(alarm_rule.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alarm rule name already exists !");
		}else {
			return alarmruleRepository.save(alarmrule);
		}
		
		
	}
	
	
	public AlarmRule editAlarmRule(Long id,AlarmRule alarmrule) {
		Optional<AlarmRule> optalarmrule = alarmruleRepository.findById(id);
		AlarmRule alarmrules = optalarmrule.get();
		
		
		Optional<AlarmRule> optalarmname = alarmruleRepository.findAlarmName(alarmrule.getAlarm_name());
		AlarmRule alarmname = null;
		if(optalarmname.isPresent()) {
			alarmname = optalarmname.get();
			if(id.equals(alarmname.getGlobal_alarm_id())) {
				
				alarmrules.setAlarm_name(alarmrule.getAlarm_name());
				alarmrules.setAlarm_notify(alarmrule.getAlarm_notify());
				alarmrules.setAlarm_rules(alarmrule.getAlarm_rules());
				alarmrules.setData_profile(alarmrules.getData_profile());
				alarmrules = alarmruleRepository.save(alarmrules);
				return alarmrules;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alarm rule name matches with other alarm rule name !");
			}
		}else {
			alarmrules.setAlarm_name(alarmrule.getAlarm_name());
			alarmrules.setAlarm_notify(alarmrule.getAlarm_notify());
			alarmrules.setAlarm_rules(alarmrule.getAlarm_rules());
			alarmrules.setData_profile(alarmrules.getData_profile());
			alarmrules = alarmruleRepository.save(alarmrules);
			return alarmrules;
		}
		
		
		
	}
	
	public void deleteAlarmrule(Long id) {
		alarmruleRepository.deleteById(id);
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	//ORGANIZATION-PROFILE
	
	
	@Autowired
	private OrganizationProfileRepository organizationprofileRepository;
	
	
	public Long organizationProfileCount() {
		return organizationprofileRepository.count();
	}
	
	
	public List<OrganizationProfile> allOrganizationProfile(){
		return organizationprofileRepository.findAll();
	}
	
	
	public OrganizationProfile getSingleOrganizationProfile(Long id) {
		Optional<OrganizationProfile> organization_profile = organizationprofileRepository.findById(id);
		if(organization_profile.isPresent()) {
			return organization_profile.get();
		}
		throw new RuntimeException("organization profile is not present for this id ==>"+id);
	}
	
	
	public OrganizationProfile saveOrganizationProfile(OrganizationProfile organizationprofile) {
		
		Optional<OrganizationProfile> optorgprofile = organizationprofileRepository.findByOrgProfileName(organizationprofile.getOrganization_profile_name());
		if(optorgprofile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "organization profile name already exists !");
		}else {
			return organizationprofileRepository.save(organizationprofile);
		}
		
	}
	
	
	public OrganizationProfile editOrganizationProfile(Long id,OrganizationProfile organizationprofile) {
		Optional<OrganizationProfile> optorgpro = organizationprofileRepository.findById(id);
		OrganizationProfile org_pro = optorgpro.get();
		
		
		Optional<OrganizationProfile> optalarmname = organizationprofileRepository.findByOrgProfileName(organizationprofile.getOrganization_profile_name());
		OrganizationProfile alarmname = null;
		if(optalarmname.isPresent()) {
			alarmname = optalarmname.get();
			if(id.equals(alarmname.getOrganization_profile_id())) {
				
				org_pro.setUpdated_by(organizationprofile.getUpdated_by());
				org_pro.setOrganization_profile_name(organizationprofile.getOrganization_profile_name());
				org_pro.setCustom_data(organizationprofile.getCustom_data());
				org_pro = organizationprofileRepository.save(org_pro);
				return org_pro;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alarm rule name matches with other alarm rule name !");
			}
		}else {
			org_pro.setUpdated_by(organizationprofile.getUpdated_by());
			org_pro.setOrganization_profile_name(organizationprofile.getOrganization_profile_name());
			org_pro.setCustom_data(organizationprofile.getCustom_data());
			org_pro = organizationprofileRepository.save(org_pro);
			return org_pro;
		}
		
		
	}
	
	public void deleteOrganizationProfile(Long id) {
		organizationprofileRepository.deleteById(id);
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	//USER-PROFILE
	
	
	@Autowired
	private UserProfilesRepository userprofilesRepository;
	
	
	public Long userProfilescount() {
		return userprofilesRepository.count();
	}
	
	
	public List<UserProfiles> allUserProfiles(){
		return userprofilesRepository.findAll();
	}
	
	
	public UserProfiles getSingleUserProfiles(Long id) {
		Optional<UserProfiles> user_profiles = userprofilesRepository.findById(id);
		if(user_profiles.isPresent()) {
			return user_profiles.get();
		}
		throw new RuntimeException("user profile is not present for this id ==>"+id);
	}
	
	
	public UserProfiles saveUserProfiles(UserProfiles userprofiles) {
		
		Optional<UserProfiles> optuserprofile = userprofilesRepository.findByUserProfileName(userprofiles.getUser_profile_name());
		if(optuserprofile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user profile name already exists !");
		}else {
			return userprofilesRepository.save(userprofiles);
		}
		
	}
	
	public UserProfiles updateUserProfiles(Long id, UserProfiles userprofiles) {
		Optional<UserProfiles> optEn = userprofilesRepository.findById(id);
		UserProfiles update = optEn.get();
		
		
		Optional<UserProfiles> optalarmname = userprofilesRepository.findByUserProfileName(userprofiles.getUser_profile_name());
		UserProfiles alarmname = null;
		if(optalarmname.isPresent()) {
			alarmname = optalarmname.get();
			if(id.equals(alarmname.getUser_profile_id())) {
				
				update.setUpdated_by(userprofiles.getUpdated_by());
				update.setUser_profile_name(userprofiles.getUser_profile_name());
				update.setCustom_data(userprofiles.getCustom_data());
				update = userprofilesRepository.save(update);
				return update;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user profile name matches with other user profile name !");
			}
		}else {
			update.setUpdated_by(userprofiles.getUpdated_by());
			update.setUser_profile_name(userprofiles.getUser_profile_name());
			update.setCustom_data(userprofiles.getCustom_data());
			update = userprofilesRepository.save(update);
			return update;
		}
		
	}
	
	
	public void deleteUserProfile(Long id) {
		userprofilesRepository.deleteById(id);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	//NOTIFICATION-EMAIL-CONFIG
	
	
	@Autowired
	private NotificationEmailConfigRepository notificationemailconfigRepository;
	
	public Long notificationEmailConfigCount() {
		return notificationemailconfigRepository.count();
	}
	
	
	public List<NotificationEmailConfig> allNotificationEmailConfig(){
		return notificationemailconfigRepository.findAll();
	}
	
	
	public NotificationEmailConfig getSingleNotificationEmailConfig(Long id) {
		Optional<NotificationEmailConfig> email_config = notificationemailconfigRepository.findById(id);
		if(email_config.isPresent()) {
			return email_config.get();
		}
		throw new RuntimeException("Notification Email Config is not present for this id ==>"+id);
	}
	
	
	public NotificationEmailConfig saveNotificationEmailConfig(NotificationEmailConfig notificationemailconfig) {
		
		Optional<NotificationEmailConfig> optEmailConfig = notificationemailconfigRepository.findByContentName(notificationemailconfig.getContent_name());
		if(optEmailConfig.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content name already exists !");
		}else {
			return notificationemailconfigRepository.save(notificationemailconfig);
		}
		
		
	}
	
	public NotificationEmailConfig updateNotificationEmailConfig(Long id, NotificationEmailConfig notificationemailconfig) {
		Optional<NotificationEmailConfig> optEn = notificationemailconfigRepository.findById(id);
		NotificationEmailConfig update = optEn.get();
		
		Optional<NotificationEmailConfig> optEmailname = notificationemailconfigRepository.findByContentName(notificationemailconfig.getContent_name());
		NotificationEmailConfig emailName = null;
		if(optEmailname.isPresent()) {
			emailName = optEmailname.get();
			if(id.equals(emailName.getEmail_config_id())) {
				
				update.setUpdated_by(notificationemailconfig.getUpdated_by());
				update.setContent_name(notificationemailconfig.getContent_name());
				update.setEmail_content(notificationemailconfig.getEmail_content());
				update.setSubject(notificationemailconfig.getSubject());
				update = notificationemailconfigRepository.save(update);
				return update;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content name matches with other content name !");
			}
		}else {
			update.setUpdated_by(notificationemailconfig.getUpdated_by());
			update.setContent_name(notificationemailconfig.getContent_name());
			update.setEmail_content(notificationemailconfig.getEmail_content());
			update.setSubject(notificationemailconfig.getSubject());
			update = notificationemailconfigRepository.save(update);
			return update;
		}
		
		
	}
	
	public void deleteNotificationEmailConfig(Long id) {
		notificationemailconfigRepository.deleteById(id);
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	//NOTIFICATION-SMS-CONFIG
	
	
	@Autowired
	private NotificationSmsConfigRepository notificationsmsconfigRepository;
	
	public Long notificationSmsConfigCount() {
		return notificationsmsconfigRepository.count();
	}
	
	
	public List<NotificationSmsConfig> allNotificationSmsConfig(){
		return notificationsmsconfigRepository.findAll();
	}
	
	
	public NotificationSmsConfig getSingleNotificationSmsConfig(Long id) {
		Optional<NotificationSmsConfig> sms_config = notificationsmsconfigRepository.findById(id);
		if(sms_config.isPresent()) {
			return sms_config.get();
		}
		throw new RuntimeException("Notification Sms Config is not present for this id ==>"+id);
	}
	
	
	public NotificationSmsConfig saveNotificationSmsConfig(NotificationSmsConfig notificationsmsconfig) {
		
		Optional<NotificationSmsConfig> optSmsConfig = notificationsmsconfigRepository.findByContentName(notificationsmsconfig.getContent_name());
		if(optSmsConfig.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content name already exists !");
		}else {
			return notificationsmsconfigRepository.save(notificationsmsconfig);
		}
	}
	
	public NotificationSmsConfig updateNotificationSmsConfig(Long id, NotificationSmsConfig notificationsmsconfig) {
		Optional<NotificationSmsConfig> optEn = notificationsmsconfigRepository.findById(id);
		NotificationSmsConfig update = optEn.get();
		
		
		Optional<NotificationSmsConfig> optSmsname = notificationsmsconfigRepository.findByContentName(notificationsmsconfig.getContent_name());
		NotificationSmsConfig smsName = null;
		if(optSmsname.isPresent()) {
			smsName = optSmsname.get();
			if(id.equals(smsName.getSms_config_id())) {
				
				update.setUpdated_by(notificationsmsconfig.getUpdated_by());
				update.setContent_name(notificationsmsconfig.getContent_name());
				update.setSms_content(notificationsmsconfig.getSms_content());
				update.setSubject(notificationsmsconfig.getSubject());
				update = notificationsmsconfigRepository.save(update);
				return update;
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content name matches with other content name !");
			}
		}else {
			update.setUpdated_by(notificationsmsconfig.getUpdated_by());
			update.setContent_name(notificationsmsconfig.getContent_name());
			update.setSms_content(notificationsmsconfig.getSms_content());
			update.setSubject(notificationsmsconfig.getSubject());
			update = notificationsmsconfigRepository.save(update);
			return update;
		}
		
		
	}
	
	public void deleteNotificationSmsConfig(Long id) {
		notificationsmsconfigRepository.deleteById(id);
	}

}
