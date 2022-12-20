package com.sedlarski.productshop.web.controllers;

import com.sedlarski.productshop.domain.models.binding.UserEditBindingModel;
import com.sedlarski.productshop.domain.models.binding.UserRegisterBindingModel;
import com.sedlarski.productshop.domain.models.service.UserServiceModel;
import com.sedlarski.productshop.domain.view.UserAllViewModel;
import com.sedlarski.productshop.domain.view.UserProfileViewModel;
import com.sedlarski.productshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.view("user/register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute UserRegisterBindingModel model) {
        if(!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("user/register");
        }

        userService.registerUser(modelMapper.map(model, UserServiceModel.class));

        return super.redirect("user/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("user/login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        UserProfileViewModel userProfileViewModel = this.modelMapper
                .map(this.userService.findByUsername(principal.getName()), UserProfileViewModel.class);
        modelAndView.addObject("model", userProfileViewModel);
        return super.view("user/profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findByUsername(principal.getName()), UserEditBindingModel.class));
        return super.view("user/edit-profile", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model) {
        if(!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("user/edit-profile");
        }

        this.userService.editUserProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());

        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserAllViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = u.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
                    user.setAuthorities(authorities);

                    return user;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("users", users);
        return super.view("user/all-users", modelAndView);
    }



    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        this.userService.setUserRole(id, "user");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");
        return super.redirect("/users/all");
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


}
