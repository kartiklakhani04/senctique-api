package org.uwl.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.uwl.email.UserEmail;
import org.uwl.entity.User;
import org.uwl.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserService userService;
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private UserEmail userEmail;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody User user) {
    String plainPassword = user.getPassword();
    User savedUser = userService.saveUser(user);
    userEmail.sendRegistrationEmail(savedUser, plainPassword);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(
      @RequestBody final User user, final HttpServletRequest request) {
    User existingUser = userService.getUserByEmail(user.getEmail());
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(existingUser, user.getPassword()));
    final HttpSession httpSession = request.getSession(true);
    httpSession.setAttribute("LoggedInUserEmail", existingUser.getEmail());
    final Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", "Login successful!");
    responseMap.put("userId", existingUser.getId());
    return ResponseEntity.ok(responseMap);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return ResponseEntity.ok("Logout successful!");
  }

  @GetMapping("/loggedInUser")
  public ResponseEntity<User> loggedInUser() {
    final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User user) {
      user.setPassword(null);
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @PostMapping(path = "/forgotPassword")
  public ResponseEntity<Boolean> forgotPassword(@RequestBody User user) {
    final String newPassword = UUID.randomUUID().toString().substring(0, 8);
    final User userDB = userService.forgotPassword(user.getEmail(), newPassword);
    if (userDB != null) {
      CompletableFuture.runAsync(() -> userEmail.sendForgetPasswordEmail(userDB, newPassword));
    }
    return ResponseEntity.ok(userDB != null);
  }

  @PostMapping("/changePassword")
  public ResponseEntity<String> changePassword(
      @RequestBody Map<String, String> passwordMap, HttpServletRequest request) {
    String oldPassword = passwordMap.get("oldPassword");
    String newPassword = passwordMap.get("newPassword");

    final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User user) {
      boolean updated = userService.changePassword(user.getEmail(), oldPassword, newPassword);
      if (updated) {
        return ResponseEntity.ok("Password changed successfully.");
      } else {
        return ResponseEntity.badRequest().body("Old password is incorrect.");
      }
    }
    return ResponseEntity.status(403).body("Unauthorized.");
  }
}
