package ru.skishop.criteriaApi;

import org.springframework.data.jpa.domain.Specification;
import ru.skishop.entity.Ski;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SkiSpecificationBilder {

    public static Specification<Ski> buildSpecification(SkiPageableFilter skiPageableFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (skiPageableFilter.getTitle() != null) {
                predicates.add(criteriaBuilder.equal(root.get("title"), skiPageableFilter.getTitle()));
            }
            if (skiPageableFilter.getCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), skiPageableFilter.getCategory()));
            }
            if (skiPageableFilter.getCompany() != null) {
                predicates.add(criteriaBuilder.equal(root.get("company"), skiPageableFilter.getCompany()));
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