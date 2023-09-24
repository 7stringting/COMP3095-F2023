package ca.gbc.productservice.repository; // Update with your actual package name

import ca.gbc.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List; // Import List

public interface ProductRepository extends MongoRepository<Product, String> {
        // Correct the return type to List<Product>
        List<Product> findAll();
}
