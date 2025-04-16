package org.uwl.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.Product;
import org.uwl.repository.ProductRepository;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Product findById(Long id) {
    return productRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Product> getProductsByCategoryAndGender(
      List<Product.Category> categories, List<Product.Gender> gender) {
    return productRepository.findByCategoryAndGender(categories, gender);
  }

  @Transactional(readOnly = true)
  public List<Product> searchProducts(String search) {
    return productRepository.searchProducts(search);
  }

  @Transactional(readOnly = true)
  public List<Product> getRandomProducts() {
    return productRepository.findRandomProducts();
  }

  @Transactional(readOnly = true)
  public List<Product> filterProducts(
      Double minPrice,
      Double maxPrice,
      List<String> concentration,
      List<String> size,
      List<String> fragranceType,
      List<String> sillage) {
    return productRepository.filterProducts(
        minPrice, maxPrice, concentration, size, fragranceType, sillage);
  }
}
