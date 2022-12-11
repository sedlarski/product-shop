package com.sedlarski.productshop.validation;

import com.sedlarski.productshop.domain.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(UserServiceModel user);
}
