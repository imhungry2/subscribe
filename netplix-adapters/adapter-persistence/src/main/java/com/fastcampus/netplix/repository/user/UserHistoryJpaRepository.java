package com.fastcampus.netplix.repository.user;

import com.fastcampus.netplix.entity.user.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryJpaRepository extends JpaRepository<UserHistoryEntity, Long> {
}
