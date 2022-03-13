package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.entities.Role;
import ru.skishop.repository.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRolesByIds(List<Long> ids) {
        return roleRepository.findRolesByIdList(ids);
    }
}