package ru.skishop.dto.request;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class UserPageableFilter extends PageableParams {

    private String fullName;
    private String email;
    private List<Long> roleIds;
    private String sortField;
    private Sort.Direction sortDirection;
}