package School.Management.School.Management.repo;

import School.Management.School.Management.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StandardRepository extends JpaRepository<Standard ,UUID> {

    // For dropdown (sorted by level)
    List<Standard> findBySchoolIdOrderByLevel(UUID schoolId);

    // For promotion (find next class)
    Optional<Standard> findBySchoolIdAndLevel(UUID schoolId, Integer level);

    // For validation
    Optional<Standard> findByIdAndSchoolId(UUID id, UUID schoolId);


}
