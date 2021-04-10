package com.example.iwwof;

import com.example.iwwof.payload.request.LoginRequest;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import com.example.iwwof.security.jwt.JwtUtils;
import com.example.iwwof.service.AuthService;
import com.example.iwwof.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringBootSecurityJwtApplicationTests {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private AuthService authService;

	@Autowired
	private MailService mailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Test
	public void contextLoads() {
		System.out.println(passwordEncoder.encode("Hi"));
		System.out.println(passwordEncoder.encode("Hi"));
		assertEquals(passwordEncoder.encode("Hi"), passwordEncoder.encode("Hi"));
	}

	@Test
	public void testJWTGenerate(){
		LoginRequest loginRequest1 = new LoginRequest();
		loginRequest1.setUsername("test1");
		loginRequest1.setPassword("12345678");

		LoginRequest loginRequest2 = new LoginRequest();
		loginRequest2.setUsername("test1");
		loginRequest2.setPassword("12345678");

		assertEquals(authService.authenticateUser(loginRequest1), authService.authenticateUser(loginRequest2));

	}

	@Test
	public void testEmailService(){
		System.out.println(mailService.sendMail("IWWOF","IWWOF","nguyendangtritoan2305@gmail.com","Hi"));
	}

}
