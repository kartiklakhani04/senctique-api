package org.uwl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uwl.entity.Favorite;
import org.uwl.entity.Product;
import org.uwl.service.FavoriteService;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

  @Autowired private FavoriteService favoriteService;

  @PostMapping("/{userId}/{productId}")
  public ResponseEntity<Void> add(@PathVariable Long userId, @PathVariable Long productId) {
    final Favorite favorite = new Favorite();
    favorite.setUserId(userId);
    favorite.setProductId(productId);
    favoriteService.save(favorite);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{userId}/{productId}")
  public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long productId) {
    favoriteService.delete(userId, productId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{userId}/{productId}")
  public ResponseEntity<Boolean> isFavorited(
      @PathVariable Long userId, @PathVariable Long productId) {
    boolean isFavorited = favoriteService.isFavorited(userId, productId);
    return ResponseEntity.ok(isFavorited);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<Product>> getFavorites(@PathVariable Long userId) {
    List<Product> favoriteProducts = favoriteService.getFavoriteProductsByUserId(userId);
    return ResponseEntity.ok(favoriteProducts);
  }
}
