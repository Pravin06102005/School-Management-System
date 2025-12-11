package School.Management.School.Management.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {

    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 50, message = "Name must be 2–50 characters")
    private String name;

//    @NotBlank(message = "Date of birth is required")
    private LocalDate dob;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;


    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Enter a valid 10-digit Indian phone number"
    )
    private String phone;


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;


    @NotNull(message = "Standard ID is required")
    private UUID standardId;

    @NotNull(message = "Division ID is required")
    private UUID divisionId;

    @NotNull(message = "Roll Number is required")
    @Min(value = 1, message = "Roll number must be at least 1")
    private Integer rollNumber;
}
