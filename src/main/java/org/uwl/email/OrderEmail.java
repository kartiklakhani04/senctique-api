package org.uwl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.uwl.entity.Order;
import org.uwl.entity.OrderItem;

@Component
public class OrderEmail {

  @Autowired private JavaMailSender javaMailSender;

  public void sendOrderEmail(final Order order) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("kartiklakhani05@gmail.com");
      message.setTo(order.getUser().getEmail());
      message.setSubject(
          "Your Order Confirmation - Senctique Shop (Order ID: #" + order.getId() + ")");

      StringBuilder body = new StringBuilder();
      body.append(String.format("Dear %s,\n\n", order.getUser().getFirstName()));
      body.append(
          "Thank you for shopping with Senctique, We're happy to confirm your order. Here are the details:\n\n");
      body.append(String.format("Order ID: #%d\n", order.getId()));
      body.append("--------------------------------------------------\n");
      body.append(String.format("%-25s %-10s %-10s %-10s\n", "Product", "Price", "Qty", "Total"));
      body.append("--------------------------------------------------\n");

      for (OrderItem item : order.getOrderItems()) {
        String productName = item.getProduct().getName();
        double price = item.getPrice();
        long quantity = item.getQuantity();
        double total = item.getTotal();

        body.append(
            String.format("%-25s £%-9.2f %-10d £%-10.2f\n", productName, price, quantity, total));
      }

      body.append("--------------------------------------------------\n");
      body.append(String.format("Order Total: £%.2f\n\n", order.getTotal()));
      body.append("Your order is now being processed and will be shipped to you shortly.\n");
      body.append("We'll send another email once your items are on their way.\n\n");
      body.append("Thank you for choosing Senctique!\n\n");
      body.append("Warm regards,\nSenctique");

      message.setText(body.toString());
      javaMailSender.send(message);
    } catch (Exception e) {
      System.out.println("Error sending email: " + e.getMessage());
    }
  }
}
