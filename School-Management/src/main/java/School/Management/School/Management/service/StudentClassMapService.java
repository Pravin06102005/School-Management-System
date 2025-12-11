package School.Management.School.Management.service;

import School.Management.School.Management.dto.StudentClassMapDto;

import java.util.List;
import java.util.UUID;

public interface StudentClassMapService {
    List<StudentClassMapDto> listByYear(UUID academicYearId);
    List<StudentClassMapDto> listByClass(UUID standardId, UUID divisionId);
    List<StudentClassMapDto> historyOfStudent(UUID studentId);
}
