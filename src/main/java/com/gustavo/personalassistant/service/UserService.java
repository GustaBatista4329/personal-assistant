package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.userDto.UserRegistrationDto;
import com.gustavo.personalassistant.dto.userDto.UserResponseDto;
import com.gustavo.personalassistant.dto.userDto.UserUpdateDto;
import com.gustavo.personalassistant.exception.NotFoundException;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.model.user.UserRoles;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    @Transactional
    public User userRegistration(UserRegistrationDto dto){
        if(userRepository.existsByEmail(dto.email())){
            throw new IllegalArgumentException("This email address is already registered");
        }

        User user = new User(dto);
        user.setRole(UserRoles.USER);

        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserResponseDto updateUser(UUID userId, UserUpdateDto dto){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::userNotFound);

        user.userUpdate(dto);

        return new UserResponseDto(user);
    }
}
