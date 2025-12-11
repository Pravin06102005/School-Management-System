package School.Management.School.Management.service.impl;

import School.Management.School.Management.dto.AcademicYearDto;
import School.Management.School.Management.entity.AcademicYear;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.repo.AcademicYearRepository;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.service.AcademicYearService;
import School.Management.School.Management.util.AdminUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;
    private final ModelMapper modelMapper;
    private final AdminUtil adminUtil;
    private final SchoolRepository schoolRepository;

    @Override
    public AcademicYearDto create(AcademicYearDto req) {
        UUID schoolId =adminUtil.getSchoolId();
        School s=schoolRepository.findById(schoolId).orElseThrow(()->new RuntimeException("School not found"));

        AcademicYear ay=new AcademicYear();
        ay.setCode(req.getCode());
        ay.setActive(req.isActive());
        ay.setSchool(s);
        ay=academicYearRepository.save(ay);

        if (ay.isActive()) {
            // deactivate other active years
            AcademicYear finalAy = ay;
            academicYearRepository.findBySchoolId(schoolId).stream()
                    .filter(a -> !a.getId().equals(finalAy.getId()) && a.isActive())
                    .forEach(a -> {
                        a.setActive(false);
                        academicYearRepository.save(a);
                    });
        }
        return modelMapper.map(ay, AcademicYearDto.class);
    }

    @Override
    public AcademicYearDto getActiveyear() {
        UUID schoolId=adminUtil.getSchoolId();
        AcademicYear ay=academicYearRepository.findBySchoolIdAndActiveTrue(schoolId).orElseThrow(()-> new RuntimeException("Active year not set"));
        return modelMapper.map(ay,AcademicYearDto.class);
    }

    @Override
    public List<AcademicYearDto> listAll() {
        UUID schoolId=adminUtil.getSchoolId();
        return academicYearRepository.findBySchoolId(schoolId)
                .stream()
                .map(academicYear -> modelMapper.map(academicYear,AcademicYearDto.class))
                .toList();
    }

    @Override
    public AcademicYearDto update(UUID id, AcademicYearDto req) {
        AcademicYear ay = academicYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Academic year not found"));
        if (!ay.getSchool().getId().equals(adminUtil.getSchoolId())) throw new IllegalArgumentException("Not allowed");
        ay.setCode(req.getCode());
        ay.setActive(req.isActive());
        ay = academicYearRepository.save(ay);
        if (ay.isActive()) {
            AcademicYear finalAy = ay;
            academicYearRepository.findBySchoolId(ay.getSchool().getId()).stream()
                    .filter(a -> !a.getId().equals(finalAy.getId()) && a.isActive())
                    .forEach(a -> { a.setActive(false); academicYearRepository.save(a); });
        }
        return modelMapper.map(ay, AcademicYearDto.class);
    }

    @Override
    public void delete(UUID id) {
        AcademicYear ay = academicYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Academic year not found"));
        if (!ay.getSchool().getId().equals(adminUtil.getSchoolId())) throw new IllegalArgumentException("Not allowed");
        academicYearRepository.delete(ay);
    }

    @Override
    public AcademicYearDto setActive(UUID id) {
        AcademicYear ay = academicYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Academic year not found"));
        if (!ay.getSchool().getId().equals(adminUtil.getSchoolId())) throw new IllegalArgumentException("Not allowed");

        // deactivate others
        academicYearRepository.findBySchoolId(ay.getSchool().getId()).stream()
                .filter(a -> a.isActive())
                .forEach(a -> { a.setActive(false); academicYearRepository.save(a); });

        ay.setActive(true);
        ay = academicYearRepository.save(ay);
        return modelMapper.map(ay, AcademicYearDto.class);
    }
}
