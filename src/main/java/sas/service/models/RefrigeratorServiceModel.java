package sas.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RefrigeratorServiceModel {
    private String brand;
    private String model;
    private String imageUrl;
    private String type;
    private String origin;
    private String color;
    private BigDecimal price;
    private String energyClass;
    private double height;
    private double width;
    private double depth;
    private double powerConsumption;
    private int doorsCount;
    private int liters;
    private boolean noFrost;
    private boolean hasWaterDispenser;
    private String refrigerant;
}
