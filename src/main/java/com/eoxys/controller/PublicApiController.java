package com.eoxys.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eoxys.dto.AuthCode;
import com.eoxys.dto.PlatformAuthRequest;
import com.eoxys.dto.UserAuthRequest;
import com.eoxys.model.PlatformInfo;
import com.eoxys.model.Users;
import com.eoxys.repository.PlatformInfoRepository;
import com.eoxys.repository.UsersRepository;
import com.eoxys.security_config.JwtConfig;
import com.eoxys.service.RawDataService;
import com.eoxys.service.RawDataTest;
import com.eoxys.service.UserService;

@RestController
public class PublicApiController {

	private static final Logger logger = LoggerFactory.getLogger(PublicApiController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtConfig jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PlatformInfoRepository platformRepository;

	@Autowired
	private RawDataService rawdataService;
	

	@Autowired
	private UserService userservice;

	@PostMapping("/user_login")
	public AuthCode login(@RequestBody UserAuthRequest authRequest) {
		Optional<Users> optUser = usersRepository.findByUserEmail(authRequest.getUsername());
		AuthCode auth_code = new AuthCode();

		String authcode = generateAuthCode();
		if (optUser.isPresent()) {
			auth_code.setAuth_code(authcode);
			Users user = optUser.get();

			user.setAuth_code(authcode);
			user.setCode_challenge(authRequest.getCode_challenge());

			usersRepository.save(user);

			return auth_code;

		} else {
			auth_code.setAuth_code(authcode);
			Users user = new Users();

			user.setEmail(authRequest.getUsername());
			user.setPhone_number(authRequest.getPhone());
			user.setAccess_token(passwordEncoder.encode(authRequest.getAccess_token()));
			user.setUsername(authRequest.getName());
			user.setRoles(authRequest.getGroup());

			user.setAuth_code(authcode);
			user.setIsActive(true);
			user.setCode_challenge(authRequest.getCode_challenge());

			usersRepository.save(user);

			return auth_code;
		}

	}

	@PostMapping("/user_authorize")
	public Map<String, String> authorize(@RequestBody UserAuthRequest authRequest) {

		HashMap<String, String> response = new HashMap<>();
		Optional<Users> optUser = usersRepository.findByUserEmail(authRequest.getUsername());

		if (optUser.isPresent()) {
			Users user = optUser.get();

			String authcode = authRequest.getAuth_code();
			String user_auth_code = user.getAuth_code();
			String code_verifier = authRequest.getCode_verifier();
			String user_code_challenge = user.getCode_challenge();

			if (user_auth_code.equals(authcode)) {

				try {
					char[] code_challenge;
					Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
					SecretKeySpec secret_key = new SecretKeySpec("123456".getBytes("UTF-8"), "HmacSHA256");
					sha256_HMAC.init(secret_key);
					code_challenge = Hex.encode(sha256_HMAC.doFinal(code_verifier.getBytes("UTF-8")));
					String code_challenge_str = new String(code_challenge);

					if (user_code_challenge.equals(code_challenge_str)) {

						user.setAccess_token(passwordEncoder.encode(authRequest.getAccess_token()));

						usersRepository.save(user);

						logger.info("user authenticate api is hitted");
						logger.info(" user login credentials ==> " + authRequest);
						System.out.println("user authenticate");
						System.out.println(" user login credentials ==> " + authRequest);
						Authentication authentication = authenticationManager
								.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
										authRequest.getAccess_token()));
						if (authentication.isAuthenticated()) {
							logger.info("successfully authorized user");
							System.out.println("successfully authorized user");
							return jwtService.generateToken(authRequest.getUsername(), authRequest.getScope());
						} else {
							System.out.println("failed to authorize user");
							logger.info("failed to authorize user");
							response.put("message", "failed to authorize user");
							return response;
						}

					} else {
						response.put("message", "code_challenge dosent match");

						Users handleUser = optUser.get();
						usersRepository.deleteById(handleUser.getUser_id());

						return response;
					}

				} catch (Exception e) {

					return response;

				}

			} else {
				response.put("message", "authcode dosent match");
				return response;
			}

		} else {
			response.put("message", "user dosent exists !");
			return response;
		}
	}

	@EventListener
	public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent) {
		System.out.println("login success");
		System.out.println("This is success event : " + authorizedEvent.getAuthentication().getName());
	}

	@EventListener
	public void authFailedEventListener(AbstractAuthenticationFailureEvent authorizedEvent) {
		System.out.println("This is faliure event : " + authorizedEvent.getAuthentication().getName());
		String username = authorizedEvent.getAuthentication().getName();
		Optional<PlatformInfo> optPlatform = platformRepository.findByEmail(username);
		if (optPlatform.isPresent()) {
			PlatformInfo platform = optPlatform.get();
			platform.setFailed_login(platform.getFailed_login() + 1);
			platformRepository.save(platform);
			logger.info("failed to authorize");
			System.out.println("failed to authorize");

		}
		
		
	}

//	@PostMapping("/authenticate")
	@RequestMapping (value = "/authenticate", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	public Map<String, String> authenticateAndGetToken(@RequestBody PlatformAuthRequest authRequest) {
		logger.info("login credentials ==> " + authRequest);
		System.out.println("Authenticate");
		System.out.println("login credentials ==> " + authRequest);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLid(), authRequest.getPwd()));
		if (authentication.isAuthenticated()) {
			logger.info("successfully authorized");
			System.out.println("successfully authorized");
			Optional<PlatformInfo> optPlatform = platformRepository.findByEmail(authRequest.getLid());
			if (optPlatform.isPresent()) {
				PlatformInfo platform = optPlatform.get();
				platform.setLast_login(dateAndTime());
				platform.setSuccess_login(platform.getSuccess_login() + 1);
				platformRepository.save(platform);

			}
			return jwtService.generateToken(authRequest.getLid(), authRequest.getTid());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}

	}

	@PostMapping("/add_user")
	public ResponseEntity<Users> saveOrg(@RequestBody Users dev) {
		System.out.println("SaveDevice ==>" + dev);
		logger.info("save user ==>" + dev);
		return new ResponseEntity<Users>(userservice.addUser(dev), HttpStatus.CREATED);
	}

	@PostMapping("/http_device_data")
	public ResponseEntity<Map<String, String>> independentdeviceData(@RequestBody String string) {

		return new ResponseEntity<Map<String, String>>(rawdataService.deviceDataCSV(string), HttpStatus.OK);
	}

	
	@PostMapping("/raw_data_test")
	public ResponseEntity<Map<String, String>> Test(@RequestBody String string) {

		return new ResponseEntity<Map<String, String>>(rawdataService.receiveData(string), HttpStatus.OK);
	}

	private String generateAuthCode() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String auth_code = new String(array, Charset.forName("UTF-8"));
		char ch[] = auth_code.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			String hexString = Integer.toHexString(ch[i]);
			sb.append(hexString);
		}
		String result = sb.toString();
		System.out.println(result);
		return result;
	}

	public String dateAndTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		return formatter.format(date);
	}
}
