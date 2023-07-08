package com.eoxys.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eoxys.model.PlatformInfo;
import com.eoxys.repository.PlatformInfoRepository;

@Service
public class PlatformService {

	@Autowired
	private PlatformInfoRepository platforminfoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(PlatformService.class);

	public Long platformCount() {
		return platforminfoRepository.count();
	}

	public List<PlatformInfo> allPlatform() {
		return platforminfoRepository.findAll();
	}

	public PlatformInfo getSinglePlatform(Integer id) {
		Optional<PlatformInfo> users = platforminfoRepository.findById(id);
		if (users.isPresent()) {
			return users.get();

		}

		throw new RuntimeException("Platform is not present for this id ==>" + id);
	}

	public String addPlatform(PlatformInfo userInfo) {

		Optional<PlatformInfo> optSmsConfig = platforminfoRepository.findByEmail(userInfo.getEmail());
		if (optSmsConfig.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "platform already exists !");
		} else {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			platforminfoRepository.save(userInfo);
			return "user added to system ";
		}

	}

	public void deletePlatform(Integer id) {
		platforminfoRepository.deleteById(id);
	}

}
