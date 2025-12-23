package com.stack.app.controllers.jwt;


import com.stack.app.cfg.jwt.JwtConfig;
import com.stack.app.cfg.jwt.UserDetailsImpl;
import com.stack.app.generalDTO.AuthResponseTokenDTO;
import com.stack.app.models.user.DTO.UserDTO;
import com.stack.app.models.user.DTO.UserLoginDTO;
import com.stack.app.models.user.UserEntity;
import com.stack.app.repository.user.UserRepository;
import com.stack.app.service.jwt.JwtService;
import com.stack.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JwtController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtConfig jwtConfig;

    @PostMapping("/login")
    public ResponseEntity<?> auth (@RequestBody UserLoginDTO auth) {
        try {
            if (auth.nick() == null || auth.email() == null || auth.password() == null || auth.password().isEmpty()) {
                return ResponseEntity.badRequest().body("Неверные данные");
            }

            String token = jwtService.login(auth);

            return ResponseEntity.status(200).body(token);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        try {
            if (user.nick() == null || user.email() == null || user.password() == null || user.password().isEmpty()) {
                return ResponseEntity.badRequest().body("Неверные данные");
            }
            if (userRepository.existsByEmail(user.email())) {
                return ResponseEntity.badRequest().body("User already exists");
            }

            UserDTO createdUser = userService.createUser(user);
            UserEntity createdUserEntity = userRepository.findByEmail(user.email());

            UserDetailsImpl userDetails = UserDetailsImpl.build(createdUserEntity);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            String token =  jwtConfig.generateToken(authentication);

            AuthResponseTokenDTO userResponse = new AuthResponseTokenDTO(
                    token,
                    createdUserEntity.getId(),
                    createdUserEntity.getNick(),
                    createdUserEntity.getEmail(),
                    createdUserEntity.getRoles()
            );

            return ResponseEntity.status(200).body(userResponse);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
