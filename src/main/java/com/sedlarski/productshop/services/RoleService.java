package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.entities.Role;
import com.sedlarski.productshop.domain.models.service.RoleServiceModel;
import com.sedlarski.productshop.domain.models.service.UserServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRolesInDb();

//    void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers);

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
