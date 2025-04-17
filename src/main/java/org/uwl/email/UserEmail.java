package org.uwl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.uwl.entity.User;

@Component
public class UserEmail {

  @Autowired private JavaMailSender javaMailSender;

  public void sendRegistrationEmail(final User user, final String password) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("kartiklakhani05@gmail.com");
      message.setTo(user.getEmail());
      message.setSubject("Welcome to Senctique - Registration Successful");

      String body =
          String.format("Dear %s,\n\n", user.getFirstName())
              + "Thank you for registering with Senctique! We're excited to have you as part of our community.\n\n"
              + "Here are your account details:\n\n"
              + String.format("Username: %s\n", user.getEmail())
              + "You can now log in to your account and start exploring our products.\n\n"
              + "Thank you for choosing Senctique. We look forward to serving you!\n\n"
              + "Best regards,\nSenctique\n"
              + "\n--------------------------------------------------\n"
              + "Senctique | Your Premium Shopping Experience\n"
              + "Visit Us: http://185.181.11.152:5173\n"
              + "Follow Us: @SenctiqueShop on Social Media\n";

      message.setText(body);
      javaMailSender.send(message);
    } catch (Exception e) {
      System.out.println("Error sending email: " + e.getMessage());
    }
  }

  public void sendForgetPasswordEmail(final User user, final String newPassword) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("kartiklakhani05@gmail.com");
      message.setTo(user.getEmail());
      message.setSubject("Your New Password - Senctique");

      String body =
          String.format("Dear %s,\n\n", user.getFirstName())
              + "We have generated a new password for you. Please use the following details to log in:\n\n"
              + String.format("Username: %s\n", user.getEmail())
              + String.format("Password: %s\n\n", newPassword)
              + "Please log in and change it as soon as possible.\n\n"
              + "Thank you,\nSenctique";

      message.setText(body);
      javaMailSender.send(message);
    } catch (Exception e) {
      System.out.println("Error sending new password email: " + e.getMessage());
    }
  }
}
