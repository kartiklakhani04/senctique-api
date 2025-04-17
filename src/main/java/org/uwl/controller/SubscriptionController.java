package org.uwl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uwl.entity.Subscription;
import org.uwl.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

  @Autowired private SubscriptionService subscriptionService;

  @PostMapping("/save")
  public ResponseEntity<Subscription> save(@RequestBody Subscription subscription) {
    return ResponseEntity.ok(subscriptionService.save(subscription));
  }
}
