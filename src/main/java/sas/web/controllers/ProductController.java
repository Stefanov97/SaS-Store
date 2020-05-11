package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sas.data.models.Refrigerator;
import sas.service.models.*;
import sas.service.services.CloudinaryService;
import sas.service.services.ProductService;
import sas.web.models.ProductCreateModel;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
private final ProductService productService;
private final ModelMapper mapper;

    public ProductController(ProductService productService, ModelMapper mapper, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("/products/create")
    public String addProduct(){

        return "/product/add-product";
    }

    @PostMapping("/products/create")
    public ModelAndView addProduct(ModelAndView modelAndView, @ModelAttribute ProductCreateModel model, HttpSession session){
        session.setAttribute("product", model);
        modelAndView.setViewName("/product/add-" + model.getType().toLowerCase());
        return modelAndView;
    }

    @GetMapping("/products/create/refrigerator")
    public String addRefrigerator(){
       return "/product/add-refrigerator";
    }

    @PostMapping("/products/create/refrigerator")
    public ModelAndView addRefrigeratorConfirm(ModelAndView modelAndView, @ModelAttribute ProductCreateModel refrigerator ) throws IOException {
        modelAndView.setViewName("redirect:/products/create");
        this.productService.addRefrigerator(this.mapper.map(refrigerator, ProductServiceModel.class));
       return modelAndView;
    }


    @GetMapping("/products/create/washing-machine")
    public String addWashingMachine(){
      return "/product/add-washing-machine";
    }

    @PostMapping("/products/create/washing-machine")
    public ModelAndView addWashingMachineConfirm(ModelAndView modelAndView, @ModelAttribute ProductCreateModel washingMachine ) throws IOException {
        modelAndView.setViewName("redirect:/products/create");
        this.productService.addWashingMachine(this.mapper.map(washingMachine, ProductServiceModel.class));
        return modelAndView;
    }

    @GetMapping("/products/create/boiler")
    public String addBoiler(){
       return "/product/add-boiler";
    }
    @PostMapping("/products/create/boiler")
    public ModelAndView addBoilerConfirm(ModelAndView modelAndView, @ModelAttribute ProductCreateModel boiler ) throws IOException {
        modelAndView.setViewName("redirect:/products/create");
        this.productService.addBoiler(this.mapper.map(boiler, ProductServiceModel.class));
        return modelAndView;
    }

    @GetMapping("/products/create/air-conditioner")
    public String addAirConditioner(){
       return "/product/add-air-conditioner";
    }

    @PostMapping("/products/create/air-conditioner")
    public ModelAndView addAirConditionerConfirm(ModelAndView modelAndView, @ModelAttribute ProductCreateModel airConditioner ) throws IOException {
        modelAndView.setViewName("redirect:/products/create");
        this.productService.addAirConditioner(this.mapper.map(airConditioner, ProductServiceModel.class));
        return modelAndView;
    }

    @GetMapping("products/refrigerators")
    public ModelAndView showRefrigerators(ModelAndView modelAndView){
       List<RefrigeratorServiceModel> refrigerators = this.productService.getAllRefrigerators();
       modelAndView.addObject("products",refrigerators);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/air-conditioners")
    public ModelAndView showAirConditioners(ModelAndView modelAndView){
        List<AirConditionerServiceModel> airConditioners = this.productService.getAllAirConditioners();
        modelAndView.addObject("products",airConditioners);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/boilers")
    public ModelAndView showBoilers(ModelAndView modelAndView){
        List<BoilerServiceModel> boilers = this.productService.getAllBoilers();
        modelAndView.addObject("products",boilers);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/washing-machines")
    public ModelAndView showWashingMachines(ModelAndView modelAndView){
        List<WashingMachineServiceModel> washingMachines = this.productService.getAllWashingMachines();
        modelAndView.addObject("products",washingMachines);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/details/{model}")
    public String getProductDetails(@PathVariable String model, ModelAndView modelAndView, HttpSession session){
        ProductCreateModel product = this.mapper.map(this.productService.getProductByModel(model), ProductCreateModel.class);
        session.setAttribute("detailsModel", product);
       return ("/product/details-" + product.getType().toLowerCase());

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("products/delete/{model}")
    public ModelAndView deleteProductByModel(@PathVariable String model, ModelAndView modelAndView){
        ProductCreateModel product = this.mapper.map(this.productService.getProductByModel(model), ProductCreateModel.class);
        String url = "/products/" + product.getType() + "s";
        this.productService.deleteByModel(model);
        modelAndView.setViewName(String.format("redirect:%s",url));
        return modelAndView;
    }
}
