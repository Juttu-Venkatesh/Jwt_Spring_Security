package com.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import com.jwt.model.User;
import com.jwt.repository.UserRepository;

//import jakarta.transaction.Transactional;

@Service
public class UserDetailsImpl implements UserDetailsService {

	private final UserRepository repository;
	
	public UserDetailsImpl(UserRepository repository) {
		this.repository = repository;
	}
//	
//	 @Transactional
//	    public void saveUser(User user) {
//	        repository.save(user);
//	    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
