package org.uwl.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.Subscription;
import org.uwl.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

  @Autowired private SubscriptionRepository subscriptionRepository;

  @Transactional
  public Subscription save(Subscription subscription) {
    subscription.setDate(new Date());
    return subscriptionRepository.save(subscription);
  }
}
