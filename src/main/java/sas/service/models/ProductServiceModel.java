package sas.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceModel {
    private String type;
    private String origin;
    private String brand;
    private String model;
    private String color;
    private BigDecimal price;
    private String energyClass;
    private double height;
    private double width;
    private double depth;
    private double powerConsumption;
    private int liters;
    private int doorsCount;
    private boolean noFrost;
    private boolean hasWaterDispenser;
    private String refrigerant;
    private MultipartFile image;
    private String imageUrl;
    private int recommendedVolumeForCooling;
    private int recommendedVolumeForHeating;
    private double coolingPowerOutput;
    private double heatingPowerOutput;
    private String installation;
    private int capacity;
}
