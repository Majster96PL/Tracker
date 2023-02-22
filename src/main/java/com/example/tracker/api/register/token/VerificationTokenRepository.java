package com.example.tracker.api.register.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

  /*  @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.confirmed = ?2 WHERE t.token = ?1")
    int updateToken(String token, LocalDateTime confirmed); */
}
