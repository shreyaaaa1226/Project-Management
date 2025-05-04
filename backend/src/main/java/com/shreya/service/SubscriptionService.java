package com.shreya.service;

import com.shreya.domain.PlanType;
import com.shreya.model.Subscription;
import com.shreya.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUserSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
