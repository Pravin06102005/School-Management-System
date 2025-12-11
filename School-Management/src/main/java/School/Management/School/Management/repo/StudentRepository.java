package School.Management.School.Management.repo;

import School.Management.School.Management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>, JpaSpecificationExecutor<Student> {

    // All students of selected school
    List<Student> findBySchoolId(UUID schoolId);

    // For secure fetch
    Optional<Student> findByIdAndSchoolId(UUID id, UUID schoolId);

    // For promotions (optional filtering by status)
    List<Student> findBySchoolIdAndStatus(UUID schoolId, String status);


}
