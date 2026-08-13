package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.UserRegistrationDto;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.model.user.UserRoles;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public UserRegistrationDto userRegistration(UserRegistrationDto dto){
        if(userRepository.existsByEmail(dto.email())){
            System.out.println("This email address is already registered");
            throw new IllegalArgumentException("This email address is already registered");
        }

        User user = new User(dto);
        user.setRole(UserRoles.USER);

        userRepository.save(user);
        return dto;
    }
}
