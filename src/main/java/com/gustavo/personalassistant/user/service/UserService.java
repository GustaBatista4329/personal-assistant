package com.gustavo.personalassistant.user.service;

import com.gustavo.personalassistant.user.dto.UserRegistrationDto;
import com.gustavo.personalassistant.user.dto.UserSummaryDto;
import com.gustavo.personalassistant.user.dto.UserUpdateDto;
import com.gustavo.personalassistant.infra.exception.NotFoundException;
import com.gustavo.personalassistant.user.model.User;
import com.gustavo.personalassistant.user.model.UserRoles;
import com.gustavo.personalassistant.user.repository.UserRepository;
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
    public UserSummaryDto userRegistration(UserRegistrationDto dto){
        if(userRepository.existsByEmail(dto.email())){
            throw new IllegalArgumentException("This email address is already registered");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto);
        user.setPassword(hashedPassword);
        user.setRole(UserRoles.USER);

        userRepository.save(user);
        return new UserSummaryDto(user);
    }

    @Transactional
    public UserSummaryDto updateUser(UUID userId, UserUpdateDto dto){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::userNotFound);

        user.userUpdate(dto);

        return new UserSummaryDto(user);
    }
}
