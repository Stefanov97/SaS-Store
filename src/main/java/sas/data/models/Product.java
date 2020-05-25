package sas.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends BaseEntity {
    @Column(name = "image_url")
    private String imageUrl;
    @Column
    private String type;
    @Column
    private String origin;
    @Column
    private String brand;
    @Column
    private String model;
    @Column
    private String color;
    @Column
    private BigDecimal price;
    @Column
    private String energyClass;
    @Column
    private double height;
    @Column
    private double width;
    @Column
    private double depth;
    @Column(name = "power_consumption")
    private double powerConsumption;
}
