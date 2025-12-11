package School.Management.School.Management.service.impl;

import School.Management.School.Management.dto.StudentClassMapDto;
import School.Management.School.Management.repo.StudentClassMapRepository;
import School.Management.School.Management.service.StudentClassMapService;
import School.Management.School.Management.util.AdminUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentClassMapServiceImpl implements StudentClassMapService {

    private final StudentClassMapRepository scmRepo;
    private final AdminUtil adminUtil;
    private final ModelMapper modelMapper;

    @Override
    public List<StudentClassMapDto> listByYear(UUID academicYearId) {
        UUID schoolId=adminUtil.getSchoolId();
        return scmRepo.findBySchoolIdAndAcademicYearId(schoolId, academicYearId)
                .stream()
                .map(m -> modelMapper.map(m, StudentClassMapDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentClassMapDto> listByClass(UUID standardId, UUID divisionId) {
        UUID schoolId=adminUtil.getSchoolId();
        return scmRepo.findBySchoolIdAndStandardIdAndDivisionId(schoolId, standardId, divisionId).stream()
                .map(m -> modelMapper.map(m, StudentClassMapDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<StudentClassMapDto> historyOfStudent(UUID studentId) {
        UUID schoolId=adminUtil.getSchoolId();
        var list=scmRepo.findByStudentIdAndSchoolId(studentId,schoolId);
        if (list==null || list.isEmpty()) throw new RuntimeException("No history found");
        return list.stream()
                .map(m->modelMapper.map(m,StudentClassMapDto.class))
                .collect(Collectors.toList());
    }
}
