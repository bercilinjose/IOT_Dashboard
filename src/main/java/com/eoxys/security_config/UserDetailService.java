package com.eoxys.security_config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eoxys.model.Users;
import com.eoxys.repository.UsersRepository;
@Component
public class UserDetailService implements UserDetailsService {
	
	@Autowired
    private UsersRepository repository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userInfo = repository.findByUserEmail(username);
        
        return userInfo.map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

}
