package School.Management.School.Management.controller;

import School.Management.School.Management.dto.DivisionDto;
import School.Management.School.Management.service.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/divisions")
@CrossOrigin(origins = "*")
public class DivisionController {

    private final DivisionService divisionService;

    @PostMapping
    public ResponseEntity<DivisionDto> create(@RequestBody DivisionDto dto){
        return ResponseEntity.ok(divisionService.create(dto));
    }

    // ⭐ GET ALL DIVISIONS OF LOGGED-IN SCHOOL
    @GetMapping
    public ResponseEntity<List<DivisionDto>> list() {
        return ResponseEntity.ok(divisionService.getAll());
    }

    // ⭐ GET DIVISIONS BY STANDARD
    @GetMapping("/standard/{standardId}")
    public ResponseEntity<List<DivisionDto>> listByStandard(@PathVariable UUID standardId){
        return ResponseEntity.ok(divisionService.listByStandard(standardId));
    }

    // ⭐ GET SINGLE DIVISION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DivisionDto> get(@PathVariable UUID id){
        return ResponseEntity.ok(divisionService.getById(id));
    }

    // ⭐ UPDATE DIVISION
    @PutMapping("/{id}")
    public ResponseEntity<DivisionDto> update(
            @PathVariable UUID id,
            @RequestBody DivisionDto dto
    ){
        return ResponseEntity.ok(divisionService.update(id, dto));
    }

    // ⭐ DELETE DIVISION
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        divisionService.delete(id);
        return ResponseEntity.ok("Delete successful");
    }
}
