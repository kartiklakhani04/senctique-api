package org.uwl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.Order;
import org.uwl.entity.User;
import org.uwl.repository.OrderRepository;
import java.util.List;

@Service
public class OrderService {

  @Autowired private OrderRepository orderRepository;

  @Transactional
  public Order save(Order order) {
    order.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    order.getOrderItems().forEach(item -> item.setOrder(order));
    return orderRepository.save(order);
  }

  public List<Order> getOrdersByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }
}
