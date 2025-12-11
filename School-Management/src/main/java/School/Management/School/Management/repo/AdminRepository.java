package School.Management.School.Management.repo;


import School.Management.School.Management.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin>findByUsername(String username);
    Optional<Admin> findByEmail(String email);

}
