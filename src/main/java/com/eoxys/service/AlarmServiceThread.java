package com.eoxys.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoxys.model.AlarmEventList;
import com.eoxys.model.Device;
import com.eoxys.model.Notification;
import com.eoxys.repository.AlarmEventListRepository;

import freemarker.template.TemplateException;

@Service
public class AlarmServiceThread implements Runnable {

	@Autowired
	private MailService mailService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AlarmEventListRepository alRepository;

	public AlarmServiceThread() {
		super();
		System.out.println("Initial thread count" + Thread.activeCount());
	}

	Device device = null;
	Map<String, Object> map = null;

	@Override
	public void run() {

		System.out.println("thread is running...");
		triggerAlarm();

	}

	public void setDevice(Device device, Map<String, Object> map) {
		this.device = device;
		this.map = map;
	}

	public void triggerAlarm() {

		List<Map<String, Object>> alarm_rules = (List<Map<String, Object>>) device.getDevice_category().get(0)
				.get("global_alarm_rule");

		for (int i = 0; i <= alarm_rules.size() - 1; ++i) {

			List<Map<String, Object>> alarm_rule = (List<Map<String, Object>>) alarm_rules.get(i).get("alarm_rules");
			String al_name = (String) alarm_rules.get(i).get("alarm_name");
			String alarm_notify = (String) alarm_rules.get(i).get("alarm_notify");
			if (alarm_rule.size() == 1) {
				Map<String, Object> single_condition = alarm_rule.get(0);

				singleCondition(single_condition, device, map, al_name,alarm_notify);

			} else if (alarm_rule.size() > 1) {

				List<String> data = new ArrayList<String>();
				String conditions = "";
				String value = "";
				int rule_length = 1;
				
				for (Map<String, Object> s : alarm_rule) {
					
					data.add(multipleRule(s, device, map, al_name));
					if (s.get("rule").toString().equals("&&") || s.get("rule").toString().equals("||")) {
						data.add(s.get("rule").toString());
						conditions += s.get("alarm_parameter") + " "
								+ s.get("alarm_condition") + " " + s.get("alarm_value")+" "+s.get("rule")+" " ;
						
						
					} else if (s.get("rule").toString().equals("")) {
						conditions += s.get("alarm_parameter") + " " + s.get("alarm_condition") + " "
								+ s.get("alarm_value");
					}
					
					
					
					if(rule_length !=alarm_rule.size()) {
						value += s.get("alarm_parameter") + " is " +map.get(s.get("alarm_parameter"))+ " , ";
						rule_length ++;
						
					}else if(rule_length == alarm_rule.size()) {
						value += s.get("alarm_parameter") + " is " +map.get(s.get("alarm_parameter"));
						rule_length = 1;
					}
					

				}
				String listString = "";

				for (i = 0; i <= data.size() - 1; i++) {

					if (i < data.size() - 1) {
						listString += data.get(i) + " ";
					} else if (i == data.size() - 1) {
						listString += data.get(i);
					}

				}

				System.out.println("rules in string ==> " + listString);

				System.out.println("conditions in string ==> " + conditions);

				System.out.println("rule ==> " + evaluate(listString));

				if (evaluate(listString)) {
					notificationAndAlarmEvent(device, alarm_notify, al_name, conditions,value);
				}

			}

		}

	}

	public void singleCondition(Map<String, Object> s, Device device, Map<String, Object> map, String al_name , String alarm_notify) {

		String alarm_parameter = (String) s.get("alarm_parameter");
		String alarm_condition = (String) s.get("alarm_condition");
		String alarm_value = (String) s.get("alarm_value");
		String data_type = (String) s.get("data_type");

		String condition = "";
		String value = "";
		condition += alarm_parameter + " " + alarm_condition + " " + alarm_value;
		

		if (map.containsKey(alarm_parameter)) {

			if (data_type.toLowerCase().equals("text")) {

				String data = (String) map.get(alarm_parameter);
				value += alarm_parameter +" is "+data;

				switch (alarm_condition) {
				case "==":
					if (data.toLowerCase().equals(alarm_value.toLowerCase())) {

						System.out.println("Equals condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);
					}
					break;
				case "!=":
					if (data.toLowerCase() != alarm_value.toLowerCase()) {
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);
						System.out.println("Not Equals condition satisfies");
					}
					break;
				}

			} else if (data_type.toLowerCase().equals("number")) {
				value += alarm_parameter +" is "+ map.get(alarm_parameter).toString();
				float data = Float.parseFloat((String) map.get(alarm_parameter));

				float alarm_val = Float.parseFloat(alarm_value);

				switch (alarm_condition) {
				case ">":
					if (data > alarm_val) {

						System.out.println("> condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);
					}
					break;
				case ">=":
					if (data >= alarm_val) {

						System.out.println(">= condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);

					}
					break;
				case "<":
					if (data < alarm_val) {

						System.out.println("Equals condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);

					}
					break;
				case "<=":
					if (data <= alarm_val) {

						System.out.println("<= condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);

					}
					break;
				case "==":
					if (data == alarm_val) {

						System.out.println("Equals condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);

					}
					break;
				case "!=":
					if (data != alarm_val) {

						System.out.println("!= condition satisfies");
						notificationAndAlarmEvent(device, alarm_notify, al_name, condition,value);

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

	public void notificationAndAlarmEvent(Device device, String alarm_notify, String al_name, String alarm_condition, String alarm_value) {

		AlarmEventList alarm_event_list = new AlarmEventList();
		Notification notification = new Notification();

		alarm_event_list.setAlarm_name(al_name);
		alarm_event_list.setDevice_name(device.getDevice_name());
		alarm_event_list.setDevice_mac_id(device.getDevice_mac_id());
//		alarm_event_list.setAlarm_parameter_name(alarm_parameter);
		alarm_event_list.setAlarm_condition(alarm_condition);
		alarm_event_list.setAlarm_value(alarm_value);
		alarm_event_list.setNotification(alarm_notify);
		alarm_event_list.setView_status(false);
		alRepository.save(alarm_event_list);

		if (device.getUser_id() != null) {
			if (device.getUser_id().getOrganization().getSelected_email() != null) {
				notification.setNotification_receiver(device.getUser_id().getOrganization());
				notification.setUser_id(device.getUser_id());
				notification.setDevice_mac_id(device.getDevice_mac_id());
				String notifymsg = device.getUser_id().getOrganization().getSelected_email().get("content").toString();
				notifymsg = notifymsg.replace("$alert_message", alarm_notify);
				notifymsg = notifymsg.replace("$device_id", device.getDevice_mac_id());
				notifymsg = notifymsg.replace("$value", alarm_value);
				notifymsg = notifymsg.replace("$username", device.getUser_id().getUsername());
				System.out.println("Notify msg ---> " + notifymsg);
				notification.setNotification_message(notifymsg);
				notification.setSubject(device.getUser_id().getOrganization().getSelected_email().get("subject").toString());
				notificationService.saveNotify(notification);

				try {
					mailService.sendAlertEmail();
				} catch (JSONException e) {
					System.out.println("JSONException ---> "+e);
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("IOException ---> "+e);
					e.printStackTrace();
				} catch (TemplateException e) {
					System.out.println("TemplateException ---> "+e);
					e.printStackTrace();
				} finally {
					System.out.println("Thread.activeCount()" + Thread.activeCount());
				}

			} else {
				System.out.println("Thread.activeCount --> " + Thread.activeCount());
			}
		}

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
