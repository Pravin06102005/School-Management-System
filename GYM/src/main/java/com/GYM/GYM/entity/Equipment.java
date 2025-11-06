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
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String imagePath;


}
