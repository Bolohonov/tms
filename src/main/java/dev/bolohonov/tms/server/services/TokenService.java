package dev.bolohonov.tms.server.services;

import dev.bolohonov.tms.server.model.Token;

import java.util.Optional;

public interface TokenService {

    Token saveToken(Token token);

    Optional<Token> findById(Long id);

}
