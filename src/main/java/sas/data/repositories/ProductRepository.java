package sas.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sas.data.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findAllByType(String type);

    Product findByModel(String model);

    void deleteByModel(String model);
}
