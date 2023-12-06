package dev.bolohonov.tms.server.services.impl;

import dev.bolohonov.tms.server.model.Token;
import dev.bolohonov.tms.server.repo.token.TokenRepository;
import dev.bolohonov.tms.server.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Optional<Token> findById(Long id) {
        return tokenRepository.findById(id);
    }
}
