package com.GYM.GYM.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
