package sas.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sas.web.models.ProductCartModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderServiceModel {
    private String id;
    private BigDecimal totalPrice;
    private List<ProductCartModel> products;
    private LocalDateTime createdOn;
    private int orderNumber;
}
