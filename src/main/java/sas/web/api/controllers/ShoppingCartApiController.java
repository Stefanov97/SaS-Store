package sas.web.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sas.service.services.ShoppingCartService;
import sas.web.models.ProductCartModel;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ShoppingCartApiController {
    private final ShoppingCartService cartService;

    public ShoppingCartApiController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("api/shopping-cart")
    public ResponseEntity<List<ProductCartModel>> getShoppingCart(HttpSession session) {
        List<ProductCartModel> products = this.cartService.getProducts(session);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
