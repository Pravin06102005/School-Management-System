package School.Management.School.Management.service;

import School.Management.School.Management.dto.StudentDto;
import School.Management.School.Management.dto.StudentCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentDto create(StudentCreateRequest req, MultipartFile imageUrl);
    StudentDto getById(UUID id);
    List<StudentDto>listAll();
    StudentDto update(UUID id, StudentCreateRequest req, MultipartFile imageUrl);
    void delete(UUID id);

    List<StudentDto> listFiltered(String name, UUID standardId, UUID divisionId, String gender);
}
