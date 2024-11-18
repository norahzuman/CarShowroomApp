package com.example.carshowroom.core.mapper;

import com.example.carshowroom.api.models.CarDTO;
import com.example.carshowroom.dao.models.CarEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-18T22:03:46+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDTO toDTO(CarEntity showroomEntity) {
        if ( showroomEntity == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setVin( showroomEntity.getVin() );
        carDTO.setMaker( showroomEntity.getMaker() );
        carDTO.setModel( showroomEntity.getModel() );
        carDTO.setModelYear( showroomEntity.getModelYear() );
        carDTO.setPrice( showroomEntity.getPrice() );

        return carDTO;
    }
}
