package com.GYM.GYM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false) // 💡 Recommended: Ensure specialization is NOT NULL
    private String specialization;

    @Column(nullable = false) // 💡 Recommended: Ensure experience is NOT NULL
    private Integer experience;


    private String imagePath;

    
}