package sas.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sas.service.services.ProductService;
import sas.service.services.ShoppingCartService;
import sas.web.models.ProductCartModel;
import sas.web.models.ProductViewModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ProductService productService;
    private final ModelMapper mapper;

    public ShoppingCartServiceImpl(ProductService productService, ModelMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @Override
    public ProductViewModel addProduct(HttpSession session, String model, Integer quantity) {
        ProductViewModel product = this.mapper.map(this.productService.getByModel(model), ProductViewModel.class);
        ProductCartModel productCartModel = new ProductCartModel();
        productCartModel.setProduct(product);
        if(session.getAttribute("products") == null){
            List<ProductCartModel> products = new ArrayList<>();
            productCartModel.setQuantity(quantity);
            products.add(productCartModel);
            session.setAttribute("products", products);
        }else {
            List<ProductCartModel> products = this.getProducts(session);
            if(!products.contains(productCartModel)){
                productCartModel.setQuantity(quantity);
                products.add(productCartModel);
            }else {
                int index = products.indexOf(productCartModel);
                products.get(index).setQuantity(products.get(index).getQuantity() + quantity);
            }
        }
        return product;
    }

    @Override
    public void removeProduct(HttpSession session, String model) {
        List<ProductCartModel> products = this.getProducts(session);
        products.removeIf(p-> p.getProduct().getModel().equals(model));
        if(products.isEmpty()){
            session.removeAttribute("products");
        }
    }

    @Override
    public List<ProductCartModel> getProducts(HttpSession session) {
        return (List<ProductCartModel>) session.getAttribute("products");
    }
}
