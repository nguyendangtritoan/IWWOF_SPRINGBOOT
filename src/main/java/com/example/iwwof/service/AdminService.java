package com.example.iwwof.service;

import com.example.iwwof.models.User;
import com.example.iwwof.payload.request.LoginRequest;
import com.example.iwwof.repository.BusinessRepository;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AuthService authService;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AdminService(AuthService authService,BusinessRepository businessRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authService = authService;
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public String letUserRegister(Long id){
        if(id == null){
            return "id is null";
        }
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return "no user with id: "+id;
        }else{
            user.setAllowByAdmin(true);
            userRepository.save(user);
            return "success";
        }
    }

    public String deleteUser(Long id){
        System.out.println("Id: "+id);
        if(id == null){
            return "id is null";
        }
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return "no user with id: "+id;
        }
        System.out.println("User email: "+user.getEmail());
        userRepository.deleteById(id);
        return "success";
    }

    public User updateUser(User userUpdated){
        User user = userRepository.findById(userUpdated.getId()).orElse(null);
        if(user == null){
            return null;
        }
        user.setEmail(userUpdated.getEmail());
        user.setLocation(userUpdated.getLocation());
        user.setName(userUpdated.getName());
        user.setOtherContactInfo(userUpdated.getOtherContactInfo());
        user.setPhoneNumber(userUpdated.getPhoneNumber());
        user.setWebsite(userUpdated.getWebsite());

        return userRepository.save(user);

    }

    public String updatePassword(String username, String newpassword,String oldPassword){
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            return "can't find user with username";
        }

//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername(username);
//        loginRequest.setPassword(oldPassword);
//
//        ResponseEntity<?> responseEntity = authService.authenticateUser(loginRequest);
        Boolean isCorrectPassword = encoder.matches(oldPassword, user.getPassword());
        if(isCorrectPassword) {
            String encodePass = encoder.encode(newpassword);
            user.setPassword(encodePass);
            userRepository.save(user);
        }else {
            return "Wrong password";
        }
        return "Updated succesfully";

    }

    public List<User> getPendingToAcceptUsers(){
        return userRepository.findByAccess();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
