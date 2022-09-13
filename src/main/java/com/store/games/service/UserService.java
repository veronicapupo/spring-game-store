package com.store.games.service;

import com.store.games.model.User;
import com.store.games.model.UserLogin;
import com.store.games.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerUser(User user) {

        if (userRepository.findByUser(user.getUser()).isPresent())
            return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }
    public Optional<User> updateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> searchUser = userRepository.findByUser(user.getUser());

            if ( (searchUser.isPresent()) && ( searchUser.get().getId() != user.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "User exists!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

}
    public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {

        Optional<User> user = userRepository.findByUser(userLogin.get().getUser());

        if (user.isPresent()) {

            if (comparepasswords(userLogin.get().getPassword(), user.get().getPassword())) {

                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setPhoto(user.get().getPhoto());
                userLogin.get().setToken(gerarBasicToken(userLogin.get().getUser(),        userLogin.get().getPassword()));
                userLogin.get().setPassword(user.get().getPassword());

                return userLogin;

            }
        }

        return Optional.empty();

    }

    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);

    }

    private boolean comparepasswords(String senhaDigitada, String senhaBanco) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String user, String password) {

        String token = user + ":" + password;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }

}

