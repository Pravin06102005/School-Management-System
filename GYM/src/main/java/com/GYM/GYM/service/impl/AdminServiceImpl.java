package com.GYM.GYM.service.impl;

import com.GYM.GYM.dto.AuthRequest;
import com.GYM.GYM.dto.AuthResponse;
import com.GYM.GYM.entity.Admin;
import com.GYM.GYM.repository.AdminRepository;
import com.GYM.GYM.service.AdminService;
import com.GYM.GYM.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl  implements AdminService {


    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void registerAdmin(String username, String password) {

        if (adminRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        Admin admin=Admin.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // ✅ encrypt password
                .role("ROLE_ADMIN") // ✅ set default role
                .build();

        adminRepository.save(admin);

    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Admin admin=adminRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(()->new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(authRequest.getPassword(),admin.getPassword())){
            throw new RuntimeException("Invaild username and password");
        }

        String token=jwtUtil.generateToken(admin.getUsername(),admin.getRole());
        return new AuthResponse(token);
    }
}
