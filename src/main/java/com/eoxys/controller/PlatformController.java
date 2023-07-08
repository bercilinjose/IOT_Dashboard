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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eoxys.model.PlatformInfo;
import com.eoxys.service.PlatformService;

@RestController
//@PreAuthorize("hasAuthority('ADMIN_GROUP')")
public class PlatformController {

	private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);

	@Autowired
	private PlatformService platformService;

	@GetMapping("/platform")
	public ResponseEntity<List<PlatformInfo>> getPlatform() {
		System.out.println("GetPlatform");
		logger.info("GetPlatform");
		return new ResponseEntity<List<PlatformInfo>>(platformService.allPlatform(), HttpStatus.OK);
	}

	@GetMapping("/platform_count")
	public ResponseEntity<Long> platformCount() {
		System.out.println("PlatformCount");
		logger.info("PlatformCount");
		return new ResponseEntity<Long>(platformService.platformCount(), HttpStatus.OK);
	}

	@GetMapping("/platform/{id}")
	public ResponseEntity<PlatformInfo> getPlatformById(@PathVariable Integer id) {
		System.out.println("GetPlatformById ==>" + id);
		logger.info("GetPlatformById ==>" + id);
		return new ResponseEntity<PlatformInfo>(platformService.getSinglePlatform(id), HttpStatus.OK);
	}

	@PostMapping("/platform")
	public String addNewPlatform(@RequestBody PlatformInfo userInfo) {
		System.out.println("PlatformInfo => " + userInfo);
		logger.info("PlatformInfo => " + userInfo);
		return platformService.addPlatform(userInfo);
	}

	@DeleteMapping("/platform/{id}")
	public void deletePlatform(@PathVariable Integer id) {
		platformService.deletePlatform(id);
	}

}
