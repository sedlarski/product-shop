package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.error.ProductNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({ProductNotFoundException.class})
    public ModelAndView handleProductNotFound(Throwable e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

}
