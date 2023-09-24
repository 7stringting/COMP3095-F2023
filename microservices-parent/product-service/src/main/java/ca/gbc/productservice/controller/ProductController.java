package ca.gbc.productservice.controller;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Import List

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping("/getAllProducts") // New endpoint
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        // Call ProductService to retrieve all products and map them to ProductResponse
        return productService.getAllProducts();
    }

    @PutMapping("/updateProduct") // New endpoint
    public ResponseEntity<Void> updateProduct(@RequestBody ProductRequest productRequest) {
        productService.updateProduct(productRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}
