package ca.gbc.productservice.service;

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
        Product product = mapProductRequestToProduct(productRequest);
        productRepository.save(product);
        log.info("Created a new product: {}", product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .collect(Collectors.toList());
    }

    private Product mapProductRequestToProduct(ProductRequest productRequest) {

        return new Product();
    }

    @Override
    public void updateProduct(ProductRequest productRequest) {
        Optional<Product> existingProduct = productRepository.findById(productRequest.getId());
        if (existingProduct.isEmpty()) {
            log.error("Product with id '{}' not found for update.", productRequest.getId());
        } else {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setName(productRequest.getName());
            productToUpdate.setDescription(productRequest.getDescription());
            productToUpdate.setPrice(productRequest.getPrice());
            productRepository.save(productToUpdate);
            log.info("Updated product with id: {}", productRequest.getId());
        }
    }
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
        log.info("Deleted product with id: {}", id);
    }

}
