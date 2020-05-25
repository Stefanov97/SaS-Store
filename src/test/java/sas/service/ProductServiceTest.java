package sas.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import sas.base.BaseTest;
import sas.data.models.Product;
import sas.data.repositories.ProductRepository;
import sas.service.models.ProductServiceModel;
import sas.service.services.ProductService;

import java.io.IOException;
import java.math.BigDecimal;

public class ProductServiceTest extends BaseTest {
    private final static BigDecimal BASE_PRICE = new BigDecimal(1);
    private final static BigDecimal NEW_PRICE = new BigDecimal(5);
    private final static String BASE_MODEL = "My Model";
    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    void updateProductShouldUpdatePriceCorrectly() throws IOException {
        Product myProduct = new Product();
        myProduct.setModel(BASE_MODEL);
        myProduct.setPrice(BASE_PRICE);

        Mockito.when(this.productRepository.findByModel(BASE_MODEL)).thenReturn(myProduct);
        ProductServiceModel model = new ProductServiceModel();
        model.setModel(BASE_MODEL);
        model.setPrice(NEW_PRICE);
        this.productService.editProduct(model);
        boolean matches = myProduct.getPrice().compareTo(model.getPrice()) == 0;
        Assert.assertTrue(matches);
    }
}
