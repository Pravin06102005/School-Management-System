package com.GYM.GYM.service;

import com.GYM.GYM.dto.MembershipPlanDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MembershipPlanService {

    MembershipPlanDto addPlan(MembershipPlanDto dto, MultipartFile image) throws IOException;
    MembershipPlanDto updatePlan(Long id, MembershipPlanDto dto, MultipartFile image) throws IOException;
    void deletePlan(Long id);
    List<MembershipPlanDto> getAllPlans();
    MembershipPlanDto getPlanById(Long id);
}
