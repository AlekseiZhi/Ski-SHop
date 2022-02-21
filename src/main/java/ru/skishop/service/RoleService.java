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

//    public List<Role> getRolesByIds(List<Long> list) {
//        List<Role> roleList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            roleList.add(roleRepository.findById(Long.valueOf(list.get(i))).orElseThrow(() -> {
//                throw new RuntimeException();
//            }));
//        }
//        return roleList;
//    }

    public List<Role> getRolesByIds(List<Long> list) {
        return roleRepository.findRolesByIdList(list);
    }
}