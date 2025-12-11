package School.Management.School.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardDto {

    private UUID id;

    private String name;

    private Integer level;
}
