package School.Management.School.Management.repo;

import School.Management.School.Management.entity.StudentClassMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentClassMapRepository extends JpaRepository<StudentClassMap, UUID> {

    // All mappings for a school in a specific active academic year
    List<StudentClassMap> findBySchoolIdAndAcademicYearId(UUID schoolId, UUID academicYearId);

    // For class table view (standard + division)
    List<StudentClassMap> findBySchoolIdAndStandardIdAndDivisionId(UUID schoolId, UUID standardId, UUID divisionId);

    // ⬅️ NEW: Get all students by Standard (inside school)
    List<StudentClassMap> findBySchoolIdAndStandardId(UUID schoolId, UUID standardId);

    // Get all maps for student inside school
    List<StudentClassMap> findByStudentIdAndSchoolId(UUID studentId, UUID schoolId);

    // Delete mappings when student is deleted
    void deleteByStudentId(UUID studentId);
}
