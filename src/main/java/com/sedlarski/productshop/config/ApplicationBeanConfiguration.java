package com.sedlarski.productshop.config;

import com.sedlarski.productshop.domain.entities.Order;
import com.sedlarski.productshop.domain.models.binding.ProductAddBindingModel;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.domain.models.service.OrderServiceModel;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import com.sedlarski.productshop.mappings.MappingsInitializer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class ApplicationBeanConfiguration {

    static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        MappingsInitializer.initMappings(mapper);
    }
    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
