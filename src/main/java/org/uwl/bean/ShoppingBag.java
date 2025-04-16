package org.uwl.bean;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.uwl.entity.Order;

@Component
@SessionScope
@Data
public class ShoppingBag {

  private Order order = new Order();

  public void clean() {
    order = new Order();
  }
}
