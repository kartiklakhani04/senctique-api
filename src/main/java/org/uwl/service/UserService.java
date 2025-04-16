package org.uwl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.User;
import org.uwl.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Transactional
  public User saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Transactional
  public User forgotPassword(final String email, final String newPassword) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user);
      return user;
    }
    return null;
  }

  @Transactional
  public boolean changePassword(String email, String oldPassword, String newPassword) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (passwordEncoder.matches(oldPassword, user.getPassword())) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
      } else {
        return false;
      }
    }
    return false;
  }
}
