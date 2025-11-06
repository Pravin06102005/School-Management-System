package com.GYM.GYM.controller;


import com.GYM.GYM.dto.TrainerDTO;
import com.GYM.GYM.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    public ResponseEntity<TrainerDTO> addTrainer(
            @RequestPart("trainer") @Valid TrainerDTO trainerDto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(trainerService.addTrainer(trainerDto, image));

    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(
            @PathVariable Long id,
            @RequestPart("trainer") @Valid TrainerDTO trainerDto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainerDto, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.ok("Trainer deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }
}
