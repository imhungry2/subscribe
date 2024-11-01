package com.fastcampus.netplix.repository.token;

import com.fastcampus.netplix.entity.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, String>, TokenCustomRepository {
}
