package io.tamknown.springreddit.repository;

import io.tamknown.springreddit.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByQwerty(String token);
    @Transactional
    void deleteByQwerty(String token);
}
