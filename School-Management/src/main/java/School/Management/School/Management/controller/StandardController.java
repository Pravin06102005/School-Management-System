package School.Management.School.Management.controller;


import School.Management.School.Management.dto.StandardCreateRequest;
import School.Management.School.Management.dto.StandardDto;
import School.Management.School.Management.service.StandardService;
import School.Management.School.Management.service.impl.StandardServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/standards")
@CrossOrigin(origins = "*")
public class StandardController {

    private final StandardService standardService;

    @GetMapping()
    public ResponseEntity<List<StandardDto>>list(){
        return ResponseEntity.ok(standardService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardDto> get(@PathVariable UUID id){
        return ResponseEntity.ok(standardService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StandardDto>create(@Valid @RequestBody StandardCreateRequest request){
        return ResponseEntity.ok(standardService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardDto> update(@Valid @PathVariable UUID id,@RequestBody StandardCreateRequest request){
        return ResponseEntity.ok(standardService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        standardService.delete(id);
        return ResponseEntity.ok("Delete successfully");
    }


}
