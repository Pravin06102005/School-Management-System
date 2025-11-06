package com.GYM.GYM.controller;


import com.GYM.GYM.dto.EquipmentDTO;
import com.GYM.GYM.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipmentController {


    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentDTO> addEquipment(
            @RequestPart("equipment") @Valid EquipmentDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(equipmentService.addEquipment(dto, image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(
            @PathVariable Long id,
            @RequestPart("equipment") @Valid EquipmentDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(equipmentService.updateEquipment(id, dto, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.ok("Equipment deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAll() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }
}
