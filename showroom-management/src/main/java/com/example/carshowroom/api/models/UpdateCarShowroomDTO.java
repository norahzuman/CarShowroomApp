package com.example.carshowroom.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateCarShowroomDTO {

    @Size(max = 15, message = "Contact number must not exceed 15 digits")
    private Long contactNumber;

    @Size(max = 100, message = "Manager name must not exceed 100 characters")
    private String managerName;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;
}
