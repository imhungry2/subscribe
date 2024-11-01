package com.fastcampus.netplix.repository.subscription;

import com.fastcampus.netplix.entity.subscription.UserSubscriptionEntity;
import com.fastcampus.netplix.subscription.FetchUserSubscriptionPort;
import com.fastcampus.netplix.subscription.InsertUserSubscriptionPort;
import com.fastcampus.netplix.subscription.UpdateUserSubscriptionPort;
import com.fastcampus.netplix.subscription.UserSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserSubscriptionRepository implements FetchUserSubscriptionPort, InsertUserSubscriptionPort, UpdateUserSubscriptionPort {

    private final UserSubscriptionJpaRepository userSubscriptionJpaRepository;

    @Override
    public Optional<UserSubscription> findByUserId(String userId) {
        return userSubscriptionJpaRepository.findByUserId(userId)
                .map(UserSubscriptionEntity::toDomain);
    }

    @Override
    public void create(String userId) {
        UserSubscription userSubscription = UserSubscription.newSubscription(userId);
        userSubscriptionJpaRepository.save(UserSubscriptionEntity.toEntity(userSubscription));
    }

    @Override
    public void update(UserSubscription userSubscription) {
        userSubscriptionJpaRepository.save(UserSubscriptionEntity.toEntity(userSubscription));
    }
}
