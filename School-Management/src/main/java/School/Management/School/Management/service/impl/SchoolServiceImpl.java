package School.Management.School.Management.service.impl;

import School.Management.School.Management.dto.SchoolDto;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.service.SchoolService;
import School.Management.School.Management.util.AdminUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final AdminUtil  adminUtil;
    private final ModelMapper modelMapper;



    @Override
    public SchoolDto getById(UUID id) {
        School s=schoolRepository.findById(id).orElseThrow(()->new RuntimeException("School not found"));
        return  modelMapper.map(s,SchoolDto.class);
    }

    @Override
    public SchoolDto getMySchool() {
        UUID schoolId=adminUtil.getSchoolId();
        return getById(schoolId);
    }

    @Override
    public SchoolDto update(SchoolDto dto) {

        // Fetch school ID from JWT
        UUID schoolId = adminUtil.getSchoolId();

        // Fetch school linked with admin
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // Update only name
        school.setName(dto.getName());

        // Save
        school = schoolRepository.save(school);

        return modelMapper.map(school, SchoolDto.class);
    }


    @Override
    public void delete(UUID id) {
        if(!id.equals(adminUtil.getSchoolId()))throw  new IllegalArgumentException("Not Allowed");
        schoolRepository.deleteById(id);

    }
}
