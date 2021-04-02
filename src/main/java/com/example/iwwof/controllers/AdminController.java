package com.example.iwwof.controllers;

import com.example.iwwof.models.Business;
import com.example.iwwof.models.User;
import com.example.iwwof.service.AdminService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/approve/user")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String letUserRegister(@RequestBody User user){
        return adminService.letUserRegister(user.getId());
    }

    @DeleteMapping("/delete/user")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String deleteUser(@RequestBody User user){
        return adminService.deleteUser(user.getId());
    }

    @GetMapping("/get/pending")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getPendingToAcceptUsers(){
        return adminService.getPendingToAcceptUsers();
    }

    @PutMapping("/update/user")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public User updateUser(@RequestBody User user){
        return adminService.updateUser(user);
    }


}
