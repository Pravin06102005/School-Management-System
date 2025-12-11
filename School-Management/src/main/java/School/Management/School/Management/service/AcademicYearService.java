package School.Management.School.Management.service;

import School.Management.School.Management.dto.AcademicYearDto;

import java.util.List;
import java.util.UUID;

public interface AcademicYearService {

    AcademicYearDto create(AcademicYearDto req);
    AcademicYearDto getActiveyear();
    List<AcademicYearDto>listAll();
    AcademicYearDto update(UUID id, AcademicYearDto req);
    void  delete(UUID id);
    AcademicYearDto setActive(UUID id);// set given year active, deactivate others

}
