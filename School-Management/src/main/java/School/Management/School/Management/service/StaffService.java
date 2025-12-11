package School.Management.School.Management.service;

import School.Management.School.Management.dto.StaffDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface StaffService {

    StaffDto create(StaffDto req, MultipartFile image);

    // Ensure this signature is correct:
// StaffService.java
    List<StaffDto> listAll(String search, String role, String department); // Use 'role'

    StaffDto getById(String id); // Note the corrected return type

    StaffDto update(UUID id, StaffDto dto, MultipartFile image);

    void delete(UUID id);

}
