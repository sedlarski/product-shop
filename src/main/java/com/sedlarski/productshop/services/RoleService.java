package com.sedlarski.productshop.services;

import com.sedlarski.productshop.domain.models.service.UserServiceModel;

public interface RoleService {

    void seedRolesInDb();

    void assignUserRoles(UserServiceModel userServiceModel);
}
