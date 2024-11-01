package com.fastcampus.netplix.entity.subscription;

import com.fastcampus.netplix.audit.MutableBaseEntity;
import com.fastcampus.netplix.subscription.SubscriptionType;
import com.fastcampus.netplix.subscription.UserSubscription;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_subscription")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSubscriptionEntity extends MutableBaseEntity {

    @Id @Column(name = "USER_SUBSCRIPTION_ID")
    private String userSubscriptionId;

    @Column(name = "USER_ID")
    private String userId;

    @Enumerated(value = EnumType.STRING) @Column(name = "SUBSCRIPTION_NAME")
    private SubscriptionType subscriptionName;

    @Column(name = "START_AT")
    private LocalDateTime subscriptionStartAt;

    @Column(name = "END_AT")
    private LocalDateTime subscriptionEndAt;

    @Column(name = "VALID_YN")
    private Boolean validYn;

    public UserSubscription toDomain() {
        return UserSubscription.builder()
                .userId(this.userId)
                .subscriptionType(this.subscriptionName)
                .startAt(this.subscriptionStartAt)
                .endAt(this.subscriptionEndAt)
                .validYn(this.validYn)
                .build();
    }

    public static UserSubscriptionEntity toEntity(UserSubscription userSubscription) {
        return new UserSubscriptionEntity(
                UUID.randomUUID().toString(),
                userSubscription.getUserId(),
                userSubscription.getSubscriptionType(),
                userSubscription.getStartAt(),
                userSubscription.getEndAt(),
                userSubscription.getValidYn()
        );
    }
}