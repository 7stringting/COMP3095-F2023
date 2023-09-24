package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    // Define service methods
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();

    void updateProduct(ProductRequest productRequest);

    void deleteProduct(String id);

}
