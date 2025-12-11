package School.Management.School.Management.repo;

import School.Management.School.Management.dto.AcademicYearDto;
import School.Management.School.Management.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear,UUID> {

    // Only one active year per school
    Optional<AcademicYear> findBySchoolIdAndActiveTrue(UUID schoolId);

    List<AcademicYear> findBySchoolId(UUID schoolId);

    Optional<AcademicYear> findByIdAndSchoolId(UUID id, UUID schoolId);
}
