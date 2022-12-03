package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Role;
import com.sedlarski.productshop.domain.models.service.RoleServiceModel;
import com.sedlarski.productshop.domain.models.service.UserServiceModel;
import com.sedlarski.productshop.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {

        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void seedRolesInDb() {
        if(roleRepository.count() == 0) {
            roleRepository.saveAndFlush(new Role("ROLE_USER"));
            roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            roleRepository.saveAndFlush(new Role("ROLE_ROOT"));

        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(r -> modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

//    @Override
//    public void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers) {
//        if (numberOfUsers == 0) {
//            userServiceModel
//                    .setAuthorities(this.roleRepository
//                            .findAll()
//                            .stream()
//                            .map(r -> modelMapper.map(r, RoleServiceModel.class))
//                            .collect(Collectors.toSet()));
//        }
//    }


    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
