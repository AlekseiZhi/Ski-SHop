package ru.skishop.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import ru.skishop.dto.request.SkiPageableFilter;
import ru.skishop.entity.Ski;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SkiSpecificationBuilder {

    public static Specification<Ski> buildSpecification(SkiPageableFilter skiPageableFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(skiPageableFilter.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), skiPageableFilter.getTitle()));
            }
            if (StringUtils.isNotEmpty(skiPageableFilter.getCategory())) {
                predicates.add(criteriaBuilder.like(root.get("category"), skiPageableFilter.getCategory()));
            }
            if (StringUtils.isNotEmpty(skiPageableFilter.getCompany())) {
                predicates.add(criteriaBuilder.like(root.get("company"), skiPageableFilter.getCompany()));
            }
            if (skiPageableFilter.getLengthFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("length"), skiPageableFilter.getLengthFrom()));
            }
            if (skiPageableFilter.getLengthTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("length"), skiPageableFilter.getLengthTo()));
            }
            if (skiPageableFilter.getPriceFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), skiPageableFilter.getPriceFrom()));
            }
            if (skiPageableFilter.getPriceTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), skiPageableFilter.getPriceTo()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}