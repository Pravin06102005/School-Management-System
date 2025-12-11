package School.Management.School.Management.service.impl;

import School.Management.School.Management.dto.DivisionDto;
import School.Management.School.Management.entity.Division;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.entity.Standard;
import School.Management.School.Management.repo.DivisionRepository;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.repo.StandardRepository;
import School.Management.School.Management.service.DivisionService;
import School.Management.School.Management.util.AdminUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;
    private final StandardRepository standardRepository;
    private final SchoolRepository schoolRepository;
    private final AdminUtil adminUtil;
    private final ModelMapper modelMapper;

    // -----------------------------------
    // CREATE
    // -----------------------------------
    @Override
    public DivisionDto create(DivisionDto req) {

        UUID schoolId = adminUtil.getSchoolId();
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        Standard std = standardRepository.findById(req.getStandardId())
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        if (!std.getSchool().getId().equals(schoolId))
            throw new IllegalArgumentException("Standard not in your school");

        Division d = new Division();
        d.setName(req.getName());
        d.setStandard(std);
        d.setSchool(school);

        d = divisionRepository.save(d);

        DivisionDto dto = modelMapper.map(d, DivisionDto.class);
        dto.setStandardId(std.getId());
        dto.setStandardName(std.getName());

        return dto;
    }

    // -----------------------------------
    // LIST ALL
    // -----------------------------------
    @Override
    public List<DivisionDto> getAll() {
        UUID schoolId = adminUtil.getSchoolId();

        return divisionRepository.findBySchoolId(schoolId)
                .stream()
                .map(d -> {
                    DivisionDto dto = modelMapper.map(d, DivisionDto.class);
                    dto.setStandardId(d.getStandard().getId());
                    dto.setStandardName(d.getStandard().getName());
                    return dto;
                })
                .toList();
    }

    // -----------------------------------
    // GET BY ID
    // -----------------------------------
    @Override
    public DivisionDto getById(UUID id) {

        Division d = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found"));

        if (!d.getSchool().getId().equals(adminUtil.getSchoolId()))
            throw new IllegalArgumentException("Not allowed");

        DivisionDto dto = modelMapper.map(d, DivisionDto.class);
        dto.setStandardId(d.getStandard().getId());
        dto.setStandardName(d.getStandard().getName());

        return dto;
    }

    // -----------------------------------
    // UPDATE
    // -----------------------------------
    @Override
    public DivisionDto update(UUID id, DivisionDto req) {

        Division d = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found"));

        if (!d.getSchool().getId().equals(adminUtil.getSchoolId()))
            throw new IllegalArgumentException("Not allowed");

        d.setName(req.getName());

        d = divisionRepository.save(d);

        DivisionDto dto = modelMapper.map(d, DivisionDto.class);
        dto.setStandardId(d.getStandard().getId());
        dto.setStandardName(d.getStandard().getName());

        return dto;
    }

    // -----------------------------------
    // LIST BY STANDARD
    // -----------------------------------
    @Override
    public List<DivisionDto> listByStandard(UUID standardId) {
        UUID schoolId = adminUtil.getSchoolId();

        return divisionRepository.findBySchoolIdAndStandardId(schoolId, standardId)
                .stream()
                .map(d -> {
                    DivisionDto dto = modelMapper.map(d, DivisionDto.class);
                    dto.setStandardId(d.getStandard().getId());
                    dto.setStandardName(d.getStandard().getName());
                    return dto;
                })
                .toList();
    }

    // -----------------------------------
    // DELETE
    // -----------------------------------
    @Override
    public void delete(UUID id) {

        Division d = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found"));

        if (!d.getSchool().getId().equals(adminUtil.getSchoolId()))
            throw new IllegalArgumentException("Not allowed");

        divisionRepository.delete(d);
    }
}
