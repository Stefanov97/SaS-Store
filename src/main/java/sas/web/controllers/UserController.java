package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sas.service.models.RoleServiceModel;
import sas.service.models.UserServiceModel;
import sas.service.services.UserService;
import sas.web.models.UserEditEmailModel;
import sas.web.models.UserEditPasswordModel;
import sas.web.models.UserLoginModel;
import sas.web.models.UserViewModel;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @ModelAttribute("userEditPasswordModel")
    public UserEditPasswordModel userEditPasswordModel() {
        return new UserEditPasswordModel();
    }

    @ModelAttribute("userEditEmailModel")
    public UserEditEmailModel userEditEmailModel() {
        return new UserEditEmailModel();
    }

    private final UserService userService;
    private final ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("users/profile")
    public ModelAndView getUserProfile(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.getByUsername(username);
        UserLoginModel user = this.mapper.map(userServiceModel, UserLoginModel.class);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/user/profile");
        return modelAndView;
    }

    @GetMapping("users/edit-password")
    public String getUserEditProfile(Principal principal, HttpSession session) {
        String username = principal.getName();
        UserLoginModel user = this.mapper.map(this.userService.getByUsername(username), UserLoginModel.class);
        session.setAttribute("user", user);
        return "/user/edit-password";
    }

    @PostMapping("users/edit-password")
    public String getUserEditProfileConfirm(@Valid @ModelAttribute("userEditPasswordModel") UserEditPasswordModel model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/user/edit-password";
        } else {
            this.userService.updatePassword(this.mapper.map(model, UserServiceModel.class));
            return "redirect:/users/profile";
        }
    }

    @GetMapping("users/edit-email")
    public String getUserEditEmail(Principal principal, HttpSession session) {
        String username = principal.getName();
        UserLoginModel user = this.mapper.map(this.userService.getByUsername(username), UserLoginModel.class);
        session.setAttribute("user", user);
        return "/user/edit-email";
    }

    @PostMapping("users/edit-email")
    public String getUserEditEmailConfirm(@Valid @ModelAttribute("userEditEmailModel") UserEditEmailModel model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/user/edit-email";
        } else {
            this.userService.updateEmail(this.mapper.map(model, UserServiceModel.class));
            return "redirect:/users/profile";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("users/all-users")
    public ModelAndView getAllUsers(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        List<UserViewModel> users = this.userService.getAll().stream().filter(u -> !u.getUsername().equals(username)).map(u -> {
            UserViewModel user = this.mapper.map(u, UserViewModel.class);
            List<String> authorities = u.getAuthorities().stream().map(RoleServiceModel::getAuthority).collect(Collectors.toList());
            user.setAuthorities(authorities);
            return user;
        }).collect(Collectors.toList());

        modelAndView.addObject("users", users);
        modelAndView.setViewName("user/all-users");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @PostMapping("/users/set-user/{id}")
    public String demoteAdminToUser(@PathVariable String id) {
        this.userService.demoteAdmin(id);
        return "redirect:/users/all-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/set-admin/{id}")
    public String promoteUserToAdmin(@PathVariable String id) {
        this.userService.promoteAdmin(id);
        return "redirect:/users/all-users";
    }
}
