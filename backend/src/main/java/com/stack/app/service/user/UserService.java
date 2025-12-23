package com.stack.app.service.user;


import com.stack.app.generalDTO.DeleteResponseUserDTO;
import com.stack.app.models.user.DTO.UserDTO;
import com.stack.app.models.user.UserEntity;
import com.stack.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDTO createUser (UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("User with" + userDTO.email() + "already exist!");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.password());

        UserEntity newUser = new UserEntity(userDTO.nick(), userDTO.email(), encodedPassword);
        UserEntity savedUser = (UserEntity) userRepository.save(newUser);

        return new UserDTO(null ,savedUser.getNick(), "*******", savedUser.getEmail(), savedUser.getRoles());
    }

    public DeleteResponseUserDTO deleteUser (Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " doesn't exist"));
        userRepository.deleteById(id);

        return new DeleteResponseUserDTO(
                user.getId(),
                user.getNick() +
                "User deleted!"
        );
    }

    public boolean checkPassword (String rawPassword, String encodedPassword) {
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            return true;
        }
        return false;
    }


}
