package com.example.EndProjectITBC.services;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Login;
import com.example.EndProjectITBC.models.Role;
import com.example.EndProjectITBC.repository.ClientRepository;
import com.example.EndProjectITBC.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, RoleRepository roleRepository) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
    }

    public boolean validEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    public Role saveRole(Role role) {
        return  roleRepository.save(role);
    }

    public void addRoleToClient(String username, String roleName) {
        Optional<Client> client = clientRepository.findClientByUsername(username);
        Role role = roleRepository.findByName(roleName);
        client.get().getRoles().add(role);
    }

    //Come back to this (not working)
    public boolean validPassword(String password) {
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        char[] chars = password.toCharArray();
        for (var ch : chars) {
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }

            if (numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    public void registerClient(Client client) {
        Optional<Client> username = clientRepository.findClientByUsername(client.getUsername());
        Optional<Client> email = clientRepository.findClientByEmail(client.getEmail());
                                                //(not working)
        if (!validEmail(client.getEmail()) && !validPassword(client.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (username.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (email.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        clientRepository.save(client);
        throw new ResponseStatusException(HttpStatus.CREATED);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void logInClient(Login login) {

    }
}
