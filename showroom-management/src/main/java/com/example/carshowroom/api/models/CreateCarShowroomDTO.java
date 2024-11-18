package com.example.carshowroom.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateCarShowroomDTO {

    @NotBlank(message = "Showroom name is required")
    @Size(max = 100, message = "Showroom name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Commercial registration number is required")
    @Digits(integer = 10, fraction = 0, message = "Commercial registration number must be exactly 10 digits")
    private Long commercialRegistrationNumber;

    @Size(max = 100, message = "Manager name cannot exceed 100 characters")
    private String managerName;

    @NotNull(message = "Contact number is required")
    @Digits(integer = 15, fraction = 0, message = "Contact number must be numeric and cannot exceed 15 digits")
    private Long contactNumber;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;
}
