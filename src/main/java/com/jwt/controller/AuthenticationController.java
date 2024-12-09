package com.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.AuthenticationResponse;
import com.jwt.model.User;
import com.jwt.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	private final AuthenticationService authService;

	public AuthenticationController(AuthenticationService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody User request) {
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login (@RequestBody User request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	@GetMapping("/user")
	public ResponseEntity<String> user() {
		return ResponseEntity.ok("Hello form user url");
	}
	
	@GetMapping("/admin")
	public ResponseEntity<String> admin() {
		return ResponseEntity.ok("Hello form admin url");
	}
}
