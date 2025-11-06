package com.GYM.GYM.controller;


import com.GYM.GYM.dto.MembershipPlanDto;
import com.GYM.GYM.service.MembershipPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/membership")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MembershipPlanController {


    private final MembershipPlanService membershipPlanService;


    @PostMapping
    public ResponseEntity<MembershipPlanDto> createPlan(
            @RequestPart("plan") @Valid MembershipPlanDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(membershipPlanService.addPlan(dto, image));
    }


    @GetMapping
    public ResponseEntity<List<MembershipPlanDto>> getAllPlans() {
        return ResponseEntity.ok(membershipPlanService.getAllPlans());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MembershipPlanDto> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(membershipPlanService.getPlanById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MembershipPlanDto> updatePlan(
            @PathVariable Long id,
            @RequestPart("plan") @Valid MembershipPlanDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(membershipPlanService.updatePlan(id, dto, image));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) {
        membershipPlanService.deletePlan(id);
        return ResponseEntity.ok("Membership plan deleted successfully.");
    }
}
