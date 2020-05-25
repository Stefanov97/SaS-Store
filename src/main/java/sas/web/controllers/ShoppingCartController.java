package sas.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sas.service.services.ShoppingCartService;
import sas.web.models.AddToCartModel;
import sas.web.models.ProductViewModel;

import javax.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    public ShoppingCartController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("shopping-cart")
    public String getShoppingCart() {
        return "/cart/shopping-cart";
    }

    @PostMapping("add-to-cart/{model}")
    public String addToCardConfirm(@ModelAttribute AddToCartModel addToCartModel, HttpSession session) {
        ProductViewModel product = this.cartService.addProduct(session, addToCartModel.getModel(), addToCartModel.getQuantity());
        return ("redirect:/products/" + product.getType().toLowerCase() + "s");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("remove-from-cart/{model}")
    public String removeFromCard(@PathVariable String model, HttpSession session) {
        this.cartService.removeProduct(session, model);
        return "/cart/shopping-cart";
    }
}
