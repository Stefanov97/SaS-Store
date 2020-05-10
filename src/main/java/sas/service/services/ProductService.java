package sas.service.services;

import sas.service.models.*;
import sas.web.models.ProductCreateModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductServiceModel getById(String id);

    void addRefrigerator(ProductServiceModel product) throws IOException;

    void addWashingMachine(ProductServiceModel product) throws IOException;

    void addBoiler(ProductServiceModel product) throws IOException;

    void addAirConditioner(ProductServiceModel product) throws IOException;

    List<RefrigeratorServiceModel> getAllRefrigerators();

    List<AirConditionerServiceModel> getAllAirConditioners();

    List<BoilerServiceModel> getAllBoilers();

    List<WashingMachineServiceModel> getAllWashingMachines();
}
