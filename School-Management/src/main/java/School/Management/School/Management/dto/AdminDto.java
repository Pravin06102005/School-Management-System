package School.Management.School.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    private UUID id;
    private String username;
    private String name;
    private String email;
    private UUID schoolId;
}
