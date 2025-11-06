package com.GYM.GYM.service;

import com.GYM.GYM.dto.TrainerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TrainerService {
    TrainerDTO addTrainer(TrainerDTO trainerDto, MultipartFile image) throws IOException;
    TrainerDTO updateTrainer(Long id, TrainerDTO trainerDto, MultipartFile image) throws IOException;
    void deleteTrainer(Long id);
    List<TrainerDTO> getAllTrainers();
    TrainerDTO getTrainerById(Long id);


}
