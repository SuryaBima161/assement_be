package com.example.Backend_ApprenticeshipProgram.service;

import com.example.Backend_ApprenticeshipProgram.dto.UserDto;
import com.example.Backend_ApprenticeshipProgram.model.UserModel;
import com.example.Backend_ApprenticeshipProgram.repository.UserRepository;
import com.example.Backend_ApprenticeshipProgram.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(UserDto userDto) {

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);


        UserModel user = new UserModel();
        user.setUsername(userDto.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public UserModel loadByUser(String user) throws UsernameNotFoundException {
        return userRepository.findByUsername(user);
    }
}
