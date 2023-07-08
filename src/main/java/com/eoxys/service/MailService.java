package com.eoxys.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.eoxys.model.AlarmEventList;
import com.eoxys.model.Notification;
import com.eoxys.model.Organization;
import com.eoxys.repository.AlarmEventListRepository;
import com.eoxys.repository.NotificationRepository;
import com.eoxys.utils.EmailResponse;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
@Service
public class MailService {
	
    
	@Value("${spring.mail.host}")
	private String mail_host;
	@Value("${spring.mail.port}")
	private int mail_port;
	@Value("${spring.mail.username}")
	private String mail_username;
	@Value("${spring.mail.password}")
	private String mail_password;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;
	@Value("${spring.mail.properties.mail.smtp.ssl.enable}")
	private boolean ssl;

	@Autowired
	Configuration configuration;

	@Autowired
	NotificationRepository econfigRepo;

	@Autowired
	private JavaMailSender emailSender;

	public MailService(Configuration configuration) {

		this.configuration = configuration;
	}

	public String getEmailContent(String username, String alert_message)
			throws IOException, TemplateException, JSONException {
		JSONObject bodydata = new JSONObject();
		StringWriter stringWriter = new StringWriter();
		List<Notification> emailconfig = econfigRepo.findAll();
		String email_content = (String) emailconfig.get(emailconfig.size() - 1).getNotification_receiver()
				.getSelected_email().get("content");
		email_content = email_content.replace("$alertMessage", alert_message);
		bodydata.put("email", email_content);
		Map<String, Object> model = new HashMap<>();
		model.put("content", bodydata.get("email"));
		model.put("username", username);
		configuration.getTemplate("emailformat.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	public List<String> getValuesForGivenKey(List<String> jsonArrayStr, String key) {
		JSONArray jsonArray = new JSONArray(jsonArrayStr);
		return IntStream.range(0, jsonArray.length())
				.mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key)).collect(Collectors.toList());
	}

	public Map<String, String> sendEmail() throws JSONException, IOException, TemplateException {

		Map<String, String> object = new HashMap<>();

		List<Notification> emailconfig = econfigRepo.findAll();
		Notification notification = emailconfig.get(emailconfig.size() - 1);
		String body_content = notification.getNotification_message();
		String contact_email = null;
		String notification_email = null;

		contact_email = notification.getNotification_receiver().getContact_person().get("email").toString();
		notification_email = notification.getNotification_receiver().getNotification_profile().get("email").toString();

		System.out.println("notification_email ==> " + notification_email);
		System.out.println("contact_email ==> " + contact_email);

		List<String> contact_additional_email = null;
		List<String> notification_additional_email = null;

		contact_additional_email = (List<String>) notification.getNotification_receiver().getAdditional_contacts()
				.get(0).get("contact_person");
		notification_additional_email = (List<String>) notification.getNotification_receiver().getAdditional_contacts()
				.get(1).get("notification_profile");

		System.out.println("contact_additional_email ==> " + contact_additional_email);
		System.out.println("notification_additional_email ==> " + notification_additional_email);

		List<String> contact = null;
		List<String> notification1 = null;

		contact = getValuesForGivenKey(contact_additional_email, "email");
		notification1 = getValuesForGivenKey(notification_additional_email, "email");

		System.out.println("contact ==> " + contact);
		System.out.println("notification 1 ==> " + notification1);

		if (contact_email != null) {

			System.out.println("contact_email block");

//			emailService(notification, contact_email, body_content);

		}

		if (notification_email != null) {

			System.out.println("notification_email block");

//			emailService(notification, notification_email, body_content);

		}

		if (contact != null) {

			System.out.println("contact if block");

			for (String email_addr : contact) {

				System.out.println("For loooooop  ===>  " + email_addr);

//				emailService(notification, email_addr, body_content);
			}

		}

		if (notification1 != null) {

			System.out.println("notification1 if block");

			for (String email_addr : notification1) {

				System.out.println("For loooooop  ===>  " + email_addr);

//				emailService(notification, email_addr, body_content);

			}

		}

		object.put("msg", "message not sent successfully ==> condition is not satisfied in if block");
		return object;

	}
	
	
	
	
	
	
	
	
	public Map<String, String> sendAlertEmail() throws JSONException, IOException, TemplateException {

		Map<String, String> object = new HashMap<>();

		List<Notification> emailconfig = econfigRepo.findAll();
		Notification notification = emailconfig.get(emailconfig.size() - 1);
		String body_content = notification.getNotification_message();
		String contact_email = null;
		String subject = null;
		contact_email = notification.getUser_id().getEmail();
		subject = notification.getSubject();
		System.out.println("contact_email ==> " + contact_email);
		
		if (contact_email != null) {


			emailService(notification, contact_email,subject, body_content);
//			triggerMail(notification, contact_email, body_content);
			object.put("msg", "message sent successfully");
			

		}else {
			object.put("msg", "message not sent successfully");
			
		}
		
		return object;

		

	}

	public void emailService(Notification notification, String contact_email,String subject, String body_content) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		if (notification.getNotification_receiver().getEmail_configuration() != null) {

			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

			String host = (String) notification.getNotification_receiver().getEmail_configuration().get("host");
			String port_string = (String) notification.getNotification_receiver().getEmail_configuration().get("port");
			int port = Integer.parseInt(port_string);
			String username = (String) notification.getNotification_receiver().getEmail_configuration().get("username");
			String password = (String) notification.getNotification_receiver().getEmail_configuration().get("password");

			System.out.println("host --> " + host + " port --> " + port + " username --> " + username + " password --> "
					+ password);

			

			mailSender.setHost(host);
			mailSender.setPort(port);
			mailSender.setUsername(username);
			mailSender.setPassword(password);
			
			Properties pros = new Properties();
			pros.put("mail.smtp.auth", true);
//			pros.put("mail.smtp.timeout", 25000);
//			pros.put("mail.smtp.port", 25);
//			pros.put("mail.smtp.socketFactory.port", port);
//			pros.put("mail.smtp.socketFactory.fallback", false);
//			pros.put("mail.smtp.ssl.enable", true);
			pros.put("mail.smtp.starttls.enable", true);

			mailMessage.setFrom(username);
			mailMessage.setTo(contact_email);
			mailMessage.setText(body_content);
			mailMessage.setSubject(subject);
			
			
			mailSender.setJavaMailProperties(pros);
			
			if(emailValidator(contact_email)) {
				System.out.println("success");
//				mailSender.send(mailMessage);
				
				
			}else {
				System.out.println("falied");
			}

			

		} else {

			mailMessage.setFrom("iot@hubeoxys.com");
			mailMessage.setTo(contact_email);
			mailMessage.setText(body_content);
			mailMessage.setSubject(subject);

			
			
			if(emailValidator(contact_email)) {
//				emailSender.send(mailMessage);
				System.out.println("success");
			}else {
				System.out.println("falied");
			}

		}

	}
	
	public boolean emailValidator(final String email) {
        boolean isValid = false;
        try {
            final InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        }
        catch (AddressException e) {
            System.out.println("You are in catch block -- Exception Occurred for: " + email);
        }
        return isValid;
    }
	
	
	private String triggerMail(Notification notification, String contact_email, String body_content) {
		String host  = "";
		String port_string="";
		int port;
		String username = "";
		String password = "";
		Properties pros = new Properties();
		
		if (notification.getNotification_receiver().getEmail_configuration() != null) {
			
			host = (String) notification.getNotification_receiver().getEmail_configuration().get("host");
			port_string = (String) notification.getNotification_receiver().getEmail_configuration().get("port");
			port = Integer.parseInt(port_string);
			username = (String) notification.getNotification_receiver().getEmail_configuration().get("username");
			password = (String) notification.getNotification_receiver().getEmail_configuration().get("password");
			pros.put("mail.smtp.auth", true);
			pros.put("mail.smtp.starttls.enable", true);
			
		}else {
			host = mail_host;
			port = mail_port;
			username = mail_username;
			password = mail_password;
			
			pros.put("mail.smtp.auth", true);
			pros.put("mail.smtp.starttls.enable", true);
		}
		
		final String username_final = username;
		final String password_final = password;
		System.out.println("username_final"+username_final);
		System.out.println("password_final"+password_final);
		
        String eMsg = "";
        final Session session = Session.getInstance(pros, (Authenticator)new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username_final,password_final);
            }
        }); 
        System.out.println("Password Verified");
        try {
            System.out.println(contact_email);
            final Message message = (Message)new MimeMessage(session);
            message.setFrom((Address)new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(contact_email));
            final BodyPart messageBodyPart = (BodyPart)new MimeBodyPart();
            messageBodyPart.setContent((Object)body_content, "text/html");
            final Multipart multipart = (Multipart)new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
//            message.setSubject(subject);
            System.out.println("Wait Email is sending....");
            System.out.println("Email Sent Successfully!");
            eMsg = "Email Sent Successfully!";
        }
        catch (MessagingException e) {
            eMsg = "Error : " + e;
            System.out.println("Error --> "+e);
        }
        catch (Exception e2) {
            eMsg = "Error : " + e2;
            System.out.println("Error --> "+e2);
        }
        return eMsg;
    }


    

}
