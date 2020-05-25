package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sas.service.models.*;
import sas.service.services.CloudinaryService;
import sas.service.services.ProductService;
import sas.web.models.ProductCreateModel;
import sas.web.models.ProductEditModel;
import sas.web.models.ProductViewModel;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductService productService;
    private final ModelMapper mapper;

    public ProductController(ProductService productService, ModelMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/products/create")
    public String addProduct() {

        return "/product/add-product";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/create")
    public String addProduct(@ModelAttribute ProductCreateModel model, HttpSession session) {
        session.setAttribute("product", model);
        return "/product/add-" + model.getType().toLowerCase();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/products/create/refrigerator")
    public String addRefrigerator() {
        return "/product/add-refrigerator";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/create/refrigerator")
    public String addRefrigeratorConfirm(@ModelAttribute ProductCreateModel refrigerator) throws IOException {
        this.productService.addRefrigerator(this.mapper.map(refrigerator, ProductServiceModel.class));
        return "redirect:/products/create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/products/create/washing-machine")
    public String addWashingMachine() {
        return "/product/add-washing-machine";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/create/washing-machine")
    public String addWashingMachineConfirm(@ModelAttribute ProductCreateModel washingMachine) throws IOException {
        this.productService.addWashingMachine(this.mapper.map(washingMachine, ProductServiceModel.class));
        return "redirect:/products/create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/products/create/boiler")
    public String addBoiler() {
        return "/product/add-boiler";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/create/boiler")
    public String addBoilerConfirm(@ModelAttribute ProductCreateModel boiler) throws IOException {
        this.productService.addBoiler(this.mapper.map(boiler, ProductServiceModel.class));
        return "redirect:/products/create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/products/create/air-conditioner")
    public String addAirConditioner() {
        return "/product/add-air-conditioner";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/create/air-conditioner")
    public String addAirConditionerConfirm(@ModelAttribute ProductCreateModel airConditioner) throws IOException {
        this.productService.addAirConditioner(this.mapper.map(airConditioner, ProductServiceModel.class));
        return "redirect:/products/create";
    }

    @GetMapping("products/refrigerators")
    public ModelAndView showRefrigerators(ModelAndView modelAndView) {
        List<ProductViewModel> refrigerators = this.productService.getAllRefrigerators().stream().map(r -> this.mapper.map(r, ProductViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("products", refrigerators);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/air-conditioners")
    public ModelAndView showAirConditioners(ModelAndView modelAndView) {
        List<ProductViewModel> airConditioners = this.productService.getAllAirConditioners().stream().map(ac -> this.mapper.map(ac, ProductViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("products", airConditioners);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/boilers")
    public ModelAndView showBoilers(ModelAndView modelAndView) {
        List<ProductViewModel> boilers = this.productService.getAllBoilers().stream().map(b -> this.mapper.map(b, ProductViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("products", boilers);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/washing-machines")
    public ModelAndView showWashingMachines(ModelAndView modelAndView) {
        List<ProductViewModel> washingMachines = this.productService.getAllWashingMachines().stream().map(wm -> this.mapper.map(wm, ProductViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("products", washingMachines);
        modelAndView.setViewName("/product/show-products");
        return modelAndView;
    }

    @GetMapping("products/details/{model}")
    public String getProductDetails(@PathVariable String model, HttpSession session) {
        ProductCreateModel product = this.mapper.map(this.productService.getByModel(model), ProductCreateModel.class);
        session.setAttribute("detailsModel", product);
        return ("/product/details-" + product.getType().toLowerCase());

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("products/delete/{model}")
    public ModelAndView deleteProductByModel(@PathVariable String model, ModelAndView modelAndView) {
        ProductCreateModel product = this.mapper.map(this.productService.getByModel(model), ProductCreateModel.class);
        String url = "/products/" + product.getType() + "s";
        this.productService.deleteByModel(model);
        modelAndView.setViewName(String.format("redirect:%s", url));
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("products/edit/{model}")
    public ModelAndView editProduct(@PathVariable String model, ModelAndView modelAndView) {
        ProductEditModel product = this.mapper.map(this.productService.getByModel(model), ProductEditModel.class);
        modelAndView.addObject("editProduct", product);
        modelAndView.setViewName("/product/edit-product");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("products/edit")
    public String editProduct(@ModelAttribute ProductEditModel editModel) throws IOException {
        this.productService.editProduct(this.mapper.map(editModel, ProductServiceModel.class));
        return ("redirect:/products/" + editModel.getType().toLowerCase() + "s");
    }
}
