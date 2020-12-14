package org.iesalixar.poriad.security.service;

import org.iesalixar.poriad.security.entity.UserMain;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserSnowy user = userService.getByUsername(username).get();
		return UserMain.build(user);
	}

}
