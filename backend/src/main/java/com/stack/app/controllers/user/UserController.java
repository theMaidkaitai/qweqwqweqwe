package com.stack.app.controllers.user;

import com.stack.app.generalDTO.DeleteResponseUserDTO;
import com.stack.app.models.user.DTO.UserDTO;
import com.stack.app.models.user.UserEntity;
import com.stack.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Validated UserDTO userDTO) {
        try {
            UserDTO user = userService.createUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            DeleteResponseUserDTO response = userService.deleteUser(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }

    @GetMapping("/user/info/full")
    public ResponseEntity<String> getUserFullInfo(@RequestHeader("Authorization") String header) {
        try {
            String user = header.substring(7);

            return ResponseEntity.ok(user);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}