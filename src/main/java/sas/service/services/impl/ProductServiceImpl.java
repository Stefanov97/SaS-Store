package sas.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sas.data.models.*;
import sas.data.repositories.ProductRepository;
import sas.service.models.*;
import sas.service.services.CloudinaryService;
import sas.service.services.ProductService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String REFRIGERATOR  = "refrigerator";
    private static final String AIR_CONDITIONER  = "air-conditioner";
    private static final String BOILER  = "boiler";
    private static final String WASHING_MACHINE  = "washing-machine";
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public ProductServiceModel getById(String id) {
        return this.mapper.map(this.productRepository.findById(id),ProductServiceModel.class);
    }

    @Override
    public void addRefrigerator(ProductServiceModel product) throws IOException {
        String imageUrl = this.cloudinaryService.uploadImage(product.getImage());
        Refrigerator refrigerator = this.mapper.map(product,Refrigerator.class);
        refrigerator.setImageUrl(imageUrl);
        this.productRepository.saveAndFlush(refrigerator);
    }

    @Override
    public void addWashingMachine(ProductServiceModel product) throws IOException {
        String imageUrl = this.cloudinaryService.uploadImage(product.getImage());
        WashingMachine washingMachine = this.mapper.map(product,WashingMachine.class);
        washingMachine.setImageUrl(imageUrl);
        this.productRepository.saveAndFlush(washingMachine);
    }

    @Override
    public void addBoiler(ProductServiceModel product) throws IOException {
        String imageUrl = this.cloudinaryService.uploadImage(product.getImage());
        Boiler boiler = this.mapper.map(product,Boiler.class);
        boiler.setImageUrl(imageUrl);
        this.productRepository.saveAndFlush(boiler);
    }

    @Override
    public void addAirConditioner(ProductServiceModel product) throws IOException {
        String imageUrl = this.cloudinaryService.uploadImage(product.getImage());
        AirConditioner airConditioner = this.mapper.map(product,AirConditioner.class);
        airConditioner.setImageUrl(imageUrl);
        this.productRepository.saveAndFlush(airConditioner);
    }

    @Override
    public List<RefrigeratorServiceModel> getAllRefrigerators() {
        List<Product> refrigerators = this.productRepository.findAllByType(REFRIGERATOR);
        return refrigerators.stream().map(refrigerator-> this.mapper.map(refrigerator,RefrigeratorServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<AirConditionerServiceModel> getAllAirConditioners() {
        List<Product> airConditioners = this.productRepository.findAllByType(AIR_CONDITIONER);
        return airConditioners.stream().map(airConditioner-> this.mapper.map(airConditioner,AirConditionerServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<BoilerServiceModel> getAllBoilers() {
        List<Product> boilers = this.productRepository.findAllByType(BOILER);
        return boilers.stream().map(boiler-> this.mapper.map(boiler,BoilerServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<WashingMachineServiceModel> getAllWashingMachines() {
        List<Product> washingMachines = this.productRepository.findAllByType(WASHING_MACHINE);
        return washingMachines.stream().map(washingMachine-> this.mapper.map(washingMachine,WashingMachineServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel getProductByModel(String model) {
        return this.mapper.map(this.productRepository.findByModel(model),ProductServiceModel.class);
    }

    @Override
    @Transactional
    public void deleteByModel(String model) {
        this.productRepository.deleteByModel(model);
    }
}
