package School.Management.School.Management.service;

import School.Management.School.Management.dto.StandardCreateRequest;
import School.Management.School.Management.dto.StandardDto;

import java.util.List;
import java.util.UUID;

public interface StandardService {

    StandardDto create(StandardCreateRequest req);
    StandardDto getById(UUID id);
    List<StandardDto> listAll();
    StandardDto update(UUID id, StandardCreateRequest req);
    void delete(UUID id);
}
