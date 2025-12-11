package School.Management.School.Management.repo;

import School.Management.School.Management.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {

    List<Staff> findBySchoolId(UUID schoolId);

    // Optional for future role-based filtering
    List<Staff> findBySchoolIdAndRole(UUID schoolId, String role);
}
