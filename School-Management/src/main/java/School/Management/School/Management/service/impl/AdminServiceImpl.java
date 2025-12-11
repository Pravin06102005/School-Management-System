package School.Management.School.Management.service.impl;


import School.Management.School.Management.dto.AdminDto;
import School.Management.School.Management.dto.RegisterRequest;
import School.Management.School.Management.entity.Admin;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.entity.Standard;
import School.Management.School.Management.repo.AdminRepository;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.repo.StandardRepository;
import School.Management.School.Management.security.JwtUtil;
import School.Management.School.Management.service.AdminService;
import School.Management.School.Management.util.AdminUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final SchoolRepository schoolRepository;
    private final StandardRepository standardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AdminUtil adminUtil;

    @Override
    @Transactional
    public AdminDto register(RegisterRequest req) {
        if (adminRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if (adminRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // create school

         School school=new School();
        school.setName(req.getSchoolName()==null ? req.getUsername()+"School ":req.getSchoolName());
        school=schoolRepository.save(school);

        //create Admin

        Admin admin=new Admin();
        admin.setUsername(req.getUsername());
        admin.setPassword(passwordEncoder.encode(req.getPassword()));
        admin.setName(req.getName());
        admin.setEmail(req.getEmail());
        admin.setSchool(school);
        admin=adminRepository.save(admin);


        // set admin back-reference on school (if you need)
        school.setAdmin(admin);
        schoolRepository.save(school);

        // seed standards Sr, 1..10
        seedStandardsForSchool(school);

        return modelMapper.map(admin,AdminDto.class);


    }
    // helper to seed standards
    private void seedStandardsForSchool(School school) {
        List<String> names = new ArrayList<>();
        names.add("Sr");
        for (int i = 1; i <= 10; i++) names.add(String.valueOf(i));
        int level = 0;
        for (String n : names) {
            Standard st = new Standard();
            st.setName(n);
            st.setLevel(level++);
            st.setSchool(school);
            standardRepository.save(st);
        }
    }



    // Login -> validate and return JWT token string
    @Override
    public String login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Admin not found"));
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return jwtUtil.generateToken(admin.getUsername(), admin.getSchool().getId());
    }

    @Override
    public AdminDto getProfile() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Admin admin=adminRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Admin not found"));
        return modelMapper.map(admin,AdminDto.class);
    }

    @Override
    public AdminDto updateProfile(AdminDto dto) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Admin admin=adminRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Admin not found"));
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin=adminRepository.save(admin);

        return modelMapper.map(admin,AdminDto.class);
    }

    @Override
    public void deleteAccount() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Admin admin=adminRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Admin not found"));
        School school=admin.getSchool();
        adminRepository.delete(admin);
        if (school !=null)schoolRepository.delete(school);
    }

}
