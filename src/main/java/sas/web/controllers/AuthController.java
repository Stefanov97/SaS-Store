package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sas.service.models.UserServiceModel;
import sas.service.services.AuthService;
import sas.web.models.UserRegisterModel;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class AuthController {
    @ModelAttribute("userRegisterModel")
    public UserRegisterModel userRegisterModel() {
        return new UserRegisterModel();
    }
    private final ModelMapper mapper;
    private final AuthService authService;

    @Autowired
    public AuthController(ModelMapper mapper, AuthService authService){
        this.mapper = mapper;
        this.authService = authService;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterForm(ModelAndView modelAndView){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = this.authService.checkIfLogged(auth);
        if (isUserLogged) {
            return new ModelAndView("redirect:/home");
        }
        modelAndView.setViewName("/auth/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register( @Valid @ModelAttribute("userRegisterModel") UserRegisterModel registerModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
          return "/auth/register";
        }else {
            this.authService.register(this.mapper.map(registerModel, UserServiceModel.class));
          return "/auth/login";
        }

    }

    @GetMapping("/login")
    public ModelAndView getLoginForm(ModelAndView modelAndView){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = this.authService.checkIfLogged(auth);
        if (isUserLogged) {
            return new ModelAndView("redirect:/");
        }
        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }
}
