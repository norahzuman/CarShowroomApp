package com.example.carshowroom.api.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarDTO {
    private String vin;
    private String maker;
    private String model;
    private int modelYear;
    private BigDecimal price;
    private String carShowroomName;
    private Long contactNumber;
}
