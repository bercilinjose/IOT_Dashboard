package com.eoxys.security_config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eoxys.model.PlatformInfo;
import com.eoxys.model.Users;
import com.eoxys.repository.PlatformInfoRepository;
import com.eoxys.repository.UsersRepository;
@Component
public class PlatformDetailsService implements UserDetailsService {
	
	@Autowired
    private PlatformInfoRepository repository;
	
	@Autowired
    private UsersRepository urepository;
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Users> userInfo = urepository.findByUserEmail(username);
		
		Optional<PlatformInfo> platformInfo = repository.findByEmail(username);
        
		if(userInfo.isPresent()) {
			
			return userInfo.map(UserDetail::new)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			
		}else if (platformInfo.isPresent()) {
			return platformInfo.map(PlatformDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		}else {
			throw new UsernameNotFoundException("user not found " + username);
		}
        
        
        
        

    }
	
	

}
