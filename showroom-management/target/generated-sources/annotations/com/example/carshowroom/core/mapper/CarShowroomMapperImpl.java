package com.example.carshowroom.core.mapper;

import com.example.carshowroom.api.models.CarShowroomDTO;
import com.example.carshowroom.api.models.CreateCarShowroomDTO;
import com.example.carshowroom.dao.models.CarShowroomEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-18T22:03:46+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CarShowroomMapperImpl implements CarShowroomMapper {

    @Override
    public CarShowroomDTO toDTO(CarShowroomEntity showroomEntity) {
        if ( showroomEntity == null ) {
            return null;
        }

        CarShowroomDTO carShowroomDTO = new CarShowroomDTO();

        carShowroomDTO.setId( showroomEntity.getId() );
        carShowroomDTO.setName( showroomEntity.getName() );
        carShowroomDTO.setCommercialRegistrationNumber( showroomEntity.getCommercialRegistrationNumber() );
        carShowroomDTO.setManagerName( showroomEntity.getManagerName() );
        carShowroomDTO.setContactNumber( showroomEntity.getContactNumber() );
        carShowroomDTO.setAddress( showroomEntity.getAddress() );
        carShowroomDTO.setDeleted( showroomEntity.isDeleted() );

        return carShowroomDTO;
    }

    @Override
    public CarShowroomEntity toEntity(CreateCarShowroomDTO createDto) {
        if ( createDto == null ) {
            return null;
        }

        CarShowroomEntity carShowroomEntity = new CarShowroomEntity();

        carShowroomEntity.setName( createDto.getName() );
        carShowroomEntity.setCommercialRegistrationNumber( createDto.getCommercialRegistrationNumber() );
        carShowroomEntity.setManagerName( createDto.getManagerName() );
        carShowroomEntity.setContactNumber( createDto.getContactNumber() );
        carShowroomEntity.setAddress( createDto.getAddress() );

        return carShowroomEntity;
    }
}
