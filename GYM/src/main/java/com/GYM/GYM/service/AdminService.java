package com.GYM.GYM.service;

import com.GYM.GYM.dto.AuthRequest;
import com.GYM.GYM.dto.AuthResponse;
import com.GYM.GYM.entity.Admin;

public interface AdminService {
    void registerAdmin(String username, String password);
    AuthResponse login(AuthRequest authRequest);
}
