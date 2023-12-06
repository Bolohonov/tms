package dev.bolohonov.tms.server.mappers;

import dev.bolohonov.tms.server.dto.UserDto;
import dev.bolohonov.tms.server.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getName()
        );
    }

    public static Collection<UserDto> toUserDto(Collection<User> users) {
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
