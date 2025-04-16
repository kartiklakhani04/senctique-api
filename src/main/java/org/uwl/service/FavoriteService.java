package org.uwl.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.Favorite;
import org.uwl.entity.Product;
import org.uwl.repository.FavoriteRepository;
import org.uwl.repository.ProductRepository;

@Service
public class FavoriteService {

  @Autowired private FavoriteRepository favoriteRepository;
  @Autowired private ProductRepository productRepository;

  @Transactional
  public Favorite save(final Favorite favorite) {
    return favoriteRepository.save(favorite);
  }

  @Transactional
  public void delete(Long userId, Long productId) {
    Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId);
    if (favorite != null) {
      favoriteRepository.delete(favorite);
    }
  }

  @Transactional(readOnly = true)
  public boolean isFavorited(Long userId, Long productId) {
    return favoriteRepository.existsByUserIdAndProductId(userId, productId);
  }

  @Transactional(readOnly = true)
  public List<Product> getFavoriteProductsByUserId(Long userId) {
    List<Favorite> favorites = favoriteRepository.findByUserId(userId);
    return productRepository.findByIdIn(favorites.stream().map(Favorite::getProductId).toList());
  }
}
