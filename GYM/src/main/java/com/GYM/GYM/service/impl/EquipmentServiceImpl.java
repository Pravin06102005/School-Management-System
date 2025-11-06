package com.GYM.GYM.service.impl;

import com.GYM.GYM.dto.EquipmentDTO;
import com.GYM.GYM.entity.Equipment;
import com.GYM.GYM.repository.EquipmentRepository;
import com.GYM.GYM.service.EquipmentService;
import com.GYM.GYM.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl  implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ModelMapper modelMapper;
    private final FileUploadUtil fileUploadUtil;




    @Override
    public EquipmentDTO addEquipment(EquipmentDTO dto, MultipartFile image) throws IOException {
        Equipment equipment=modelMapper.map(dto,Equipment.class);
        equipment.setImagePath(fileUploadUtil.saveFile(image));
        Equipment saved=equipmentRepository.save(equipment);
        return modelMapper.map(saved,EquipmentDTO.class);
    }

    @Override
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO dto, MultipartFile image) throws IOException {

        Equipment equipment=equipmentRepository.findById(id).orElseThrow(()->new RuntimeException("Equipment not found"));
        equipment.setName(dto.getName());
        equipment.setDescription(dto.getDescription());

        if (image !=null || !image.isEmpty()){
            fileUploadUtil.deleteFile(equipment.getImagePath());
            equipment.setImagePath(fileUploadUtil.saveFile(image));
        }

        Equipment updated=equipmentRepository.save(equipment);
        return modelMapper.map(updated,EquipmentDTO.class);
    }

    @Override
    public void deleteEquipment(Long id) {
        equipmentRepository.findById(id).ifPresent(equipment -> {
            try {
                fileUploadUtil.deleteFile(equipment.getImagePath());
            }catch (IOException ignored){}
            equipmentRepository.delete(equipment);
        });
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return equipmentRepository.findAll()
                .stream()
                .map(equipment -> modelMapper.map(equipment,EquipmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentDTO getEquipmentById(Long id) {
        Equipment equipment=equipmentRepository.findById(id).orElseThrow(()->new RuntimeException("Equipment not found"));
        return modelMapper.map(equipment,EquipmentDTO.class);

    }
}
