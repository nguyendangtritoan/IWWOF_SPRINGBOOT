package com.example.iwwof.controllers;

import com.example.iwwof.models.Business;
import com.example.iwwof.models.User;
import com.example.iwwof.service.AdminService;
import com.example.iwwof.service.BusinessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {

    private final BusinessService businessService;
    private final AdminService adminService;

    public PublicController(BusinessService businessService, AdminService adminService) {
        this.businessService = businessService;
        this.adminService = adminService;
    }

    @GetMapping("/user/all")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @GetMapping("/business/all")
    public List<Business> getAllBusinesses(){
        return businessService.getAllBusiness();
    }

    @GetMapping("/business/category/{category}")
    public List<Business> getBusinessesByCategory(@PathVariable String category){
        return businessService.getBusinessesByCategory(category);
    }

    @GetMapping("/business/location/{location}")
    public List<Business> getBusinessesByLocation(@PathVariable String location){
        return businessService.getBusinessesByLocation(location);
    }
}
