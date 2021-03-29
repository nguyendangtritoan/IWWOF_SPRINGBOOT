package com.example.iwwof.service;

import com.example.iwwof.models.User;
import com.example.iwwof.repository.BusinessRepository;
import com.example.iwwof.repository.RoleRepository;
import com.example.iwwof.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> getPendingToAcceptUsers(){
        return userRepository.findByAccess();
    }
}
