package School.Management.School.Management.controller;


import School.Management.School.Management.dto.LoginRequest;
import School.Management.School.Management.dto.RegisterRequest;
import School.Management.School.Management.entity.Admin;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.repo.AdminRepository;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AdminRepository adminRepository;
    private final SchoolRepository schoolRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req){

        Admin admin=new Admin();
        admin.setUsername(req.getUsername());
        admin.setPassword(passwordEncoder.encode(req.getPassword()));
        admin.setName(req.getName());
        admin.setEmail(req.getEmail());

        // each admin creates one school
        School school=new School();
//        school.setName(school.getName());
        school.setName(req.getSchoolName());
        school=schoolRepository.save(school);

        admin.setSchool(school);
        admin=adminRepository.save(admin);

        return ResponseEntity.ok("Admin registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){

        Admin admin=adminRepository.findByUsername(req.getUsername())
                .orElseThrow(()->new RuntimeException("Invaild username"));

        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invaild password");
        }
            String token=jwtUtil.generateToken(
                    admin.getUsername(),
                    admin.getSchool().getId()
            );

            return ResponseEntity.ok(token);
        }

 }
