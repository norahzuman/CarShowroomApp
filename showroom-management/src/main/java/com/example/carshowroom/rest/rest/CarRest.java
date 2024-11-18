package com.example.carshowroom.rest.rest;

import com.example.carshowroom.api.CarService;
import com.example.carshowroom.api.models.CarDTO;
import com.example.carshowroom.api.models.CreateCarDTO;
import com.example.carshowroom.dao.models.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarRest {

    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CreateCarDTO createCarDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(createCarDTO));
    }

    @GetMapping("/cars")
    public ResponseEntity<Page<CarDTO>> listCars(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(carService.getFilteredCars(filters, page, size, sortBy, sortDirection));
    }

    @GetMapping("/showroom-cars")
    public ResponseEntity<Page<CarDTO>> listCarsByShowroomId(
            final Long showroomId,
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(carService.getFilteredCarsByShowroomId(showroomId, filters, page, size, sortBy, sortDirection));
    }
}
