package ru.skishop.service;

import org.springframework.stereotype.Service;
import ru.skishop.entities.Role;
import ru.skishop.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRolesByIds(List<Long> list) {
        return roleRepository.findRolesByIdList(list);
    }
}