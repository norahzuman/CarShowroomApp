package com.example.carshowroom.dao.repository;

import com.example.carshowroom.dao.models.CarShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarShowroomRepository extends JpaRepository<CarShowroomEntity, Long>, JpaSpecificationExecutor<CarShowroomEntity> {

    boolean existsByIdAndDeletedFalse(@Param("showroomId") Long showroomId);
}
