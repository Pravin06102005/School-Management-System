package com.GYM.GYM.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;


    private String imagePath;




//    That way your frontend gets a full image URL in the JSON response automatically.
    public String getImageUrl() {
        if (imagePath == null) return null;
        return "http://localhost:8080/uploads/" + imagePath;
    }

}
