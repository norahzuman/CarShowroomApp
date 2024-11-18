package com.example.carshowroom.dao.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "CAR")
public class CarEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 25)
    private String vin;

    @Column(nullable = false, length = 25)
    private String maker;

    @Column(nullable = false, length = 25)
    private String model;

    @Column(name = "model_year", nullable = false)
    private int modelYear;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "showroom_id")
    private CarShowroomEntity carShowroom;

    @Column(nullable = false)
    private boolean deleted = false;

}
