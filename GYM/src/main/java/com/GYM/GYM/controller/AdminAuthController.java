package com.GYM.GYM.controller;


import com.GYM.GYM.dto.AuthRequest;
import com.GYM.GYM.dto.AuthResponse;
import com.GYM.GYM.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminAuthController {

    private final AdminService adminService;

    // Register a new admin
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest){
        adminService.registerAdmin(authRequest.getUsername(),authRequest.getPassword());
        return ResponseEntity.ok("Admin registered successfully!");
    }

    // Login admin
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        AuthResponse response = adminService.login(authRequest);
        return ResponseEntity.ok(response);

    }
}
