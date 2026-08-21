package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.userDto.UserRegistrationDto;
import com.gustavo.personalassistant.dto.userDto.UserResponseDto;
import com.gustavo.personalassistant.dto.userDto.UserUpdateDto;
import com.gustavo.personalassistant.infra.exception.NotFoundException;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.model.user.UserRoles;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDto userRegistration(UserRegistrationDto dto){
        if(userRepository.existsByEmail(dto.email())){
            throw new IllegalArgumentException("This email address is already registered");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto);
        user.setPassword(hashedPassword);
        user.setRole(UserRoles.USER);

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(UUID userId, UserUpdateDto dto){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::userNotFound);

        user.userUpdate(dto);

        return new UserResponseDto(user);
    }
}
