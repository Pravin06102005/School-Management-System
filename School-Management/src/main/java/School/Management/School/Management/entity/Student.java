package School.Management.School.Management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private LocalDate dob;
    private String gender;
    private String phone;
    private String email;
    private String imageUrl;
    private String status; // ACTIVE / PASSED_OUT
    private Integer rollNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id",nullable = false)
    @JsonIgnore
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id", nullable = false)
    private Standard standard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<StudentClassMap> studentClassMaps=new ArrayList<>();



}
