package School.Management.School.Management.service.impl;


import School.Management.School.Management.dto.StandardCreateRequest;
import School.Management.School.Management.dto.StandardDto;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.entity.Standard;
import School.Management.School.Management.repo.DivisionRepository;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.repo.StandardRepository;
import School.Management.School.Management.service.StandardService;
import School.Management.School.Management.util.AdminUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService {

    private final StandardRepository standardRepository;
    private  final SchoolRepository schoolRepository;
    private final AdminUtil adminUtil;
    private final ModelMapper modelMapper;
    private final DivisionRepository divisionRepository;


    @Override
    public StandardDto create(StandardCreateRequest req) {
        UUID schoolId = adminUtil.getSchoolId();
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new RuntimeException("School not found"));

        Standard s = new Standard();
        s.setName(req.getName());
        s.setLevel(req.getLevel());
        s.setSchool(school);
        s = standardRepository.save(s);
        return modelMapper.map(s, StandardDto.class);
    }

    @Override
    public StandardDto getById(UUID id) {
        Standard s=standardRepository.findById(id).orElseThrow(()->new RuntimeException("Standard not found"));
        if (!s.getSchool().getId().equals(adminUtil.getSchoolId()))throw  new IllegalArgumentException("Not allowed");
        return modelMapper.map(s,StandardDto.class);
    }

    @Override
    public List<StandardDto> listAll() {
        UUID schoolId=adminUtil.getSchoolId();
        return standardRepository.findBySchoolIdOrderByLevel(schoolId).stream().map(s->modelMapper.map(s,StandardDto.class)).toList();
    }

    @Override
    public StandardDto update(UUID id, StandardCreateRequest req) {
        Standard s=standardRepository.findById(id).orElseThrow(()->new RuntimeException("Standard not found"));
        if (!s.getSchool().getId().equals(adminUtil.getSchoolId()))throw new IllegalArgumentException("not allowed");

        s.setName(req.getName());
        s.setLevel(req.getLevel());
        s=standardRepository.save(s);
        return modelMapper.map(s,StandardDto.class);
    }

    @Override
    public void delete(UUID id) {
        UUID schoolId = adminUtil.getSchoolId();
        Standard s=standardRepository.findById(id).orElseThrow(()->new RuntimeException("Standard nor found"));
        if (!s.getSchool().getId().equals(adminUtil.getSchoolId()))throw  new IllegalArgumentException("Not allowed");

        long count = divisionRepository.countByStandardId(id);
        if (count > 0) {
            throw new RuntimeException("Cannot delete. Divisions exist under this Standard.");
        }
        standardRepository.delete(s);
    }
}
