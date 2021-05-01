package com.example.iwwof;

import com.example.iwwof.models.ERole;
import com.example.iwwof.models.Role;
import com.example.iwwof.models.User;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository){
		return args -> {
			Optional<Role> resultAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
			Role adminRole = resultAdmin.orElse(null);
			if (adminRole == null) {
				adminRole = new Role();
				adminRole.setName(ERole.ROLE_ADMIN);
				roleRepository.save(adminRole);
				System.out.println("Create admin");
			}

			Optional<Role> resultUser = roleRepository.findByName(ERole.ROLE_USER);
			Role userRole = resultUser.orElse(null);
			if (userRole == null) {
				userRole = new Role();
				userRole.setName(ERole.ROLE_USER);
				roleRepository.save(userRole);
				System.out.println("Create user");
			}

			Boolean adminExist = userRepository.existsByUsername("admin@gmail.com");
			if (!adminExist) {
				User user = new User();
				user.setRoles(Collections.singletonList(adminRole));
				user.setAllowByAdmin(true);
				user.setName("admin");
				user.setPassword("$2y$12$qGgIlZs8d8xxnX/zmHhQEO4ewPrIZ2/k6Og3/zD7v8yxCJLbiVIXe");
				user.setPhoneNumber("0123456789");
				user.setLocation("Finland");
				user.setWebsite("http://iwwof.com/");
				user.setUsername("admin@gmail.com");
				userRepository.save(user);
				System.out.println("Creat admin user");
			}
		};
	}
}
