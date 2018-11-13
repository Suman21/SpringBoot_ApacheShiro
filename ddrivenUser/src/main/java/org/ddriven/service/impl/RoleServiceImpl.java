package org.ddriven.service.impl;
import org.ddriven.repository.RoleRepository;
import org.ddriven.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    public void createRole(String role){
        roleRepository.save(role);
    }
    public void deleteRole(String role){
        Long id =roleRepository.findRoleId(role);
        roleRepository.deleteRolePermission(id);
        roleRepository.deleteRoleUser(id);
        roleRepository.deleteRole(role);
    }
}
