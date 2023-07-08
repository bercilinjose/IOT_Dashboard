package com.eoxys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoxys.model.Device;
import com.eoxys.model.RawData;
import com.eoxys.repository.DeviceRepository;
import com.eoxys.repository.RawDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RawDataService {

	private static final Logger logger = LoggerFactory.getLogger(RawDataService.class);


	@Autowired
	private DeviceService devService;


	@Autowired
	private RawDataRepository rawRepository;

	@Autowired
	private DeviceRepository devRepo;

	
	@Autowired
	private AlarmServiceThread alThread;

	public Long rawDataCount() {
		return rawRepository.count();
	}

	public List<RawData> allRawData() {
		return rawRepository.findAll();
	}

	public RawData getSingleRawData(Long id) {
		Optional<RawData> rawdata = rawRepository.findById(id);
		if (rawdata.isPresent()) {
			return rawdata.get();

		}

		throw new RuntimeException("Raw data is not present for this id ==>" + id);
	}

	public RawData getSingleRawDatabyDevId(String dev_id) {
		Optional<RawData> rawdata = rawRepository.findByDeviceId(dev_id);
		if (rawdata.isPresent()) {
			return rawdata.get();

		}

		throw new RuntimeException("Raw data is not present for this id ==>" + dev_id);
	}

	public RawData addRawData(Long id, RawData data) {
		Optional<RawData> dev_data = rawRepository.findById(id);
		RawData device_data = dev_data.get();
		device_data = rawRepository.save(device_data);
		return device_data;

	}

	public Map<String, String> deviceDataCSV(String string) {

		Map<String, String> object = new HashMap<>();
		logger.info("-----Raw Data------" + string);

		String input = string;
		System.out.println("Raw data ==>" + input);

		List<String> raw_key = new ArrayList<>();

		List<String> raw_values = new ArrayList<>();

		Map<String, Object> map = new HashMap<>();

		ArrayList<Map<String, Object>> parsed = new ArrayList<>();

		Boolean isSize = false;

		String[] values = input.split(",");

		for (String w : values) {
			raw_values.add(w);
		}

		Optional<Device> optDevicee = null;
		String device_id = null;
		for (String w : raw_values) {
			optDevicee = devRepo.findByDeviceId(w);
			if (optDevicee.isPresent()) {
				System.out.println("present");
				device_id = optDevicee.get().getDevice_mac_id();
			}
		}


		Device device = null;

		if (device_id != null) {

			Optional<Device> optDevice = devRepo.findByDeviceId(device_id);
			Long dev_id = optDevice.get().getDevice_id();
			if (optDevice.isPresent()) {

				device = devService.enManager(dev_id);

				List<Map<String, Object>> custom_params = (List<Map<String, Object>>) ((List<Map<String, Object>>) device
						.getDevice_category().get(0).get("device_data_profile")).get(0).get("custom_data");

				for (Map<String, Object> parameter : custom_params) {

					raw_key.add(parameter.get("parameter").toString());

				}
				System.out.println("raw key ==> " + raw_key);
				System.out.println("raw value ==> " + raw_values);

				if (raw_key.size() == raw_values.size()) {

					isSize = true;
					map.put("received_time", dateAndTime());
					for (int i = 0; i < raw_key.size(); i++) {
						map.put(raw_key.get(i), raw_values.get(i));
					}

					System.out.println("JSON converted  ==>" + map);

				} else {
					System.out.println("Data format mismatch || key value pair length not satisfied");
					logger.info("Data format mismatch || key value pair length not satisfied");
					object.put("alert_message_1", "Data format mismatch");
				}

			} else {

				System.out.println("Device id dosent match");
				logger.info("Device id dosent match");
				object.put("alert_message_2", "Device id dosent match");
			}

			Optional<RawData> optRaw = rawRepository.findByDeviceId(device_id);

			if (optRaw.isPresent()) {

				RawData rawdata = optRaw.get();
				Long raw_tbl_id = rawdata.getRaw_data_id();
				ArrayList<String> add_data;
				add_data = rawdata.getRaw_data();
				add_data.add(input);
				rawdata.setRaw_data(add_data);

				addRawData(raw_tbl_id, rawdata);

				if (isSize) {
					RawData parsed_data = optRaw.get();
					parsed = parsed_data.getParsed_data();
					parsed.add(map);
					parsed_data.setParsed_data(parsed);
					addRawData(raw_tbl_id, parsed_data);
//					triggerAlarm(device, map);

					System.out.println("Thread count from service before"+Thread.activeCount());
					alThread.setDevice(device, map);
					Thread t1 = new Thread(alThread);
					t1.start();
//					alThread.start();
					System.out.println("runninggggg");
					System.out.println("Thread count from service after"+Thread.activeCount());
					
					System.out.println("is alive"+t1.isAlive()); 
				} else {

					RawData optMismatch = optRaw.get();
					ArrayList<String> add_mismatch_data;
					add_mismatch_data = optMismatch.getMismatch_dataset();
					add_mismatch_data.add(input);
					rawdata.setMismatch_dataset(add_mismatch_data);

					addRawData(raw_tbl_id, rawdata);

					System.out.println("due to array size mismatch parsed data is not updated");
					logger.info("due to array size mismatch parsed data is not updated");
				}

			} else {
				System.out.println("Device id dosent match with raw data id");
				logger.info("Device id dosent match with raw data id");
				
			}


		} else {
			System.out.println("device id is not present");
			logger.info("device id is not present");
			object.put("alert_message_3", "device id is not present");
			
		}

		object.put("msg", "success");
		return object;

	}
	
	public Map<String, String> deviceDataJSON(String string) {

		Map<String, String> object = new HashMap<>();
		logger.info("-----Raw Data------" + string);

		String input = string;
		System.out.println("Raw data ==>" + input);

		List<String> raw_key = new ArrayList<>();

		List<String> raw_values = new ArrayList<>();

		ArrayList<Map<String, Object>> parsed = new ArrayList<>();

		Boolean isMatch = false;

		JSONObject json = new JSONObject(string);
		Map<String, Object> map = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(json.toString(), new TypeReference<Map<String, Object>>() {
			});
			System.out.println("json format" + map);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			raw_values.add(entry.getValue().toString());
		}

		Optional<Device> optDevicee = null;
		String device_id = null;
		for (String w : raw_values) {
			optDevicee = devRepo.findByDeviceId(w);
			if (optDevicee.isPresent()) {
				System.out.println("present");
				device_id = optDevicee.get().getDevice_mac_id();
				break;
			}
		}

		Device device = null;

		if (device_id != null) {

			Optional<Device> optDevice = devRepo.findByDeviceId(device_id);
			Long dev_id = optDevice.get().getDevice_id();
			if (optDevice.isPresent()) {

				device = devService.enManager(dev_id);

				List<Map<String, Object>> custom_params = (List<Map<String, Object>>) ((List<Map<String, Object>>) device
						.getDevice_category().get(0).get("device_data_profile")).get(0).get("custom_data");

				for (Map<String, Object> parameter : custom_params) {

					raw_key.add(parameter.get("parameter").toString());

				}
				System.out.println("raw key ==> " + raw_key);
				System.out.println("raw value ==> " + raw_values);

				if (raw_key.size() == raw_values.size()) {

					for (int i = 0; i < raw_key.size(); i++) {
						if (map.containsKey(raw_key.get(i).toString())) {
							isMatch = true;
						} else {
							isMatch = false;
							break;
						}

					}
					if (isMatch) {
						map.put("received_time", dateAndTime());
					}

					System.out.println("JSON converted  ==>" + map);

				} else {
					System.out.println("Data format mismatch || key value pair length not satisfied");
					logger.info("Data format mismatch || key value pair length not satisfied");
					object.put("alert_message_1", "Data format mismatch");
				}

			} else {

				System.out.println("Device id dosent match with device table");
				logger.info("Device id dosent match with device table");
				object.put("alert_message_2", "Device id dosent match");
				
			}

			Optional<RawData> optRaw = rawRepository.findByDeviceId(device_id);

			if (optRaw.isPresent()) {

				RawData rawdata = optRaw.get();
				Long raw_tbl_id = rawdata.getRaw_data_id();
				ArrayList<String> add_data;
				add_data = rawdata.getRaw_data();
				add_data.add(input);
				rawdata.setRaw_data(add_data);

				addRawData(raw_tbl_id, rawdata);

				if (isMatch) {
					RawData parsed_data = optRaw.get();
					parsed = parsed_data.getParsed_data();
					parsed.add(map);
					parsed_data.setParsed_data(parsed);
					addRawData(raw_tbl_id, parsed_data);
//					triggerAlarm(device, map);
					System.out.println("Thread count from service before"+Thread.activeCount());
					alThread.setDevice(device, map);
					Thread t1 = new Thread(alThread);
					t1.start();
//					alThread.start();
					System.out.println("runninggggg");
					System.out.println("Thread count from service after"+Thread.activeCount());
					
					System.out.println("is alive"+t1.isAlive()); 

				} else {

					RawData optMismatch = optRaw.get();
					ArrayList<String> add_mismatch_data;
					add_mismatch_data = optMismatch.getMismatch_dataset();
					add_mismatch_data.add(input);
					rawdata.setMismatch_dataset(add_mismatch_data);

					addRawData(raw_tbl_id, rawdata);

					System.out.println("Data format mismatch");
					logger.info("Data format mismatch");
					object.put("msg", "Data format mismatch");
				}

			} else {
				System.out.println("Device id dosent match with raw data id");
				logger.info("Device id dosent match with raw data id");
			}


		} else {
			object.put("alert_message_3", "device id not present");
		}

		object.put("msg", "success");
		return object;

	}

	
	public String dateAndTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		return formatter.format(date);
	}

	public Map<String, String> hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		System.out.println("hex   ----->   String  " + output.toString());
		return deviceDataCSV(output.toString());
	}

	public Map<String, String> receiveData(String string) {
		Map<String, String> map = new HashMap<>();

		if (isValidJSON(string)) {
			logger.info("JSON format ---> "+string);
			deviceDataJSON(string);

		} else if (isValidHex(string)) {
			logger.info("HEX format ---> "+string);
			return hexToAscii(string);

		} else if (isValidCSV(string)) {
			logger.info("CSV format ---> "+string);
			return deviceDataCSV(string);

		} else {
			map.put("message", "not matched");
		}
		return map;
	}



	public boolean isValidHex(String hex) {
		StringBuilder output = new StringBuilder("");
		try {
			for (int i = 0; i < hex.length(); i += 2) {
				String str = hex.substring(i, i + 2);
				output.append((char) Integer.parseInt(str, 16));
			}
			System.out.println("HEX");

		} catch (NumberFormatException nfe) {
			System.out.println("! HEX");
			return false;
		}
		return true;
	}

	public boolean isValidCSV(String csv) {
		Pattern pattern = Pattern.compile(",");
//		^[^,\n\"]*(?:\"[^\"]*\"[^,\n\"]*)*(?:,[^,\n\"]*(?:\"[^\"]*\"[^,\n\"]*)*)*$
		Matcher matcher = pattern.matcher(csv);
		while (matcher.find()) {
			String line = matcher.group();
			if (line.isEmpty()) {
				return false;
			}
		}
		if (matcher.hitEnd()) {
			return true;
		} else {
			return false;
		}
	}



	public boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			System.out.println("JSON");
		} catch (JSONException ex) {
			try {
				new JSONArray(json);
				System.out.println("JSON ARRAY");
			} catch (JSONException ex1) {
				System.out.println("! JSON");
				return false;
			}
		}
		return true;
	}




}
