package School.Management.School.Management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicYear {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;  // e.g., "2024-2025"
    private boolean active;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id",nullable = false)
    private School school;

    @OneToMany(mappedBy = "academicYear",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<StudentClassMap> studentClassMap = new ArrayList<>();



}
