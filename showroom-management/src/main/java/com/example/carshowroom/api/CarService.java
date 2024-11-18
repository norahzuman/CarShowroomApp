package com.example.carshowroom.api;

import com.example.carshowroom.api.models.CarDTO;
import com.example.carshowroom.api.models.CreateCarDTO;
import com.example.carshowroom.api.specification.CarSpecification;
import com.example.carshowroom.core.mapper.CarMapper;
import com.example.carshowroom.dao.models.CarEntity;
import com.example.carshowroom.dao.models.CarShowroomEntity;
import com.example.carshowroom.dao.repository.CarRepository;
import com.example.carshowroom.dao.repository.CarShowroomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarShowroomRepository showroomRepository;

    @Autowired
    private CarMapper carMapper;

    public CarDTO createCar(@Valid CreateCarDTO createCarDTO) {
        // Validate if the showroom exists
        CarShowroomEntity showroom = showroomRepository.findById(createCarDTO.getShowroomId())
                .orElseThrow(() -> new EntityNotFoundException("Showroom with ID " + createCarDTO.getShowroomId() + " not found"));

        // Create a new car entity
        CarEntity car = new CarEntity();
        car.setVin(createCarDTO.getVin());
        car.setMaker(createCarDTO.getMaker());
        car.setModel(createCarDTO.getModel());
        car.setModelYear(createCarDTO.getModelYear());
        car.setPrice(createCarDTO.getPrice());
        car.setCarShowroom(showroom);

        return carMapper.toDTO(carRepository.save(car));
    }

    public Page<CarDTO> getFilteredCars(Map<String, String> filters, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending());

        // Build the dynamic filter specification
        Specification<CarEntity> spec = Specification.where(null);
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            spec = spec.and(new CarSpecification(filter.getKey(), filter.getValue()));
        }
        // Retrieve filtered cars with pagination
        Page<CarEntity> carEntities = carRepository.findAll(spec, pageable);

        // Map entities to DTOs
        return carEntities.map(car -> {
            CarDTO carDTO = carMapper.toDTO(car);
            carDTO.setCarShowroomName(car.getCarShowroom().getName());
            carDTO.setContactNumber(car.getCarShowroom().getContactNumber());
            return carDTO;
        });
    }

    public Page<CarDTO> getFilteredCarsByShowroomId(Long showroomId, Map<String, String> filters, int page, int size, String sortBy, String sortDirection) {
        if (showroomId == null) {
            throw new IllegalArgumentException("Showroom ID must not be null");
        }
        // Validate if the showroom exists
        validateShowroomExists(showroomId);

        Pageable pageable = PageRequest.of(page, size, sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending());

        Specification<CarEntity> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("carShowroom").get("id"), showroomId));

        // Add dynamic search filter
        if (filters.containsKey("query")) {
            String query = "%" + filters.get("query").toLowerCase() + "%";
            spec = spec.and((root, cq, cb) -> cb.or(
                    cb.like(cb.lower(root.get("maker")), query),
                    cb.like(cb.lower(root.get("model")), query),
                    cb.like(root.get("vin"), query),
                    cb.like(cb.function("str", String.class, root.get("modelYear")), query)
            ));
        }
        if (filters.containsKey("filters")) {
            String rawFilters = filters.get("filters");
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Convert JSON string to Map
                Map<String, String> filterMap = mapper.readValue(rawFilters, new TypeReference<Map<String, String>>() {});
                for (Map.Entry<String, String> entry : filterMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    // Add conditions dynamically based on filters
                    spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                }
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Invalid filter format. Expected JSON string.");
            }
        }
        Page<CarEntity> carEntities = carRepository.findAll(spec, pageable);

        return carEntities.map(car -> {
            CarDTO carDTO = carMapper.toDTO(car);
            carDTO.setCarShowroomName(car.getCarShowroom().getName());
            carDTO.setContactNumber(car.getCarShowroom().getContactNumber());
            return carDTO;
        });
    }

    public void validateShowroomExists(Long showroomId) {
        boolean exists = showroomRepository.existsByIdAndDeletedFalse(showroomId);
        if (!exists) {
            throw new EntityNotFoundException("Showroom with ID " + showroomId + " not found");
        }
    }
}
