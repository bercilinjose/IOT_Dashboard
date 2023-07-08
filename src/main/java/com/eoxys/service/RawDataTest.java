package com.eoxys.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoxys.model.AlarmEventList;
import com.eoxys.model.Device;
import com.eoxys.model.Notification;
import com.eoxys.model.RawData;
import com.eoxys.repository.AlarmEventListRepository;
import com.eoxys.repository.DeviceRepository;
import com.eoxys.repository.RawDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;

@Service
public class RawDataTest {

	private static final Logger logger = LoggerFactory.getLogger(RawDataService.class);

	@Autowired
	private MailService mailService;

	@Autowired
	private DeviceService devService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private RawDataRepository rawRepository;

	@Autowired
	private DeviceRepository devRepo;

	@Autowired
	private AlarmEventListRepository alRepository;

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

	public Map<String, String> deviceData(String string) {

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
				}

			} else {

				System.out.println("Device id dosent match with device table");
				logger.info("Device id dosent match with device table");
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
					triggerAlarm(device, map);

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

			object.put("msg", "success");
			return object;

		} else {
			System.out.println("device id is not present in raw data or data format mismatch");
			logger.info("device id is not present in raw data or data format mismatch");
		}

		object.put("msg", "success");
		return object;

	}

	public void notificationAndAlarmEvent(Device device, String alarm_notify, String al_name, String alarm_parameter,
			String alarm_condition, String alarm_value) {

		AlarmEventList alarm_event_list = new AlarmEventList();
		Notification notification = new Notification();

		alarm_event_list.setAlarm_name(al_name);
		alarm_event_list.setDevice_name(device.getDevice_name());
		alarm_event_list.setDevice_mac_id(device.getDevice_mac_id());
		alarm_event_list.setAlarm_parameter_name(alarm_parameter);
		alarm_event_list.setAlarm_condition(alarm_condition);
		alarm_event_list.setAlarm_value(alarm_value);
		alarm_event_list.setNotification(alarm_notify);
		alarm_event_list.setView_status(false);
		alRepository.save(alarm_event_list);

		if (device.getUser_id() != null) {
			if (device.getUser_id().getOrganization().getSelected_email() != null) {

				notification.setNotification_receiver(device.getUser_id().getOrganization());
				notification.setDevice_mac_id(device.getDevice_mac_id());
				String notifymsg = device.getUser_id().getOrganization().getSelected_email().get("content").toString();
				notifymsg = notifymsg.replace("$alert_message", alarm_value);
				notifymsg = notifymsg.replace("$device_id", device.getDevice_mac_id());
				System.out.println("Notify msg ---> " + notifymsg);
				notification.setNotification_message(notifymsg);
				notificationService.saveNotify(notification);

				try {
					mailService.sendAlertEmail();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void triggerAlarm(Device device, Map<String, Object> map) {

		Map<String, Object> alarm_rule = ((List<Map<String, Object>>) device.getDevice_category().get(0)
				.get("global_alarm_rule")).get(0);

		String al_name = (String) alarm_rule.get("alarm_name");

		ArrayList<Map<String, Object>> custom_params = (ArrayList<Map<String, Object>>) alarm_rule.get("custom_data");

		Map<String, Object> evaluateAlarm = new HashMap<String, Object>();
		ArrayList<Map<String, Object>> onlyConditions = new ArrayList<Map<String, Object>>();

		ArrayList<Map<String, Object>> rules = new ArrayList<Map<String, Object>>();

		for (int i = 0; i <= custom_params.size() - 1; ++i) {

			Map<String, Object> checkNoRule = (Map<String, Object>) custom_params.get(i).get("main_rule");
			Boolean isRule = false;
			isRule = (Boolean) checkNoRule.get("rules");
			if (!isRule) {
				onlyConditions.add(custom_params.get(i));
				break;

			}

		}

		for (int i = 0; i <= custom_params.size() - 1; ++i) {

			Map<String, Object> checkRule = (Map<String, Object>) custom_params.get(i).get("main_rule");
			Boolean isRule = false;
			isRule = (Boolean) checkRule.get("rules");
			if (isRule) {
				rules.add(custom_params.get(i));

			}

		}
		if (onlyConditions.size() == 1) {

			Map<String, Object> no_rule = (Map<String, Object>) onlyConditions.get(0).get("main_rule");
			List<Map<String, Object>> evaluate_conditions = (List<Map<String, Object>>) no_rule.get("sub_rules");

			for (Map<String, Object> s : evaluate_conditions) {

				singleCondition(s, device, map, al_name);

			}

		}

		if (rules.size() >= 1) {

			for (int i = 0; i <= rules.size() - 1; i++) {

				Map<String, Object> rule = (Map<String, Object>) rules.get(i).get("main_rule");
				List<Map<String, Object>> evaluate_conditions = (List<Map<String, Object>>) rule.get("sub_rules");
				List<String> data = new ArrayList<String>();

				for (Map<String, Object> s : evaluate_conditions) {
					data.add(multipleRule(s, device, map, al_name));
					if (s.get("rule").toString().equals("&&")  || s.get("rule").toString().equals("||")) {
						data.add(s.get("rule").toString());
					}
					

				}
				String listString = "";
				
				for(i=0;i<=data.size()-1;i++) {
					
					if(i<data.size()-1) {
						listString += data.get(i) + " ";
					}else if(i==data.size()-1) {
						listString += data.get(i);
					}
					
				}

				System.out.println("rules in string ==> " + listString);
				
				System.out.println("rule ==> " + evaluate(listString));


			}

		}

	}

	public void singleCondition(Map<String, Object> s, Device device, Map<String, Object> map, String al_name) {

		String alarm_parameter = (String) s.get("alarm_parameter");
		String alarm_condition = (String) s.get("alarm_condition");
		String alarm_value = (String) s.get("alarm_value");
		String alarm_notify = (String) s.get("alarm_notify");
		String data_type = (String) s.get("data_type");

		if (map.containsKey(alarm_parameter)) {

			if (data_type.toLowerCase().equals("text")) {

				String data = (String) map.get(alarm_parameter);

				switch (alarm_condition) {
				case "==":
					if (data.toLowerCase().equals(alarm_value.toLowerCase())) {

						System.out.println("Equals condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);
					}
					break;
				case "!=":
					if (data.toLowerCase() != alarm_value.toLowerCase()) {
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);
						System.out.println("Not Equals condition satisfies");
					}
					break;
				}

			} else if (data_type.toLowerCase().equals("number")) {

				float data = Float.parseFloat((String) map.get(alarm_parameter));

				float alarm_val = Float.parseFloat(alarm_value);

				switch (alarm_condition) {
				case ">":
					if (data > alarm_val) {

						System.out.println("> condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);
					}
					break;
				case ">=":
					if (data >= alarm_val) {

						System.out.println(">= condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);

					}
					break;
				case "<":
					if (data < alarm_val) {

						System.out.println("Equals condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);

					}
					break;
				case "<=":
					if (data <= alarm_val) {

						System.out.println("<= condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);

					}
					break;
				case "==":
					if (data == alarm_val) {

						System.out.println("Equals condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);

					}
					break;
				case "!=":
					if (data != alarm_val) {

						System.out.println("!= condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, alarm_parameter, alarm_condition,
								alarm_value);

					}
					break;
				}

			}

		} else {
			System.out.println("alarm parameter is not present in map");
		}

	}

	public String multipleRule(Map<String, Object> s, Device device, Map<String, Object> map, String al_name) {

		boolean isEqual = false;

		String alarm_parameter = (String) s.get("alarm_parameter");
		String alarm_condition = (String) s.get("alarm_condition");
		String alarm_value = (String) s.get("alarm_value");
		String alarm_notify = (String) s.get("alarm_notify");
		String data_type = (String) s.get("data_type");

		if (map.containsKey(alarm_parameter)) {

			if (data_type.toLowerCase().equals("text")) {

				String data = (String) map.get(alarm_parameter);

				switch (alarm_condition) {
				case "==":
					if (data.toLowerCase().equals(alarm_value.toLowerCase())) {

						System.out.println("Equals condition satisfies");
						isEqual = true;
					}
					break;
				case "!=":
					if (data.toLowerCase() != alarm_value.toLowerCase()) {
						System.out.println("Not Equals condition satisfies");
						isEqual = true;
					}
					break;
				}

			} else if (data_type.toLowerCase().equals("number")) {

				float data = Float.parseFloat((String) map.get(alarm_parameter));

				float alarm_val = Float.parseFloat(alarm_value);

				switch (alarm_condition) {
				case ">":
					if (data > alarm_val) {

						System.out.println("> condition satisfies");
						isEqual = true;
					}
					break;
				case ">=":
					if (data >= alarm_val) {

						System.out.println(">= condition satisfies");
						isEqual = true;

					}
					break;
				case "<":
					if (data < alarm_val) {

						System.out.println("< condition satisfies");
						isEqual = true;

					}
					break;
				case "<=":
					if (data <= alarm_val) {

						System.out.println("<= condition satisfies");
						isEqual = true;

					}
					break;
				case "==":
					if (data == alarm_val) {

						System.out.println("Equals condition satisfies");
						isEqual = true;

					}
					break;
				case "!=":
					if (data != alarm_val) {

						System.out.println("!= condition satisfies");
						isEqual = true;

					}
					break;
				}

			}

		} else {
			System.out.println("alarm parameter is not present in map");
		}
		return Boolean.toString(isEqual);

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
				}

			} else {

				System.out.println("Device id dosent match with device table");
				logger.info("Device id dosent match with device table");
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
					triggerAlarm(device, map);

				} else {

					RawData optMismatch = optRaw.get();
					ArrayList<String> add_mismatch_data;
					add_mismatch_data = optMismatch.getMismatch_dataset();
					add_mismatch_data.add(input);
					rawdata.setMismatch_dataset(add_mismatch_data);

					addRawData(raw_tbl_id, rawdata);

					System.out.println("Data format mismatch");
					logger.info("Data format mismatch");
				}

			} else {
				System.out.println("Device id dosent match with raw data id");
				logger.info("Device id dosent match with raw data id");
			}

			object.put("msg", "success");
			return object;

		} else {
			System.out.println("device id is not present in raw data or data format mismatch");
			logger.info("device id is not present in raw data or data format mismatch");
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
		return deviceData(output.toString());
	}

	public Map<String, String> receiveData(String string) {
		Map<String, String> map = new HashMap<>();

		if (isJSONValid(string)) {

			deviceDataJSON(string);
			map.put("message", "JSON");

		} else if (isValidHex(string)) {
			map.put("message", "HEX");
			return hexToAscii(string);

		} else if (isValidCSV(string)) {
			map.put("message", "CSV");
			return deviceData(string);

		} else {
			map.put("message", "not matched");
		}
		return map;
	}

	public boolean isJSON(String jsonInString) {

		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonInString);
			System.out.println(mapper.readTree(jsonInString));

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean isValidHex(String string) {
		StringBuilder output = new StringBuilder("");
		try {
			for (int i = 0; i < string.length(); i += 2) {
				String str = string.substring(i, i + 2);
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

	public boolean isHex(String cadena) {
		if (cadena.length() == 0 || (cadena.charAt(0) != '-' && Character.digit(cadena.charAt(0), 16) == -1))
			return false;
		if (cadena.length() == 1 && cadena.charAt(0) == '-')
			return false;

		for (int i = 1; i < cadena.length(); i++)
			if (Character.digit(cadena.charAt(i), 16) == -1)
				return false;
		return true;
	}

	public boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
			System.out.println("JSON");
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
				System.out.println("JSON ARRAY");
			} catch (JSONException ex1) {
				System.out.println("! JSON");
				return false;
			}
		}
		return true;
	}

	public boolean isValidJson(String json) {
		try {
			new JSONObject(json);
			System.out.println("JSON");

		} catch (JSONException e) {
			System.out.println(" ! JSON");
			return false;
		}

		return true;

	}

	public boolean isValidCSVV(String string) {
		String regex = "^(\\\\d)+,[A-Za-z]+(,[A-Za-z]+=[A-Za-z0-9{};]+)+$";
		return Pattern.matches(regex, string);
	}

	// A method to evaluate the input string using stacks
	public static boolean evaluate(String input) {
		// Create two stacks to store operators and operands
		Stack<String> opStack = new Stack<>();
		Stack<Boolean> valStack = new Stack<>();

		// Split the input string by spaces
		String[] tokens = input.split(" ");

		// Loop through the tokens
		for (String token : tokens) {
			// If the token is an operator, push it to the opStack
			if (token.equals("&&") || token.equals("||")) {
				opStack.push(token);
			}
			// If the token is an operand, parse it as a boolean and push it to the valStack
			else {
				boolean value = Boolean.parseBoolean(token);
				valStack.push(value);
			}
			// If both stacks have at least two elements, pop and evaluate them
			while (opStack.size() >= 1 && valStack.size() >= 2) {
				String op = opStack.pop();
				boolean val1 = valStack.pop();
				boolean val2 = valStack.pop();
				boolean res = apply(op, val1, val2);
				valStack.push(res);
			}
		}
		// Return the final result from the valStack
		return valStack.pop();
	}

	// A helper method to apply a boolean operator to two operands and return the
	// result

	public static boolean apply(String op, boolean val1, boolean val2) {
		if (op.equals("&&")) {
			return val1 && val2;
		} else if (op.equals("||")) {
			return val1 || val2;
		} else {
			return false;
		}
	}

}
