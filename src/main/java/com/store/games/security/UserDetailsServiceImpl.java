package com.store.games.security;

import com.store.games.model.User;
import com.store.games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUser(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));

        return user.map(UserDetailsImpl::new).get();
       /* if (user.isPresent())
            else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);*/
    }
}

