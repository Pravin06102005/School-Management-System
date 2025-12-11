package School.Management.School.Management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionDto {

    private UUID id;

    @NotBlank(message = "Division name is required")
    @Pattern(
            regexp = "^[A-Z]$",
            message = "Division must be a single uppercase letter (A-Z)"
    )
    private String name;

    @NotNull(message = "Standard ID is required")
    private UUID standardId;

    private String standardName;
}
