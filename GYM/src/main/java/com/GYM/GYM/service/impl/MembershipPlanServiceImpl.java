package com.GYM.GYM.service.impl;


import com.GYM.GYM.dto.MembershipPlanDto;
import com.GYM.GYM.entity.MembershipPlan;
import com.GYM.GYM.repository.MembershipPlanRepository;
import com.GYM.GYM.service.MembershipPlanService;
import com.GYM.GYM.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;
    private final ModelMapper modelMapper;
    private final FileUploadUtil fileUploadUtil;

    @Override
    public MembershipPlanDto addPlan(MembershipPlanDto dto, MultipartFile image) throws IOException {
        MembershipPlan plan=modelMapper.map(dto,MembershipPlan.class);
        plan.setImagePath(fileUploadUtil.saveFile(image));
        MembershipPlan saved=membershipPlanRepository.save(plan);
        return modelMapper.map(saved,MembershipPlanDto.class);
    }

    @Override
    public MembershipPlanDto updatePlan(Long id, MembershipPlanDto dto, MultipartFile image) throws IOException {
        MembershipPlan plan=membershipPlanRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Plan not found"));

        plan.setName(dto.getName());
        plan.setPrice(dto.getPrice());
        plan.setDescription(dto.getDescription());
        plan.setDurationInDays(dto.getDurationInDays());

        if (image !=null || !image.isEmpty()){
            fileUploadUtil.deleteFile(plan.getImagePath());
            plan.setImagePath(fileUploadUtil.saveFile(image));
        }

        MembershipPlan updated=membershipPlanRepository.save(plan);
        return modelMapper.map(updated,MembershipPlanDto.class);
    }

    @Override
    public void deletePlan(Long id) {

        membershipPlanRepository.findById(id).ifPresent(plan->{
            try {
                fileUploadUtil.deleteFile(plan.getImagePath());
            }catch (IOException ignored){}
            membershipPlanRepository.delete(plan);
        });
    }

    @Override
    public List<MembershipPlanDto> getAllPlans() {
        return membershipPlanRepository.findAll()
                .stream()
                .map(membershipPlan -> modelMapper.map(membershipPlan, MembershipPlanDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MembershipPlanDto getPlanById(Long id) {
        MembershipPlan membershipPlan=membershipPlanRepository.findById(id).orElseThrow(()->new RuntimeException("Plan not found"));
                return modelMapper.map(membershipPlan,MembershipPlanDto.class);

    }
}
