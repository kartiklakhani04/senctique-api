package org.uwl.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uwl.entity.Product;
import org.uwl.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired private ProductService productService;

  @GetMapping("/findAll")
  public ResponseEntity<List<Product>> findAll() {
    return ResponseEntity.ok(productService.findAll());
  }

  @GetMapping("/findById/{id}")
  public ResponseEntity<Product> findById(@PathVariable Long id) {
    return Optional.ofNullable(productService.findById(id))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/findBYCategoryAndGender")
  public ResponseEntity<List<Product>> getProductsByCategoryAndGender(
      @RequestParam List<Product.Category> categories, @RequestParam List<Product.Gender> gender) {
    return ResponseEntity.ok(productService.getProductsByCategoryAndGender(categories, gender));
  }

  @GetMapping("/search")
  public ResponseEntity<List<Product>> search(@RequestParam String search) {
    return ResponseEntity.ok(productService.searchProducts(search));
  }

  @GetMapping("/random")
  public ResponseEntity<List<Product>> getRandomProducts() {
    return ResponseEntity.ok(productService.getRandomProducts());
  }

  @GetMapping("/filter")
  public ResponseEntity<List<Product>> filterProducts(
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice,
      @RequestParam(required = false) List<String> concentration,
      @RequestParam(required = false) List<String> size,
      @RequestParam(required = false) List<String> fragranceType,
      @RequestParam(required = false) List<String> sillage) {
    return ResponseEntity.ok(
        productService.filterProducts(
            minPrice, maxPrice, concentration, size, fragranceType, sillage));
  }
}
