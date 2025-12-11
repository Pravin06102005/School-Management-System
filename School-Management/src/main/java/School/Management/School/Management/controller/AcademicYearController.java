package School.Management.School.Management.controller;


import School.Management.School.Management.dto.AcademicYearDto;
import School.Management.School.Management.service.AcademicYearService;
import School.Management.School.Management.service.impl.AcademicYearServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academic-years")
@RequiredArgsConstructor
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    @PostMapping
    public ResponseEntity<AcademicYearDto> create(@RequestBody AcademicYearDto req) {
        return ResponseEntity.ok(academicYearService.create(req));
    }

    @GetMapping("/active")
    public ResponseEntity<AcademicYearDto> getActive() {
        return ResponseEntity.ok(academicYearService.getActiveyear());
    }

    @GetMapping
    public ResponseEntity<List<AcademicYearDto>> getAll() {
        return ResponseEntity.ok(academicYearService.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYearDto> update(@PathVariable UUID id, @RequestBody AcademicYearDto req) {
        return ResponseEntity.ok(academicYearService.update(id, req));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<AcademicYearDto> setActive(@PathVariable UUID id) {
        return ResponseEntity.ok(academicYearService.setActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        academicYearService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
