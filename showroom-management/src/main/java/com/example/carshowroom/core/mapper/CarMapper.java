package com.example.carshowroom.core.mapper;

import com.example.carshowroom.api.models.CarDTO;
import com.example.carshowroom.dao.models.CarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDTO toDTO(CarEntity showroomEntity);
}
