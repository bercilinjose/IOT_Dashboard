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

import com.eoxys.model.Device;
import com.eoxys.service.DeviceService;
import com.eoxys.utils.ColorLogger;

@RestController
//@PreAuthorize("hasAnyAuthority('ADMIN_GROUP','GENERAL_GROUP')")
public class DeviceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
	
	ColorLogger log = new ColorLogger();
	
	@Autowired
	private DeviceService deviceService;
	
	@GetMapping("/device")
	public ResponseEntity<List<Device>> getDevices(){
		System.out.println("GetDevices");
		logger.info("Get devices api hitted");
		return new ResponseEntity<List<Device>>(deviceService.allDevices(),HttpStatus.OK);
	}
	
	@GetMapping("/device_count")
	public ResponseEntity<Long> deviceCount(){
		System.out.println("device count");
		logger.info("device count");
		return new ResponseEntity<Long>(deviceService.deviceCount(),HttpStatus.OK);
	}
	
	@GetMapping("/device/{id}")
	public ResponseEntity<Device> getDeviceById(@PathVariable Long id){
		System.out.println("GetDeviceById ==>"+id);
		logger.info("GetDeviceById ==>"+id);
		return new ResponseEntity<Device>(deviceService.getSingleDevice(id),HttpStatus.OK);
	}
	
	@GetMapping("/device_dev_id/{dev_id}")
	public ResponseEntity<Device> getDeviceByMacId(@PathVariable String dev_id){
		System.out.println("GetDeviceByMacId ==>"+dev_id);
		logger.info("GetDeviceByMacId ==>"+dev_id);
		return new ResponseEntity<Device>(deviceService.getSingleDevicebyDevId(dev_id),HttpStatus.OK);
	}
	
	//platform independent
	
	@PostMapping("/device")
//	@PreAuthorize("hasAuthority('ADMIN_GROUP')")
	public ResponseEntity<Device> saveOrg(@RequestBody Device dev){
		System.out.println("SaveDevice ==>"+dev);
		logger.info("SaveDevice ==>"+dev);
		return new ResponseEntity<Device>(deviceService.saveDevices(dev),HttpStatus.CREATED);
	}
	
	//platform dependent
	
	@PatchMapping("/add_platform_device")
//	@PreAuthorize("hasAuthority('ADMIN_GROUP')")
	public ResponseEntity<Device>addPlatformDevice(@RequestBody Device device) {
	  System.out.println("addPlatformDevice ==>"+device);	
	  logger.info("add_platform_device"+device);
	  return new ResponseEntity<Device>(deviceService.addPlatformDevices(device), HttpStatus.OK);
	}
	
	@PatchMapping("/edit_device/{id}")
	public ResponseEntity<Device>editdevice(@PathVariable Long id,@RequestBody Device device) {
	  System.out.println("editDevice ==>"+device);	
	  logger.info("editDevice ==>"+device);	
	  return new ResponseEntity<Device>(deviceService.editdevice(id,device), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete_device/{id}")
	public ResponseEntity<Device> deleteDevice(@PathVariable Long id) {
		System.out.println("deleted devie id ==>"+id);	
		logger.info("deleted devie id ==>"+id);
		return new ResponseEntity<Device>(deviceService.deleteDevice(id), HttpStatus.OK);
	}
	
	@PatchMapping("/delete_category/{id}")
	public ResponseEntity<Device> deleteCategory(@PathVariable Long id) {
		System.out.println("deleted category id ==>"+id);	
		logger.info("deleted category id ==>"+id);
		return new ResponseEntity<Device>(deviceService.deleteCategory(id), HttpStatus.OK);
	}

}
