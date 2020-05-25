package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductEditModel {
    private String brand;
    private String model;
    private MultipartFile image;
    private BigDecimal price;
    private String type;
}
