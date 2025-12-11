package School.Management.School.Management.service;

import School.Management.School.Management.dto.DivisionDto;
import School.Management.School.Management.dto.StandardDto;

import java.util.List;
import java.util.UUID;

public interface DivisionService {

    DivisionDto create(DivisionDto req);
    DivisionDto update (UUID id, DivisionDto req);
    DivisionDto getById(UUID id);
    List<DivisionDto> listByStandard(UUID StandardId);
    void delete(UUID id);

    List<DivisionDto> getAll();

}
