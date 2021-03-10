package com.example.iwwof.controllers;

import com.example.iwwof.models.Business;
import com.example.iwwof.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Business> getAllBusiness(){
        return businessService.getAllBusiness();
    }

    @GetMapping("/get/category/{category}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Business> getBusinessesByCategory(@PathVariable String category){
        return businessService.getBusinessesByCategory(category);
    }

    @GetMapping("/get/location/{location}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Business> getBusinessesByLocation(@PathVariable String location){
        return businessService.getBusinessesByLocation(location);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String addBusiness(@RequestBody Business business){
        return businessService.saveBusiness(business);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String removeBusiness(@PathVariable Long id){
        return businessService.deleteBusinessById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String updateBusiness(@RequestBody Business business){
        return businessService.updateBusiness(business);
    }

}