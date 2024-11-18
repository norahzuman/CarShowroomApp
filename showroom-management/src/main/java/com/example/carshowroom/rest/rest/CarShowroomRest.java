package com.example.carshowroom.rest.rest;

import com.example.carshowroom.api.CarShowroomService;
import com.example.carshowroom.api.models.CarShowroomDTO;
import com.example.carshowroom.api.models.CreateCarShowroomDTO;
import com.example.carshowroom.api.models.UpdateCarShowroomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/car-showrooms")
public class CarShowroomRest {

    @Autowired
    private CarShowroomService carShowroomService;

    @PostMapping
    public ResponseEntity<CarShowroomDTO> createShowroom(@Valid @RequestBody CreateCarShowroomDTO showroomDTO) {
        CarShowroomDTO createdShowroom = carShowroomService.createCarShowroom(showroomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShowroom);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CarShowroomDTO>> listShowrooms(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort,
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(carShowroomService.getAllShowrooms(page, size, sort, query, filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarShowroomDTO> getShowroom(@PathVariable Long id) {
        return ResponseEntity.ok(carShowroomService.getShowroomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarShowroomDTO> updateShowroom(
            @PathVariable Long id, @RequestBody UpdateCarShowroomDTO showroom) {
        return ResponseEntity.ok(carShowroomService.updateCarShowroom(id, showroom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteShowroom(@PathVariable Long id) {
        return ResponseEntity.ok(carShowroomService.deleteCarShowroomEntity(id));
    }
}
