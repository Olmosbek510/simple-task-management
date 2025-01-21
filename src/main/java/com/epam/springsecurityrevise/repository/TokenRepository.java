package com.epam.springsecurityrevise.repository;

import com.epam.springsecurityrevise.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
                SELECT t
                FROM Token t
                WHERE t.user.id = :userId
                  AND t.expired = FALSE
                  AND t.revoked = FALSE
            """)
    List<Token> findAllValidTokenByUser(@Param("userId") Long userId);

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("update Token t set t.revoked = true where t.user.id = :user_id")
    Integer revokeAllByUserId(@Param("user_id") Long userId);

    @Modifying
    @Transactional
    @Query("update Token t set t.revoked = true, t.expired = true where t.user.email = :email and t.revoked = false")
    Integer revokeAllByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("update Token t set t.expired = true, t.revoked = true where t.token = :token")
    Integer revokeToken(@Param("token") String jwt);
}