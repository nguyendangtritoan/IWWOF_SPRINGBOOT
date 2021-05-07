package com.example.iwwof.controllers;

import javax.validation.Valid;

import com.example.iwwof.payload.request.LoginRequest;
import com.example.iwwof.payload.request.SignupRequest;

import com.example.iwwof.service.AuthService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService){
		this.authService = authService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return authService.registerUser(signUpRequest);
	}

	@PostMapping("/forgotpassword")
	public String forgotPasswordHandler(@RequestParam String email){
		return authService.forgotPasswordHandler(email);
	}
}