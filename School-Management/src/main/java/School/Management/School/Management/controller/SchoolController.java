package School.Management.School.Management.controller;


import School.Management.School.Management.dto.SchoolDto;
import School.Management.School.Management.service.SchoolService;
import School.Management.School.Management.service.impl.SchoolServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school")
@CrossOrigin(origins = "*")
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/me")
    public ResponseEntity<SchoolDto> getMySchool(){
        return ResponseEntity.ok(schoolService.getMySchool());
    }


    @PutMapping("/update")
    public ResponseEntity<SchoolDto> update(@RequestBody SchoolDto dto){
        return ResponseEntity.ok(schoolService.update(dto));
    }


}
