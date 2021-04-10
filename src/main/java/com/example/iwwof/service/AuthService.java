package com.example.iwwof.service;

import com.example.iwwof.models.ERole;
import com.example.iwwof.models.Role;
import com.example.iwwof.models.User;
import com.example.iwwof.payload.request.LoginRequest;
import com.example.iwwof.payload.request.SignupRequest;
import com.example.iwwof.payload.response.JwtResponse;
import com.example.iwwof.payload.response.MessageResponse;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import com.example.iwwof.security.jwt.JwtUtils;
import com.example.iwwof.security.services.UserDetailsImpl;
import com.example.iwwof.security.util.DefaultPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class AuthService {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MailService mailService;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Incorrect username or password"));
        }else if(!user.isAllowByAdmin()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Admin has not given permission"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                user.getName(),
                user.getLocation(),
                user.getPhoneNumber(),
                user.getWebsite(),
                user.getOtherContactInfo(),
                roles));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role admin is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role mod is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public String forgotPasswordHandler(String username, String email){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent())
            return "Username not found";
        else if(!user.get().getEmail().equals(email)){
            return "No user register with this email";
        }
        else {
            String newPassword = DefaultPasswordGenerator.generate(10);
            user.get().setPassword(encoder.encode(newPassword));
            userRepository.save(user.get());
            String body = "Your password at IWWOF page has been reset to "+newPassword+", please use this to update your own password.";
            return mailService.sendMail("IWWOF", "RESET IWWOF PASSWORD", email,body);
        }
    }
}
