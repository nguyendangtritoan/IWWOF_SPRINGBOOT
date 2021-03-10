package com.example.iwwof.service;

import com.example.iwwof.models.User;
import com.example.iwwof.repository.BusinessRepository;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminService(BusinessRepository businessRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String letUserRegister(Long id){
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
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return "no user with id: "+id;
        }
        userRepository.deleteById(id);
        return "success";
    }
}
