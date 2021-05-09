package com.example.iwwof.controllers;

import com.example.iwwof.configuration.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
@RefreshScope
public class TestController {

	@Value("${db.dbProps.username}")
	private String host;

	@Autowired
	DatabaseConfiguration databaseConfiguration;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('ADMIN')")
	public String moderatorAccess() {
		return databaseConfiguration.getDbPropData("username");
	}


	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		Map<String, String> map = new HashMap<>();
		map.put("url","newURL");
		map.put("username","newUSERNAME");
		map.put("pass","newPASS");
		databaseConfiguration.setDbProps(map);

		return databaseConfiguration.getDbPropData("username");
	}
}
