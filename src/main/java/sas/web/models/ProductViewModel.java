package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class ProductViewModel {
    private String model;
    private String brand;
    private String imageUrl;
    private BigDecimal price;
    private String type;


    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof ProductViewModel)) {
            return false;
        }

        ProductViewModel product = (ProductViewModel) o;

        return this.model.equals(product.model);
    }

    @Override
    public int hashCode() {
        String model = this.getModel();
        int modelCharSum = 0;
        for (int i = 0; i < model.length(); i++) {
            modelCharSum += model.charAt(i);
        }
        String brand = this.getBrand();
        int brandCharSum = 0;
        for (int i = 0; i < brand.length(); i++) {
            brandCharSum += brand.charAt(i);
        }
        return modelCharSum * brandCharSum;
    }
}



