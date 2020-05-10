package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import sas.service.models.UserServiceModel;
import sas.service.services.UserService;
import sas.web.models.UserLoginModel;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;
    private final ModelMapper mapper;

    public HomeController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ModelAndView getIndex(ModelAndView modelAndView, Principal principal, HttpSession session){
      if(principal != null){
          String username = principal.getName();
          UserServiceModel userServiceModel = this.userService.getByUsername(username);
          UserLoginModel user = this.mapper.map(userServiceModel,UserLoginModel.class);
          modelAndView.addObject("user",user);
          session.setAttribute("user",user);

      }
        modelAndView.setViewName("/home/index");
        return modelAndView;
    }

    @GetMapping("/contacts")
    public ModelAndView getContactPage(ModelAndView modelAndView){
        modelAndView.setViewName("/home/contacts");
        return modelAndView;
    }
}
