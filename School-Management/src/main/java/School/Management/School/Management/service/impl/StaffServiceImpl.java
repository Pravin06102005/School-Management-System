package School.Management.School.Management.service.impl;


import School.Management.School.Management.dto.StaffDto;
import School.Management.School.Management.entity.School;
import School.Management.School.Management.entity.Staff;
import School.Management.School.Management.repo.SchoolRepository;
import School.Management.School.Management.repo.StaffRepository;
import School.Management.School.Management.service.CloudinaryService;
import School.Management.School.Management.service.StaffService;
import School.Management.School.Management.util.AdminUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils; // Import for checking if string is null or empty

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final SchoolRepository schoolRepository;
    private final CloudinaryService cloudinaryService;
    private final AdminUtil adminUtil;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public StaffDto create(StaffDto req, MultipartFile imageUrl) {
        UUID schoolId=adminUtil.getSchoolId();
        School school=schoolRepository.findById(schoolId).orElseThrow(()->new RuntimeException("School not found"));

        Staff s = new Staff();
        s.setName(req.getName());
        s.setRole(req.getRole());
        s.setPhone(req.getPhone());
        s.setEmail(req.getEmail());

        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Note: Casting is unnecessary here, assuming cloudinaryService.uploadFile accepts MultipartFile
            s.setImageUrl(cloudinaryService.uploadFile(imageUrl));
        }

        s.setSchool(school);
        s=staffRepository.save(s);
        return modelMapper.map(s,StaffDto.class);
    }

    // NOTE: This method signature needs correction based on the StaffController
    // It should return StaffDto, not List<StaffDto>
    @Override
// FIX: Accept String ID
    public StaffDto getById(String id) {
        UUID schoolId = adminUtil.getSchoolId();

        // Manual conversion
        UUID staffUuid = UUID.fromString(id);

        Staff staff = staffRepository.findById(staffUuid)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!staff.getSchool().getId().equals(schoolId)) {
            throw new IllegalArgumentException("Staff not found in this school");
        }
        return modelMapper.map(staff, StaffDto.class);
    }

    /**
     * Finds all staff for the current school, optionally filtering by search term, role, or department.
     * @param search General search term (e.g., name or part of email).
     * @param role Filter by staff role.
     * @param department Filter by staff department (if department field exists in Staff entity).
     * @return List of StaffDto matching the criteria.
     */
    @Override
    public List<StaffDto> listAll(String search, String role, String department) {
        UUID schoolId = adminUtil.getSchoolId();

        // 1. Fetch all staff for the authenticated school
        List<Staff> staffList = staffRepository.findBySchoolId(schoolId);

        // 2. Apply filters using streams
        return staffList.stream()
                .filter(staff -> {
                    boolean matches = true;

                    // Filter by search (case-insensitive check against name and email)
                    if (StringUtils.hasText(search)) {
                        String lowerSearch = search.toLowerCase();
                        boolean nameMatch = staff.getName() != null && staff.getName().toLowerCase().contains(lowerSearch);
                        boolean emailMatch = staff.getEmail() != null && staff.getEmail().toLowerCase().contains(lowerSearch);
                        // Assuming you want to search by name or email
                        matches = matches && (nameMatch || emailMatch);
                    }

                    // Filter by Role
                    if (StringUtils.hasText(role)) {
                        matches = matches && staff.getRole() != null && staff.getRole().equalsIgnoreCase(role);
                    }

                    // Filter by Department (assuming a 'department' field exists on Staff entity)
                    // NOTE: If your Staff entity doesn't have a 'department' field, remove this block.
                    // For now, I'll assume 'department' maps to 'role' or remove it if not possible.
                    // I will map the controller's 'designation' and 'department' to the entity's 'role' for simplicity here.

                    // If the controller used 'designation' and 'department', and your entity only has 'role',
                    // you need to decide which one maps to 'role' or if you need to check both.
                    // Based on the fields available in your DTO/Entity (name, role, phone, email),
                    // I will assume the controller's 'designation' is mapped to 'role' and ignore 'department'
                    // unless you update the Staff entity/DTO to include it.

                    // Rechecking role filter to handle both 'role' and the controller's 'designation' if needed.
                    // Since the controller used 'designation' and 'department', let's use the 'role' field here.

                    return matches;
                })
                .map(m->modelMapper.map(m,StaffDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StaffDto update(UUID id, StaffDto req, MultipartFile imageUrl) {
        UUID schoolId = adminUtil.getSchoolId();

        Staff s = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!s.getSchool().getId().equals(schoolId)) {
            throw new IllegalArgumentException("Not allowed");
        }
        // Update basic fields
        s.setName(req.getName());
        s.setRole(req.getRole());
        s.setPhone(req.getPhone());
        s.setEmail(req.getEmail());

        // Update image if new one provided
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String url = cloudinaryService.uploadFile(imageUrl);
            s.setImageUrl(url);
        }

        s = staffRepository.save(s);

        return modelMapper.map(s, StaffDto.class);

    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID schoolId=adminUtil.getSchoolId();
        Staff s=staffRepository.findById(id).orElseThrow(()->new RuntimeException("Staff not found"));
        if (!s.getSchool().getId().equals(schoolId))throw new IllegalArgumentException("Not allowed");
        staffRepository.delete(s);

    }
}