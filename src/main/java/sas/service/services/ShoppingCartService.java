package sas.service.services;

import sas.web.models.ProductCartModel;
import sas.web.models.ProductViewModel;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ShoppingCartService {
    ProductViewModel addProduct(HttpSession session, String model, Integer quantity);

    void removeProduct(HttpSession session, String model);

    List<ProductCartModel> getProducts(HttpSession session);
}
