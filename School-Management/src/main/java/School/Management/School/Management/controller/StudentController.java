package School.Management.School.Management.controller;


import School.Management.School.Management.dto.StudentCreateRequest;
import School.Management.School.Management.dto.StudentDto;
import School.Management.School.Management.service.StudentService;
import School.Management.School.Management.service.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentDto> create(

            @RequestPart("data")StudentCreateRequest req,
            @RequestPart(value = "image",required = false)MultipartFile image
            ){
        return ResponseEntity.ok(studentService.create(req,image));
    }

//    @GetMapping
//    public ResponseEntity<List<StudentDto>> list(){
//        return ResponseEntity.ok(studentService.listAll());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> get(@PathVariable UUID id){
        return ResponseEntity.ok(studentService.getById(id));
    }
    // StudentController.java (Updated list method)

    // StudentController.java

    @GetMapping
    public ResponseEntity<List<StudentDto>> list(
            // Accept all parameters as Strings since the frontend sends empty strings for 'All' filters
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String standardId,
            @RequestParam(required = false) String divisionId,
            @RequestParam(required = false) String gender
    ) {
        // 1. Prepare UUID objects, handling null or empty strings
        UUID standardUuid = null;
        if (standardId != null && !standardId.isEmpty()) {
            try {
                standardUuid = UUID.fromString(standardId);
            } catch (IllegalArgumentException e) {
                // Handle invalid UUID format if necessary, though ideally fixed in DTO/frontend
            }
        }

        UUID divisionUuid = null;
        if (divisionId != null && !divisionId.isEmpty()) {
            try {
                divisionUuid = UUID.fromString(divisionId);
            } catch (IllegalArgumentException e) {
                // Handle invalid UUID format
            }
        }

        // 2. Determine if any filter is active
        boolean isFiltered = name != null && !name.isEmpty()
                || standardUuid != null
                || divisionUuid != null
                || gender != null && !gender.isEmpty();

        if (isFiltered) {
            // 3. Call the dedicated filtering service method
            // NOTE: You must ensure studentService.listFiltered is implemented
            return ResponseEntity.ok(studentService.listFiltered(name, standardUuid, divisionUuid, gender));
        }

        // Default: return all if no filters are provided
        return ResponseEntity.ok(studentService.listAll());
    }

// ... (rest of the controller remains the same)


    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentDto> update(
            @Valid
            @PathVariable UUID id,
            @RequestPart("data")StudentCreateRequest req,
            @RequestPart(value = "image",required = false)MultipartFile image
    ){
        return ResponseEntity.ok(studentService.update(id,req,image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        studentService.delete(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
