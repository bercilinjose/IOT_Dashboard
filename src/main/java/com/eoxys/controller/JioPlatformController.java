package com.eoxys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eoxys.service.JioPlatformService;
import com.eoxys.service.RawDataService;

@RestController
@PreAuthorize("hasAuthority('JIO')")
public class JioPlatformController {
	
	private static final Logger logger = LoggerFactory.getLogger(JioPlatformController.class);
	
	@Autowired
	private RawDataService rawdataService;
	
	@Autowired
	private JioPlatformService jioplatformService;

//	@PostMapping("/notification/v2/ntfy")
	@RequestMapping (value = "/notification/v2/ntfy", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	public ResponseEntity<Map<String, String>> notification(HttpServletRequest request,@RequestBody Map<String,Object> body) {
		
		logger.info("notification api is hitted");
		logger.info("body ==> " + body);
		
		return new ResponseEntity<Map<String, String>>(jioplatformService.deviceNotify(request , body),HttpStatus.OK);
	}
	
//	@PostMapping("/device_data")
	@RequestMapping (value = "/device_data", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	public ResponseEntity<Map<String, String>> deviceData(@RequestBody String string) {

		return new ResponseEntity<Map<String, String>>(rawdataService.deviceDataCSV(string),HttpStatus.OK);
	}

}
