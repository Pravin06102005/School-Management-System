package School.Management.School.Management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardCreateRequest {


    @NotBlank(message = "Standard name is required")
    private String name;

    @NotNull(message = "Level is required")
    private Integer level;
}
