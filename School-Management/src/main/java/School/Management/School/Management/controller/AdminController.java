package School.Management.School.Management.controller;


import School.Management.School.Management.dto.AdminDto;
import School.Management.School.Management.service.AdminService;
import School.Management.School.Management.service.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/me")
    public ResponseEntity<AdminDto> getprofile(){
        return ResponseEntity.ok(adminService.getProfile());
    }

    @PutMapping("/update")
    public ResponseEntity<AdminDto> update(@RequestBody AdminDto dto){
        return ResponseEntity.ok(adminService.updateProfile(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String>delete(){
        adminService.deleteAccount();
        return ResponseEntity.ok("Admin and school successfully delete");
    }
}
