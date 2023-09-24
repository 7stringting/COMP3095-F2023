package ca.gbc.productservice.repository;

import ca.gbc.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

        List<Product> findAll();
}
