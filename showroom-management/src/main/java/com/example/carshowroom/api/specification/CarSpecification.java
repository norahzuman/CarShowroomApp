package com.example.carshowroom.api.specification;

import com.example.carshowroom.dao.models.CarEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter
@Setter
@AllArgsConstructor
public class CarSpecification implements Specification<CarEntity> {

    private final String field;
    private final String value;

    @Override
    public Predicate toPredicate(Root<CarEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // Handle filtering logic for dynamic fields
        if (field.equals("maker") || field.equals("model") || field.equals("vin")) {
            return criteriaBuilder.like(root.get(field), "%" + value + "%");
        } else if (field.equals("car_showroom_name")) {
            return criteriaBuilder.like(root.join("carShowroom").get("name"), "%" + value + "%");
        }
        return null;
    }
}
