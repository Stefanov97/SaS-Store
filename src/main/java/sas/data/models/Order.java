package sas.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    private User customer;
   
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @OneToMany
    private List<Product> products;
}
