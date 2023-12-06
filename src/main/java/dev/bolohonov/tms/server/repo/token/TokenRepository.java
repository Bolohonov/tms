package dev.bolohonov.tms.server.repo.token;

import dev.bolohonov.tms.server.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
