package School.Management.School.Management.service;

import School.Management.School.Management.dto.SchoolDto;

import java.util.List;
import java.util.UUID;

public interface SchoolService {
    SchoolDto getById(UUID id);
    SchoolDto getMySchool();
    SchoolDto update(SchoolDto dto);
    void delete(UUID id);
}
