package sas.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refrigerators")
public class Refrigerator extends Product {
    @Column
    private int doorsCount;
    @Column
    private int liters;
    @Column(name = "is_no_frost")
    private boolean noFrost;
    @Column
    private boolean hasWaterDispenser;
    @Column
    private String refrigerant;

}
