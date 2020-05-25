package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCartModel {
    private ProductViewModel product;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of ProductViewModel or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof ProductCartModel)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ProductCartModel product = (ProductCartModel) o;

        // Compare the data members and return accordingly
        return this.product.getModel().equals(product.getProduct().getModel());
    }
}
