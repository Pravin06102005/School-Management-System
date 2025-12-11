package School.Management.School.Management.repo;

import School.Management.School.Management.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DivisionRepository extends JpaRepository<Division,UUID> {

    // Dropdown: all divisions under one standard
    List<Division> findBySchoolIdAndStandardId(UUID schoolId, UUID standardId);

    Optional<Division> findByIdAndSchoolId(UUID id, UUID schoolId);

    List<Division> findBySchoolId(UUID schoolId);

    long countByStandardId(UUID standardId);


}
