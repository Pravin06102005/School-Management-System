package com.GYM.GYM.service;

import com.GYM.GYM.dto.EquipmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EquipmentService {

    EquipmentDTO addEquipment(EquipmentDTO dto, MultipartFile image) throws IOException;
    EquipmentDTO updateEquipment(Long id, EquipmentDTO dto, MultipartFile image) throws IOException;
    void deleteEquipment(Long id);
    List<EquipmentDTO> getAllEquipment();
    EquipmentDTO getEquipmentById(Long id);
}
