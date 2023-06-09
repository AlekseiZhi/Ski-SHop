package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.entity.Role;
import ru.skishop.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private static final String DEFAULT_ROLE_USER = "user";

    public List<Role> getRolesByIds(List<Long> ids) {
        return roleRepository.findRolesByIdList(ids);
    }

    public Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE_USER);
    }
}