package School.Management.School.Management.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class School {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "school" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private Admin admin;

    @OneToMany(mappedBy = "school" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Standard> standards=new ArrayList<>();

    @OneToMany(mappedBy = "school" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Division> divisions=new ArrayList<>();

    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AcademicYear> academicYears=new ArrayList<>();

    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Student> students=new ArrayList<>();

    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Staff> staff=new ArrayList<>();
}
