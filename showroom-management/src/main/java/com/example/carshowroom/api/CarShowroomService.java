package com.example.carshowroom.api;

import com.example.carshowroom.api.models.CarShowroomDTO;
import com.example.carshowroom.api.models.CreateCarShowroomDTO;
import com.example.carshowroom.api.models.UpdateCarShowroomDTO;
import com.example.carshowroom.core.mapper.CarShowroomMapper;
import com.example.carshowroom.dao.models.CarShowroomEntity;
import com.example.carshowroom.dao.repository.CarShowroomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Service
public class CarShowroomService {

    @Autowired
    private CarShowroomRepository carShowroomRepository;

    @Autowired
    private CarShowroomMapper carShowroomMapper;

    @CacheEvict(value = "showroomList", allEntries = true)
    public CarShowroomDTO updateCarShowroom(Long id, UpdateCarShowroomDTO showroomDTO) {
        CarShowroomEntity existingShowroom = carShowroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Showroom with ID " + id + " not found"));

        if(Objects.nonNull(showroomDTO.getContactNumber())) {
            existingShowroom.setContactNumber(showroomDTO.getContactNumber());
        }
        if(Objects.nonNull(showroomDTO.getManagerName())) {
            existingShowroom.setManagerName(showroomDTO.getManagerName());
        }
        if(Objects.nonNull(showroomDTO.getAddress())) {
            existingShowroom.setAddress(showroomDTO.getAddress());
        }
        CarShowroomEntity updatedShowroom = carShowroomRepository.save(existingShowroom);
        return carShowroomMapper.toDTO(updatedShowroom);
    }

    public Page<CarShowroomDTO> getAllShowrooms(final int page, final int size, final String[] sort, final String theQuery, final Map<String, String> filters) {
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sorting = Sort.by(direction, sort[0]);
        Pageable pageable = PageRequest.of(page, size, sorting);

        // Apply dynamic search if query is provided
        Specification<CarShowroomEntity> spec = Specification.where((root, cq, cb) -> cb.equal(root.get("deleted"), false));

        if (theQuery != null && !theQuery.isEmpty()) {
            String searchPattern = "%" + theQuery.toLowerCase() + "%";
            spec = spec.and((root, cq, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), searchPattern),
                    cb.like(cb.lower(root.get("contactNumber").as(String.class)), searchPattern),
                    cb.like(cb.lower(root.get("commercialRegistrationNumber").as(String.class)), searchPattern)
            ));
        }
        if (filters.containsKey("filters")) {
            String rawFilters = filters.get("filters");
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Convert JSON string to Map
                Map<String, String> filterMap = mapper.readValue(rawFilters, new TypeReference<>() {
                });
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
        Page<CarShowroomEntity> entities = carShowroomRepository.findAll(spec, pageable);

        return entities.map(carShowroomMapper::toDTO);
    }

    public CarShowroomDTO createCarShowroom(@Valid CreateCarShowroomDTO showroomDTO) {

        CarShowroomEntity showroomEntity = carShowroomMapper.toEntity(showroomDTO);
        CarShowroomEntity savedEntity = carShowroomRepository.save(showroomEntity);

        return carShowroomMapper.toDTO(savedEntity);
    }

    public CarShowroomDTO getShowroomById(Long id) {
        CarShowroomEntity showroomEntity = carShowroomRepository.findById(id)
                .filter(showroom -> !showroom.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Showroom with ID " + id + " not found"));
        return carShowroomMapper.toDTO(showroomEntity);
    }

    public boolean deleteCarShowroomEntity(Long id) {
        CarShowroomEntity showroom = carShowroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Showroom with ID " + id + " not found"));

        // Perform soft delete
        showroom.setDeleted(true);
        carShowroomRepository.save(showroom);
        return true;
    }
}
