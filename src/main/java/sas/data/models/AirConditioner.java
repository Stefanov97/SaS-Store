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
@Table(name = "air_conditioners")
public class AirConditioner extends Product {
    @Column(name = "recommended_volume_cooling")
    private int recommendedVolumeForCooling;
    @Column(name = "recommended_volume_heating")
    private int recommendedVolumeForHeating;
    @Column(name = "cooling_power_output")
    private double coolingPowerOutput;
    @Column(name = "heating_power_output")
    private double heatingPowerOutput;
    @Column
    private String refrigerant;
}
