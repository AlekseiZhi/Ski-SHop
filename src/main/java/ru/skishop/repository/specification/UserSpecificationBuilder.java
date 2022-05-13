package ru.skishop.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.skishop.dto.request.UserPageableFilter;
import ru.skishop.entity.Role;
import ru.skishop.entity.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecificationBuilder {

    public static Specification<User> buildSpecification(UserPageableFilter userPageableFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(userPageableFilter.getFullName())) {
                predicates.add(criteriaBuilder.like(root.get("fullName"), "%" + userPageableFilter.getFullName() + "%"));
            }
            if (StringUtils.isNotEmpty(userPageableFilter.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), userPageableFilter.getEmail()));
            }
            if (userPageableFilter.getRoleIds() != null) {
                Join<User, Role> roleJoin = root.join("roles");
                predicates.add(roleJoin.get("id").in(userPageableFilter.getRoleIds()));
            }

            criteriaBuilder.like(root.get("fullName"), userPageableFilter.getFullName());
            query.distinct(true);

            if (userPageableFilter.getSortDirection().equals(Sort.Direction.ASC)) {
                query.orderBy(criteriaBuilder.asc(root.get(userPageableFilter.getSortField())));
            }

            if (userPageableFilter.getSortDirection().equals(Sort.Direction.DESC)) {
                query.orderBy(criteriaBuilder.desc(root.get(userPageableFilter.getSortField())));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}