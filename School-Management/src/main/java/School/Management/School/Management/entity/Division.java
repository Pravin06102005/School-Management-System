package School.Management.School.Management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Division {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stander_id",nullable = false)
    private Standard standard;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id",nullable = false)
    private School school;

}
