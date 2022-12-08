package com.sedlarski.productshop.config;

import com.sedlarski.productshop.domain.models.binding.ProductAddBindingModel;
import com.sedlarski.productshop.domain.models.service.CategoryServiceModel;
import com.sedlarski.productshop.domain.models.service.ProductServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.createTypeMap(ProductAddBindingModel.class,
//                    ProductServiceModel.class)
//                .addMapping(src -> src.getCategories(),
//                        (dest, value) -> dest.setCategories((List<String>) value));
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
