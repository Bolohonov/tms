package dev.bolohonov.tms.api.security.services;

import dev.bolohonov.tms.api.security.UserDetailsHelper;
import dev.bolohonov.tms.server.errors.ApiError;
import dev.bolohonov.tms.server.model.User;
import dev.bolohonov.tms.server.repo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsHelper detailsHelper;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserDetailsHelper detailsHelper) {
        this.userRepository = userRepository;
        this.detailsHelper = detailsHelper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) {
        User user = userRepository.findByName(name).orElseThrow(
                () -> new ApiError(HttpStatus.NOT_FOUND, "Пользователь не найден",
                        String.format("При выполнении %s не найден %s c именем %s",
                                "аутентификации", "пользователь", name))
        );

        return detailsHelper.buildDetails(user);
    }
}
