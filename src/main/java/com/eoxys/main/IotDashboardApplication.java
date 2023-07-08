package com.eoxys.main;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.eoxys.model.NotificationEmailConfig;
import com.eoxys.model.NotificationSmsConfig;
import com.eoxys.repository.NotificationEmailConfigRepository;
import com.eoxys.repository.NotificationSmsConfigRepository;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.eoxys")
@EntityScan(basePackages = "com.eoxys.model")
@EnableJpaRepositories(basePackages = { "com.eoxys" })
public class IotDashboardApplication {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
		corsConfiguration.setExposedHeaders(Arrays.asList("*"));
		corsConfiguration.setAllowedMethods(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(IotDashboardApplication.class, args);
		System.out.println("=========================================");
	}

	@Autowired
	private NotificationSmsConfigRepository smsRepo;

	@Autowired
	private NotificationEmailConfigRepository emailRepo;

	@PostConstruct
	public void postConstruct() {
		NotificationEmailConfig email = new NotificationEmailConfig();
		NotificationSmsConfig sms = new NotificationSmsConfig();

		if (emailRepo.count() == 0) {

			email.setCreated_by("Admin");
			email.setContent_name("default message");
			email.setSubject("Alert from device");
			email.setEmail_content("hii $username, \n your device $device_id has triggered an alert ---> $alert_message and the value is $value");
			emailRepo.save(email);
		}

		if (smsRepo.count() == 0) {

			sms.setCreated_by("Admin");
			sms.setContent_name("default message");
			sms.setSubject("Alert from device");
			sms.setSms_content("hii $username, \n your device $device_id has triggered an alert ---> $alert_message and the value is $value");
			smsRepo.save(sms);
		}

	}

}
