package School.Management.School.Management.service.impl;

import School.Management.School.Management.dto.StudentDto;
import School.Management.School.Management.dto.StudentCreateRequest;
import School.Management.School.Management.entity.*;
import School.Management.School.Management.repo.*;
import School.Management.School.Management.service.CloudinaryService;
import School.Management.School.Management.service.StudentService;
import School.Management.School.Management.util.AdminUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentClassMapRepository studentClassMapRepository;
    private final StandardRepository standardRepository;
    private final DivisionRepository divisionRepository;
    private final AcademicYearRepository academicYearRepository;
    private final CloudinaryService cloudinaryService;
    private final AdminUtil adminUtil;
    private final ModelMapper modelMapper;
    private final SchoolRepository schoolRepository;

    // -----------------------------------------------------
    // CREATE STUDENT
    // -----------------------------------------------------
    @Override
    @Transactional
    public StudentDto create(StudentCreateRequest req, MultipartFile imageFile) {

        UUID schoolId = adminUtil.getSchoolId();

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        Standard std = standardRepository.findById(req.getStandardId())
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        if (!std.getSchool().getId().equals(schoolId))
            throw new IllegalArgumentException("Standard does not belong to your school");

        Division div = divisionRepository.findById(req.getDivisionId())
                .orElseThrow(() -> new RuntimeException("Division not found"));

        if (!div.getSchool().getId().equals(schoolId))
            throw new IllegalArgumentException("Division does not belong to your school");

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = cloudinaryService.uploadFile(imageFile);
        }

        Student student = new Student();
        student.setName(req.getName());
        student.setDob(req.getDob());
        student.setGender(req.getGender());
        student.setPhone(req.getPhone());
        student.setEmail(req.getEmail());
        student.setStatus("ACTIVE");
        student.setRollNumber(req.getRollNumber());
        student.setImageUrl(imageUrl);
        student.setSchool(school);
        student.setStandard(std);
        student.setDivision(div);

        student = studentRepository.save(student);

        AcademicYear ay = academicYearRepository.findBySchoolIdAndActiveTrue(schoolId)
                .orElseThrow(() -> new RuntimeException("Active Academic Year not found"));

        StudentClassMap map = new StudentClassMap();
        map.setStudent(student);
        map.setStandard(std);
        map.setDivision(div);
        map.setAcademicYear(ay);
        map.setRollNumber(req.getRollNumber());
        map.setSchool(school);

        studentClassMapRepository.save(map);

        student.getStudentClassMaps().add(map);

        return modelMapper.map(student, StudentDto.class);
    }

    // -----------------------------------------------------
    // GET ONE
    // -----------------------------------------------------
    @Override
    public StudentDto getById(UUID id) {
        UUID schoolId = adminUtil.getSchoolId();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!student.getSchool().getId().equals(schoolId))
            throw new IllegalArgumentException("Not allowed");

        return modelMapper.map(student, StudentDto.class);
    }

    // -----------------------------------------------------
    // LIST ALL
    // -----------------------------------------------------
    @Override
    public List<StudentDto> listAll() {
        UUID schoolId = adminUtil.getSchoolId();

        return studentRepository.findBySchoolId(schoolId)
                .stream()
                .map(s -> modelMapper.map(s, StudentDto.class))
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------
    // UPDATE STUDENT
    // -----------------------------------------------------
    @Override
    @Transactional
    public StudentDto update(UUID id, StudentCreateRequest req, MultipartFile imageFile) {

        UUID schoolId = adminUtil.getSchoolId();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!student.getSchool().getId().equals(schoolId))
            throw new IllegalArgumentException("Not allowed");

        student.setName(req.getName());
        student.setDob(req.getDob());
        student.setGender(req.getGender());
        student.setPhone(req.getPhone());
        student.setEmail(req.getEmail());
        student.setRollNumber(req.getRollNumber());

        if (imageFile != null && !imageFile.isEmpty()) {
            student.setImageUrl(cloudinaryService.uploadFile(imageFile));
        }

        AcademicYear ay = academicYearRepository.findBySchoolIdAndActiveTrue(schoolId)
                .orElseThrow(() -> new RuntimeException("Active Year missing"));

        Standard std = standardRepository.findById(req.getStandardId())
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        Division div = divisionRepository.findById(req.getDivisionId())
                .orElseThrow(() -> new RuntimeException("Division not found"));

        student.setStandard(std);
        student.setDivision(div);

        studentRepository.save(student);

        StudentClassMap map = studentClassMapRepository
                .findByStudentIdAndSchoolId(student.getId(), schoolId)
                .stream()
                .filter(m -> m.getAcademicYear().getId().equals(ay.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Class map missing"));

        map.setRollNumber(req.getRollNumber());
        map.setStandard(std);
        map.setDivision(div);

        studentClassMapRepository.save(map);

        return modelMapper.map(student, StudentDto.class);
    }

    // -----------------------------------------------------
    // DELETE
    // -----------------------------------------------------
    @Override
    @Transactional
    public void delete(UUID id) {

        UUID schoolId = adminUtil.getSchoolId();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!student.getSchool().getId().equals(schoolId))
            throw new IllegalArgumentException("Not allowed");

        studentClassMapRepository.deleteByStudentId(id);
        studentRepository.delete(student);
    }

    // -----------------------------------------------------
    // LIST FILTERED (Implementation of dynamic search/filter)
    // -----------------------------------------------------
    // StudentServiceImpl.java (Implementation of listFiltered)

    @Override
    public List<StudentDto> listFiltered(String name, UUID standardId, UUID divisionId, String gender) {
        UUID schoolId = adminUtil.getSchoolId();

        // 1. START: Base Specification (Filter by School ID)
        Specification<Student> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("school").get("id"), schoolId);

        // 2. Filter by Name (Search)
        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + name.toLowerCase() + "%"
                    ));
        }

        // 3. Filter by Standard ID
        if (standardId != null) {
            // We assume the Student entity has a @ManyToOne relationship named 'standard'
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("standard").get("id"), standardId));
        }

        // 4. Filter by Division ID
        if (divisionId != null) {
            // We assume the Student entity has a @ManyToOne relationship named 'division'
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("division").get("id"), divisionId));
        }

        // 5. Filter by Gender
        if (gender != null && !gender.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("gender"), gender));
        }

        // Execute the combined dynamic query
        return studentRepository.findAll(spec)
                .stream()
                .map(s -> modelMapper.map(s, StudentDto.class))
                .collect(Collectors.toList());
    }

    // ... (rest of the class) ...
}
