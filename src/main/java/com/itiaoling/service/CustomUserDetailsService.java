package com.itiaoling.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itiaoling.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserService userService;
	
	
	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s does not exists!", username));
		}
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), auth);
	}
	

}
