package ca.gbc.productservice.service; // Update with your actual package name

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.model.Product;
import ca.gbc.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        // Map the ProductRequest to a Product entity
        Product product = mapProductRequestToProduct(productRequest);

        // Save the product to the datastore via ProductRepository
        productRepository.save(product);

        // Log the transaction
        log.info("Created a new product: {}", product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        // Fetch all products from the repository and map to ProductResponse
        List<Product> products = productRepository.findAll();
        // Implement mapping logic to convert Product to ProductResponse
        // Return a list of ProductResponse objects
        // Example:
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .collect(Collectors.toList());
    }

    private Product mapProductRequestToProduct(ProductRequest productRequest) {
        // Implement the mapping logic here
        // Create a new Product instance and set its attributes based on the productRequest
        // Return the mapped Product
        return new Product(/* Set attributes based on productRequest */);
    }

    @Override
    public void updateProduct(ProductRequest productRequest) {
        // Get the product by id from the repository
        Optional<Product> existingProduct = productRepository.findById(productRequest.getId());

        // Check if the product exists
        if (existingProduct.isEmpty()) {
            // Handle the case when the product doesn't exist (e.g., throw an exception or return an error response)
            // You can customize this based on your application's requirements.
            log.error("Product with id '{}' not found for update.", productRequest.getId());
            // You may want to throw an exception or return an error response here
        } else {
            // Update the attributes of the existing product based on the productRequest
            Product productToUpdate = existingProduct.get();
            productToUpdate.setName(productRequest.getName());
            productToUpdate.setDescription(productRequest.getDescription());
            productToUpdate.setPrice(productRequest.getPrice());

            // Save the updated product to the repository
            productRepository.save(productToUpdate);

            // Log the transaction
            log.info("Updated product with id: {}", productRequest.getId());
        }
    }

    public void deleteProduct(String id) {
        // Delete the product by id from the repository
        productRepository.deleteById(id);
        // Log the transaction
        log.info("Deleted product with id: {}", id);
    }


}
