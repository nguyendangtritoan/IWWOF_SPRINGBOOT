package com.example.iwwof.controllers;

import com.example.iwwof.models.Business;
import com.example.iwwof.models.User;
import com.example.iwwof.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String letUserRegister(@RequestBody User user){
        return adminService.letUserRegister(user.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String deleteUser(@RequestBody Long id){
        return adminService.deleteUser(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getPendingToAcceptUsers(){
        return adminService.getPendingToAcceptUsers();
    }

    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public User updateUser(@RequestBody User user){
        return adminService.updateUser(user);
    }


}
