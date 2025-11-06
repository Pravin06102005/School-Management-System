package com.GYM.GYM.service.impl;


import com.GYM.GYM.dto.TrainerDTO;
import com.GYM.GYM.entity.Admin;
import com.GYM.GYM.entity.Trainer;
import com.GYM.GYM.repository.AdminRepository;
import com.GYM.GYM.repository.TrainerRepository;
import com.GYM.GYM.service.TrainerService;
import com.GYM.GYM.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;
    private  final FileUploadUtil fileUploadUtil;
    private final AdminRepository adminRepository;


    @Override
    public TrainerDTO addTrainer(TrainerDTO trainerDto, MultipartFile image) throws IOException {
        Trainer trainer=modelMapper.map(trainerDto,Trainer.class);
        if (image != null && !image.isEmpty()) {
            String imageName = fileUploadUtil.saveFile(image);
            trainer.setImagePath(imageName);
        }

        Trainer saved = trainerRepository.save(trainer);
        TrainerDTO responseDto = modelMapper.map(saved, TrainerDTO.class);
        return responseDto;
    }



    @Override
    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDto, MultipartFile image) throws IOException {
       Trainer trainer=trainerRepository.findById(id).orElseThrow(()->new RuntimeException("Trainer not found"));

       trainer.setName(trainerDto.getName());
       trainer.setSpecialization(trainerDto.getSpecialization());
       trainer.setExperience(trainerDto.getExperience());

       if (image !=null && !image.isEmpty()){
           fileUploadUtil.deleteFile(trainer.getImagePath());
           String newImage=fileUploadUtil.saveFile(image);
           trainer.setImagePath(newImage);
       }

       Trainer update=trainerRepository.save(trainer);
       return modelMapper.map(update,TrainerDTO.class);

    }



    @Override
    public void deleteTrainer(Long id) {
        trainerRepository.findById(id).ifPresent(trainer -> {
            try {
                fileUploadUtil.deleteFile(trainer.getImagePath());
            }catch (IOException ignored){}
                trainerRepository.delete(trainer);
        });
    }

    @Override
    public List<TrainerDTO> getAllTrainers() {
       return trainerRepository.findAll()
               .stream()
               .map(t->modelMapper.map(t,TrainerDTO.class))
               .collect(Collectors.toList());
    }

    @Override
    public TrainerDTO getTrainerById(Long id) {
        Trainer trainer=trainerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Trainer not found"));
        return modelMapper.map(trainer,TrainerDTO.class);
    }
}
