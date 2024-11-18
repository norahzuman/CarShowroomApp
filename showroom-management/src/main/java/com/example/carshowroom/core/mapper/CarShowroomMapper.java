package com.example.carshowroom.core.mapper;

import com.example.carshowroom.api.models.CarShowroomDTO;
import com.example.carshowroom.api.models.CreateCarShowroomDTO;
import com.example.carshowroom.dao.models.CarShowroomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarShowroomMapper {

    CarShowroomDTO toDTO(CarShowroomEntity showroomEntity);

    CarShowroomEntity toEntity(CreateCarShowroomDTO createDto);
}
