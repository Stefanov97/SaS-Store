package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sas.service.models.UserServiceModel;
import sas.service.services.UserService;
import sas.web.models.UserEditProfileModel;
import sas.web.models.UserLoginModel;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {
    @ModelAttribute("userEditModel")
    public UserEditProfileModel userEditProfileModel() {
        return new UserEditProfileModel();
    }
    private final UserService userService;
    private final ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("users/profile")
    public ModelAndView getUserProfile(ModelAndView modelAndView, Principal principal){
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.getByUsername(username);
        UserLoginModel user = this.mapper.map(userServiceModel,UserLoginModel.class);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("/user/profile");
        return modelAndView;
    }
    @GetMapping("users/edit")
    public ModelAndView  getUserEditProfile(ModelAndView modelAndView, Principal principal, HttpSession session){
        String username = principal.getName();
        UserServiceModel user = this.userService.getByUsername(username);
        session.setAttribute("user", user );
        modelAndView.setViewName("/user/edit-profile");
        return modelAndView;
    }

    @PostMapping("users/edit")
    public String getUserEditProfileConfirm(@Valid @ModelAttribute("userEditModel") UserEditProfileModel model, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/user/edit-profile";
        }else {
           this.userService.updateUser(this.mapper.map(model,UserServiceModel.class));
            return "redirect:/users/profile";
        }
    }
}
