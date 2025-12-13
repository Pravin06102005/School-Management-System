package School.Management.School.Management.controller;

import School.Management.School.Management.dto.StaffDto;
import School.Management.School.Management.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
// @CrossOrigin(origins = "*")
public class StaffController {

    private final StaffService staffService;

    // --- Create (Unchanged) ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StaffDto> create(@Valid @RequestPart("data") StaffDto dto,
                                           @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(staffService.create(dto, image));
    }

    // --- Read/List (FIXED: Parameter Names for Filtering) ---
    @GetMapping
    public ResponseEntity<List<StaffDto>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role, // Changed from 'designation' to 'role'
            @RequestParam(required = false) String department // Kept department for flexibility
    ) {
        // NOTE: StaffService method signature MUST match these parameters.
        return ResponseEntity.ok(staffService.listAll(search, role, department));
    }

    // --- Get By ID (FIXED: Ensure precise path variable binding) ---
    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> get(@PathVariable("id") UUID id) {
        // Added explicit path variable name for safer binding.
        // If this still fails, temporarily change UUID id to String id and use UUID.fromString() in the service.
        return ResponseEntity.ok((StaffDto) staffService.getById(String.valueOf(id)));
    }

    // --- Update (Unchanged, assuming the service handles the MultipartFile correctly) ---
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StaffDto> update(
            @Valid
            @PathVariable String id, // Change to String here too!
            @RequestPart("data") StaffDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image){
        return ResponseEntity.ok(staffService.update(UUID.fromString(id), dto, image));
    }

    // --- Delete (Unchanged) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        staffService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
