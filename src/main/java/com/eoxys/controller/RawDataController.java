package com.eoxys.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.eoxys.model.RawData;
import com.eoxys.service.RawDataService;

@RestController
//@PreAuthorize("hasAnyAuthority('ADMIN_GROUP','GENERAL_GROUP')")
public class RawDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(RawDataController.class);

	@Autowired
	private RawDataService rawdataService;


//	@Autowired
//	private EmailController emailController;

	@GetMapping("/raw_data")
	public ResponseEntity<List<RawData>> getAllRawData() {
		System.out.println("GetAllRawData");
		logger.info("GetAllRawData");
		return new ResponseEntity<List<RawData>>(rawdataService.allRawData(), HttpStatus.OK);
	}

	@GetMapping("/raw_data_count")
	public ResponseEntity<Long> rawDataCount() {
		System.out.println("rawDataCount");
		logger.info("rawDataCount");
		return new ResponseEntity<Long>(rawdataService.rawDataCount(), HttpStatus.OK);
	}

	@GetMapping("/raw_data/{id}")
	public ResponseEntity<RawData> getRawDataById(@PathVariable Long id) {
		System.out.println("getRawDataById ==>" + id);
		logger.info("getRawDataById ==>" + id);
		return new ResponseEntity<RawData>(rawdataService.getSingleRawData(id), HttpStatus.OK);
	}
	
	@GetMapping("/raw_data_dev_id/{dev_id}")
	public ResponseEntity<RawData> getRawDataByDevId(@PathVariable String dev_id) {
		System.out.println("getRawDataByDevId ==>" + dev_id);
		logger.info("getRawDataByDevId ==>" + dev_id);
		return new ResponseEntity<RawData>(rawdataService.getSingleRawDatabyDevId(dev_id), HttpStatus.OK);
	}

	
	
	
	
	
	
	

	

	

}
