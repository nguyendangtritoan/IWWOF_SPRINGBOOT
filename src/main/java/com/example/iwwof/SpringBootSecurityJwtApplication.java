package com.example.iwwof;

import com.example.iwwof.configuration.DatabaseConfiguration;
import com.example.iwwof.models.ERole;
import com.example.iwwof.models.Role;
import com.example.iwwof.models.User;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.*;

@SpringBootApplication
@EnableConfigurationProperties(DatabaseConfiguration.class)
public class SpringBootSecurityJwtApplication {

	@Autowired
	DatabaseConfiguration databaseConfiguration;

	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url(databaseConfiguration.getDbPropData("url"));
		dataSourceBuilder.username(databaseConfiguration.getDbPropData("username"));
		dataSourceBuilder.password(databaseConfiguration.getDbPropData("password"));
		return dataSourceBuilder.build();
	}

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

			Boolean adminExist = userRepository.existsByEmail("admin@gmail.com");
			if (!adminExist) {
				User user = new User();
				user.setEmail("iwwofcontact@gmail.com");
				user.setRoles(Collections.singletonList(adminRole));
				user.setAllowByAdmin(true);
				user.setName("admin");
				user.setPassword("$2y$12$qGgIlZs8d8xxnX/zmHhQEO4ewPrIZ2/k6Og3/zD7v8yxCJLbiVIXe");
				user.setPhoneNumber("0123456789");
				user.setLocation("Finland");
				user.setWebsite("http://iwwof.com/");
				user.setUsername("admin");
				userRepository.save(user);
				System.out.println("Creat admin user");
			}
		};
	}
}
