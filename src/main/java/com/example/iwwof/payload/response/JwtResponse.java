package com.example.iwwof.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String name;
	private String location;
	private String phoneNumber;
	private String website;
	private String otherContactInfo;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id,
					   String username,
					   String name,String location,
					   String phoneNumber, String website,
					   String otherContactInfo,
					   List<String> roles) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.name = name;
		this.location = location;
		this.phoneNumber = phoneNumber;
		this.website = website;
		this.otherContactInfo = otherContactInfo;
		this.roles = roles;
	}
}
