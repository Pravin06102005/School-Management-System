package School.Management.School.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private UUID id;

    private String name;
    private LocalDate dob;
    private String gender;
    private String phone;
    private String email;
    private String imageUrl;
    private String status;
    private Integer rollNumber;

    private UUID standardId;
    private UUID divisionId;

}
