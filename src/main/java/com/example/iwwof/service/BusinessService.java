package com.example.iwwof.service;

import com.example.iwwof.models.Business;
import com.example.iwwof.models.User;
import com.example.iwwof.repository.BusinessRepository;
import com.example.iwwof.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public BusinessService(BusinessRepository businessRepository, UserRepository userRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
    }

    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }

    public List<Business> getBusinessesByCategory(String category){
        return businessRepository.findBusinessesByCategory(category);
    }

    public List<Business> getBusinessesByLocation(String location){
        return businessRepository.findBusinessesByLocation(location);
    }

    public List<Business> getBusinessesByUserId(Long id){
        return businessRepository.findBusinessesByUserId(id);
    }

    public String deleteBusinessById(Long id){

        Business business = businessRepository.findById(id).orElse(null);

        if (business == null) {
            return "no business with id: "+id;
        }else {
            business.getUser().removeBusiness(business);
        }

        businessRepository.delete(business);
        return "success";
    }

    public String saveBusiness(Business business){

        User user = userRepository.findById(business.getUser().getId()).orElse(null);

        if(user == null){
            return "user id not exist";
        }else {
            //business.setUser(user);
            user.addBusiness(business);
        }
        Business business1 = businessRepository.save(business);
        return "Create! "+business1.getId();
    }

    public String updateBusiness(Business business){
        Business existBusiness = businessRepository.findById(business.getId()).orElse(null);
        if(existBusiness != null) {
            existBusiness.setUser(business.getUser());
            existBusiness.setDescription(business.getDescription());
            existBusiness.setBrandName(business.getBrandName());
            existBusiness.setCategory(business.getCategory());
            existBusiness.setLocation(business.getLocation());
            existBusiness.setPublished(business.isPublished());
            existBusiness.setWebsites(business.getWebsites());
            existBusiness.setAdditionalOffer(business.getAdditionalOffer());
            businessRepository.save(existBusiness);
            return "success";
        }else {
            return "no business with id: "+business.getId();
        }
    }
}
