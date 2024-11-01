package com.fastcampus.netplix.repository.user;

import com.fastcampus.netplix.entity.user.UserHistoryEntity;
import com.fastcampus.netplix.user.UserHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserHistoryRepository implements UserHistoryPort {

    private final UserHistoryJpaRepository userHistoryJpaRepository;

    @Override
    public void create(String userId, String userRole, String clientIp, String reqMethod, String reqUrl, String reqHeader, String reqPayload) {
        userHistoryJpaRepository.save(
                new UserHistoryEntity(
                        userId, userRole, clientIp, reqMethod, reqUrl, reqHeader, reqPayload)
        );
    }
}