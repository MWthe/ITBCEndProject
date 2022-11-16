package com.example.EndProjectITBC.models;

import com.example.EndProjectITBC.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

public class MyUserDetailService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public MyUserDetailService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var client = clientRepository.findClientByUsernameOpt(username);

        if (client.isEmpty()) {
            client = clientRepository.findClientByEmail(username);
            if (client.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password are incorrect!");
            }
        }

        return new User(
                client.get().getUsername(),
                client.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("Role_" + client.get().getClientType())));
    }
}
