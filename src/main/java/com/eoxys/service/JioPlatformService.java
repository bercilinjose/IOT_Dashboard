package com.eoxys.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoxys.model.Device;
import com.eoxys.model.RawData;
import com.eoxys.repository.DeviceRepository;
import com.eoxys.repository.RawDataRepository;

@Service
public class JioPlatformService {
	
	@Autowired
	private RawDataRepository rawRepository;

	@Autowired
	private DeviceRepository devRepo;
	private static final Logger logger = LoggerFactory.getLogger(JioPlatformService.class);
	
	
	public Map<String, String> deviceNotify(HttpServletRequest request, Map<String, Object> body) {

		
		String entity = request.getHeader("Entity");
		String operation = request.getHeader("Operation");

		logger.info("Entity ==> " + entity + "  Operation ==> " + operation);
		System.out.println("Entity ==> " + entity + "  Operation ==> " + operation);

		Map<String, String> resp = new HashMap<>();
		Integer dev_registration = 2;
		Integer dev_create = 1;
		Integer dev_update = 3;
		Integer dev_delete = 4;

		if (request.getHeader("Entity").equals(dev_registration.toString())
				&& request.getHeader("Operation").equals(dev_create.toString())) {

			logger.info("dev registration : dev create" + body);

			// Register device
			System.out.println("Register Device");
			Device device_add = new Device();

			RawData raw_add = new RawData();
			if (body.containsKey("device")) {
				Map<String, String> body_device = (Map<String, String>) body.get("device");
				String did = body_device.get("did");
				String bpid = (String) body.get("bpid");

				raw_add.setBpid(bpid);
				raw_add.setDevice_mac_id(did);
				raw_add.setIsDeleted(false);
				rawRepository.save(raw_add);

				device_add.setNotification(body);
				device_add.setBpid(bpid);

				device_add.setDevice_mac_id(did);
				devRepo.save(device_add);
				resp.put("msg", "success");

				return resp;
			} else {
				resp.put("msg", "invalid data device key is not present");
				return resp;
			}

		}

		if (request.getHeader("Entity").equals(dev_registration.toString())
				&& request.getHeader("Operation").equals(dev_update.toString())) {
			// Update device

			logger.info("dev registration : dev update " + body);
			System.out.println("Update device");
			if (body.containsKey("device")) {
				Map<String, String> body_device = (Map<String, String>) body.get("device");
				String did = body_device.get("did");

				Optional<Device> devOpt = devRepo.findByDeviceId(did);
				Device device_update = devOpt.get();
				device_update.setNotification(body);
				devRepo.save(device_update);
				resp.put("msg", "success");

				return resp;
			} else {
				resp.put("msg", "invalid data device key is not present");
				return resp;
			}

		}

		if (request.getHeader("Entity").equals(dev_registration.toString())
				&& request.getHeader("Operation").equals(dev_delete.toString())) {
			// Unregister device

			logger.info("dev registration : dev unregister " + body);
			System.out.println("Unregister device");
			if (body.containsKey("bpid")) {
				String bpid = (String) body.get("bpid");

				Optional<Device> devOpt = devRepo.findByDeviceBpid(bpid);
				Device device_update = devOpt.get();
				Map<String, Object> message = new HashMap<>();
				message.put("notification", "Device unregistered");
				device_update.setNotification(message);
				devRepo.save(device_update);
				resp.put("msg", "success");

				return resp;
			} else {
				resp.put("msg", "invalid data bpid is not present");
				return resp;
			}

		}

		System.out.println("Body == >" + body);

		resp.put("msg", "Entity and operation dosent satisfied");

		return resp;

	}
	
	

}
