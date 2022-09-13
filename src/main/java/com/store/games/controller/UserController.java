package com.store.games.controller;

import com.store.games.model.User;
import com.store.games.model.UserLogin;
import com.store.games.repository.UserRepository;
import com.store.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping
    public ResponseEntity<User> post(@Valid @RequestBody User user) {
                return ResponseEntity.status(HttpStatus.CREATED)
                .body(userRepository.save(user));
    }

    @PostMapping("/logar")
    public ResponseEntity<UserLogin> login(@RequestBody Optional<UserLogin> userLogin) {
        return userService.authenticateUser(userLogin)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {

        return userService.registerUser(user)
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @PutMapping
    public ResponseEntity<User> put(@Valid @RequestBody User user) {
        return userRepository.findById(user.getId())
                .map(resposta-> ResponseEntity.status(HttpStatus.OK)
                        .body(userRepository.save(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}

