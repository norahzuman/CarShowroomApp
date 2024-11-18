package com.example.carshowroom.dao.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SHOWROOM")
public class CarShowroomEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "commercial_registration_number",
            nullable = false, unique = true)
    @Digits(integer = 10, fraction = 0)
    private Long commercialRegistrationNumber;

    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Column(name = "contact_number", nullable = false)
    @Digits(integer = 15, fraction = 0, message = "Contact number must be numeric and have at most 15 digits.")
    private Long contactNumber;

    private String address;

    @Column(nullable = false)
    private boolean deleted = false;

}
