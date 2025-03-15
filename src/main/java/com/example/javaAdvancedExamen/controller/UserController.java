package com.example.javaAdvancedExamen.controller;

import com.example.javaAdvancedExamen.dto.UserDTO;
import com.example.javaAdvancedExamen.entity.User;
import com.example.javaAdvancedExamen.exceptions.GlobalExceptionHandler;
import com.example.javaAdvancedExamen.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends GlobalExceptionHandler {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "getAllUsers")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
            List<UserDTO> userDTO = userService.getAllUsers();
            return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "findUserById")
    @GetMapping("/{user-id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("user-id") Long id) {
        UserDTO foundUser = userService.getUserById(id);
        return ResponseEntity.ok(foundUser);
    }

    @Operation(summary = "createUser")
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        UserDTO newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "editUser")
    @PutMapping("/{user-id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable("user-id") Long id,
            @RequestBody User user) {
        UserDTO editUser = userService.editUser(id,user);
        return ResponseEntity.ok(editUser);
    }

    @Operation(summary = "searchUser")
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String naam) {
        return ResponseEntity.ok(userService.searchUserByName(naam));
    }

    @Operation(summary = "deleteUser")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id")Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(("Succesvol verwijderd!"));
    }
}
