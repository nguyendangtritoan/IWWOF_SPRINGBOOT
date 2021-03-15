package com.example.iwwof.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Setter
@Getter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@NotBlank
	@Size(max = 120)
	private String name;

	@NotBlank
	@Size(max = 120)
	private String location;

	@NotBlank
	@Size(max = 120)
	private String phoneNumber;

	@NotBlank
	@Size(max = 120)
	private String website;

	@NotBlank
	@Size(max = 120)
	private String otherContactInfo;

	private boolean isAllowByAdmin;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles = new HashSet<>();


	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Business> businesses = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
				@NotBlank @Size(max = 120) String password, @NotBlank @Size(max = 120) String name,
				@NotBlank @Size(max = 120) String location, @NotBlank @Size(max = 120) String phoneNumber,
				@NotBlank @Size(max = 120) String website, @NotBlank @Size(max = 120) String otherContactInfo, boolean isAllowByAdmin) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.location = location;
		this.phoneNumber = phoneNumber;
		this.website = website;
		this.otherContactInfo = otherContactInfo;
		this.isAllowByAdmin = isAllowByAdmin;
	}

	public void addBusiness(Business business){
		businesses.add(business);
		business.setUser(this);
	}

	public void removeBusiness(Business business){
		businesses.remove(business);
		business.setUser(null);
	}
}
