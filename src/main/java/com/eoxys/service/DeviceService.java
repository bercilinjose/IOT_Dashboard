package com.eoxys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eoxys.model.Device;
import com.eoxys.model.RawData;
import com.eoxys.repository.DeviceRepository;
import com.eoxys.repository.RawDataRepository;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private RawDataRepository rawRepo;

	@PersistenceContext
	private EntityManager entityManager;

	public Long deviceCount() {
		return deviceRepository.count();
	}

	public List<Device> allDevices() {
		return deviceRepository.findAll();
	}

	public Device getSingleDevice(Long id) {
		Optional<Device> device = deviceRepository.findById(id);
		if (device.isPresent()) {
			return device.get();
		}
		throw new RuntimeException("device is not present for this id ==>" + id);
	}

	public Device getSingleDevicebyDevId(String dev_id) {
		Optional<Device> device = deviceRepository.findByDeviceId(dev_id);
		if (device.isPresent()) {
			return device.get();
		}
		throw new RuntimeException("device is not present for this dev_id ==>" + dev_id);
	}

	public Device enManager(Long id) {
		Device device = entityManager.find(Device.class, id);
		entityManager.detach(device);
		return device;
	}

	// platform - independent
	public Device saveDevices(Device dev) {
		Optional<Device> device = deviceRepository.findByDeviceId(dev.getDevice_mac_id());
		Optional<Device> device_name = deviceRepository.findByDevicename(dev.getDevice_name());
		if (device.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device id already exists!");
		} else if (device_name.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device name already exists!");
		} else {
			RawData raw_data = new RawData();
			raw_data.setDevice_mac_id(dev.getDevice_mac_id());
			raw_data.setDevice_name(dev.getDevice_name());
			raw_data.setIsDeleted(false);
//			raw_data.setParsed_data(new ArrayList<>());
//			raw_data.setMismatch_dataset(new ArrayList<>());
//			raw_data.setRaw_data(new ArrayList<>());
			rawRepo.save(raw_data);

			dev.setIsDeleted(false);

			return deviceRepository.save(dev);
		}

	}

	// platform - dependent

	public Device addPlatformDevices(Device device) {

		String dev_id = device.getDevice_mac_id();

		Optional<Device> optdev = deviceRepository.findByDeviceId(dev_id);
		if (optdev.isPresent()) {
			Device dev = optdev.get();
			dev.setUpdated_by(device.getUpdated_by());
			dev.setDevice_name(device.getDevice_name());
			dev.setDevice_mac_id(device.getDevice_mac_id());
			dev.setDevice_category(device.getDevice_category());
			dev.setUser_id(device.getUser_id());
			dev.setProtocol_type(device.getProtocol_type());
			dev.setIsPlatform(device.getIsPlatform());
			dev.setPlatform(device.getPlatform());
			dev.setDevice_profile(device.getDevice_profile());
			dev.setIsDeleted(false);
			dev = deviceRepository.save(dev);
			return dev;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device id is not exists !");
		}

	}

	public Device editdevice(Long id, Device device) {
		Optional<Device> optdev = deviceRepository.findById(id);
		Device dev = optdev.get();

		String dev_mac_id = dev.getDevice_mac_id();

		Optional<Device> optDevMacId = deviceRepository.findByDeviceId(device.getDevice_mac_id());
		Optional<Device> Optdevice_name = deviceRepository.findByDevicename(device.getDevice_name());
		Device devMacId = null;
		Device dev_name = null;
		
		Boolean device_id = false;
		Boolean device_name =false;
		
		if (Optdevice_name.isPresent()) {
			dev_name = Optdevice_name.get();
			device_name = id.equals(dev_name.getDevice_id());
		}else {
			device_name = true;
		}
		
		if (optDevMacId.isPresent()) {
			devMacId = optDevMacId.get();
			
			device_id = id.equals(devMacId.getDevice_id());
			
			
			if(!device_id) {
				System.out.println("! dev id");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device mac id matches with other device mac id !");
			}
				
			if(!device_name) {
				System.out.println("! dev name");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device name matches with other device name !");
			}
				
			if (device_id) {
				
				System.out.println("device id");

				Optional<RawData> optRaw = rawRepo.findByDeviceId(dev_mac_id);

				if (optRaw.isPresent()) {	
					RawData raw_data = optRaw.get();
					raw_data.setDevice_mac_id(device.getDevice_mac_id());
					raw_data.setDevice_name(device.getDevice_name());
					rawRepo.save(raw_data);
				}

				dev.setUpdated_by(device.getUpdated_by());
				dev.setDevice_name(device.getDevice_name());
				dev.setDevice_mac_id(device.getDevice_mac_id());
				dev.setDevice_category(device.getDevice_category());
				dev.setUser_id(device.getUser_id());
				dev.setProtocol_type(device.getProtocol_type());
				dev.setIsPlatform(device.getIsPlatform());
				dev.setPlatform(device.getPlatform());
				dev.setDevice_profile(device.getDevice_profile());
				dev.setIsDeleted(false);
				dev = deviceRepository.save(dev);
				
				
				return dev;

			}

			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "something went wrong !");
			}
		} else {

			Optional<RawData> optRaw = rawRepo.findByDeviceId(dev_mac_id);

			if (optRaw.isPresent()) {
				RawData raw_data = optRaw.get();
				raw_data.setDevice_mac_id(device.getDevice_mac_id());
				raw_data.setDevice_name(device.getDevice_name());
				rawRepo.save(raw_data);
			}

			dev.setUpdated_by(device.getUpdated_by());
			dev.setDevice_name(device.getDevice_name());
			dev.setDevice_mac_id(device.getDevice_mac_id());
			dev.setDevice_category(device.getDevice_category());
			dev.setUser_id(device.getUser_id());
			dev.setProtocol_type(device.getProtocol_type());
			dev.setIsPlatform(device.getIsPlatform());
			dev.setPlatform(device.getPlatform());
			dev.setDevice_profile(device.getDevice_profile());
			dev.setIsDeleted(false);
			dev = deviceRepository.save(dev);
			return dev;
		}

	}

	public Device deleteDevice(Long id) {

		Optional<Device> optdev = deviceRepository.findById(id);

		Device dev = optdev.get();
		dev.setIsDeleted(true);
		dev = deviceRepository.save(dev);

		String dev_id = dev.getDevice_mac_id();

		Optional<RawData> optraw = rawRepo.findByDeviceId(dev_id);
		if (optraw.isPresent()) {
			RawData rawdata = optraw.get();
			rawdata.setIsDeleted(true);
			rawRepo.save(rawdata);
		}
		return dev;

	}

	public Device deleteCategory(Long id) {

		Optional<Device> optdev = deviceRepository.findById(id);

		Device dev = optdev.get();
		dev.setDevice_category(null);
		deviceRepository.save(dev);
		return dev;

	}

}
