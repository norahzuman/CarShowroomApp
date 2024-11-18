package com.example.carshowroom.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateCarDTO {

    @NotBlank(message = "VIN is required")
    @Size(max = 25, message = "VIN must not exceed 25 characters")
    private String vin;

    @NotBlank(message = "Maker is required")
    @Size(max = 25, message = "Maker must not exceed 25 characters")
    private String maker;

    @NotBlank(message = "Model is required")
    @Size(max = 25, message = "Model must not exceed 25 characters")
    private String model;

    @NotNull(message = "Model year is required")
    @Digits(integer = 4, fraction = 0, message = "Model year must be a 4-digit number")
    private Integer modelYear;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Showroom ID is required")
    private Long showroomId;
}
