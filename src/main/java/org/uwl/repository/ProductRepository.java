package org.uwl.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.uwl.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByIdIn(List<Long> ids);

  @Query("SELECT p FROM Product p WHERE p.gender IN :gender AND p.category IN :categories")
  List<Product> findByCategoryAndGender(
      @Param("categories") List<Product.Category> categories,
      @Param("gender") List<Product.Gender> gender);

  @Query(
      "SELECT p FROM Product p WHERE "
          + "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "CAST(p.price AS string) LIKE CONCAT('%', :search, '%') OR "
          + "LOWER(p.topNotes) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.middleNotes) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.baseNotes) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.concentration) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.longevity) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.size) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "CAST(p.rating AS string) LIKE CONCAT('%', :search, '%') OR "
          + "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.sillage) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.fragranceType) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.usage) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.packaging) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.ingredients) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.category) LIKE LOWER(CONCAT('%', :search, '%')) OR "
          + "LOWER(p.gender) LIKE LOWER(CONCAT('%', :search, '%')) "
          + "ORDER BY "
          + "CASE "
          + "    WHEN LOWER(p.name) LIKE LOWER(CONCAT(:search, '%')) THEN 1 "
          + "    WHEN LOWER(p.brand) LIKE LOWER(CONCAT(:search, '%')) THEN 2 "
          + "    ELSE 3 "
          + "END, "
          + "LOWER(p.name) ASC")
  List<Product> searchProducts(@Param("search") String search);

  @Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT 3", nativeQuery = true)
  List<Product> findRandomProducts();

  @Query(
      "SELECT p FROM Product p WHERE "
          + "(:minPrice IS NULL OR p.price >= :minPrice) AND "
          + "(:maxPrice IS NULL OR p.price <= :maxPrice) AND "
          + "(:concentration IS NULL OR p.concentration IN :concentration) AND "
          + "(:size IS NULL OR p.size IN :size) AND "
          + "(:fragranceType IS NULL OR p.fragranceType IN :fragranceType) AND "
          + "(:sillage IS NULL OR p.sillage IN :sillage)")
  List<Product> filterProducts(
      @Param("minPrice") Double minPrice,
      @Param("maxPrice") Double maxPrice,
      @Param("concentration") List<String> concentration,
      @Param("size") List<String> size,
      @Param("fragranceType") List<String> fragranceType,
      @Param("sillage") List<String> sillage);
}
