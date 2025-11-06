package com.GYM.GYM.repository;


import com.GYM.GYM.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
}
