package com.stack.app.service.jwt;

import com.stack.app.cfg.jwt.JwtConfig;
import com.stack.app.cfg.jwt.UserDetailsImpl;
import com.stack.app.models.user.DTO.UserDTO;
import com.stack.app.models.user.DTO.UserLoginDTO;
import com.stack.app.models.user.UserEntity;
import com.stack.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class JwtService {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String login (UserLoginDTO userDTO) {
        UserEntity user = userRepository.findByEmail(userDTO.email());

        if (user == null) {
            throw new IllegalArgumentException("User with" + userDTO.email() + " doesnt exist!");
        }

        if (!passwordEncoder.matches(userDTO.password(), user.getPassword())) {
            throw new IllegalArgumentException("Passwords don't match!");
        }

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
                null,
                userDetails.getAuthorities()
        );

        return jwtConfig.generateToken(authentication);

    }


    public Long getUserById(String token) {
        return jwtConfig.getUserIdFromToken(token);
    }

}
