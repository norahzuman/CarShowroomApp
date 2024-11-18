package com.example.carshowroom.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarShowroomDTO {
    private Long id;
    private String name;
    private Long commercialRegistrationNumber;
    private String managerName;
    private Long contactNumber;
    private String address;
    private boolean deleted;
}
