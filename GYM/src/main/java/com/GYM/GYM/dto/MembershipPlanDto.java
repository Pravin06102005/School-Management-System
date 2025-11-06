package com.GYM.GYM.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlanDto {

    private Long id;

    @NotBlank(message = "Plan name is required")
    private String name;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationInDays;

    @PositiveOrZero(message = "Price must be zero or positive")
    private Double price;

    private String description;

    private String imagePath;


    //    That way your frontend gets a full image URL in the JSON response automatically.
    public String getImageUrl() {
        if (imagePath == null) return null;
        return "http://localhost:8080/uploads/" + imagePath;
    }

}
