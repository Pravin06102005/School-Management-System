package com.GYM.GYM.repository;

import com.GYM.GYM.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Long> {


}
