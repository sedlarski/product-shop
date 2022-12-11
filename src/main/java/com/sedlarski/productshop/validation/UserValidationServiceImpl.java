package com.sedlarski.productshop.validation;

import com.sedlarski.productshop.domain.models.service.UserServiceModel;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }

}
