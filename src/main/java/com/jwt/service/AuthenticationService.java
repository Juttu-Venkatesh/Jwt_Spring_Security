package com.jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.model.AuthenticationResponse;
import com.jwt.model.User;
import com.jwt.repository.UserRepository;

@Service
public class AuthenticationService {
	
	private final UserRepository repository;
	
	private PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	
	public AuthenticationService(UserRepository repository, 
			                     PasswordEncoder passwordEncoder, 
			                     JwtService jwtService,
			                     AuthenticationManager authenticationManager) 
	{
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}


	public AuthenticationResponse register(User request) {
		User user = new User();
		user.setId(request.getId());
		user.setUserName(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		
		user = repository.save(user);
		
		String token = jwtService.generateToken(user);
		
		return new AuthenticationResponse(token);
	}
	
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
						)
	    );
		
		User user = repository.findByUserName(request.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user);
	
		return new AuthenticationResponse(token);
	}
}
