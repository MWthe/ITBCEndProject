package com.example.EndProjectITBC.services;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Login;
import com.example.EndProjectITBC.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientService {

    private final ClientRepository clientRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    public boolean validEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
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

        if (!(validPassword(client.getPassword()) && validEmail(client.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (username.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (email.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        client.setClientType(2);
        clientRepository.save(client);
        throw new ResponseStatusException(HttpStatus.CREATED);
    }

    public void registerAdmin(Client client) {
        Optional<Client> username = clientRepository.findClientByUsername(client.getUsername());
        Optional<Client> email = clientRepository.findClientByEmail(client.getEmail());

        if (!(validPassword(client.getPassword()) && validEmail(client.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (username.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (email.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        client.setClientType(1);
        clientRepository.save(client);
        throw new ResponseStatusException(HttpStatus.CREATED);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public ResponseEntity<?> logInClient(Login login) {
        Map<String, String> map = new HashMap<>();
        if (clientRepository.findClientByUsername(login.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is not valid!");
        } else if (clientRepository.findClientByUsername(login.getUsername()).isEmpty()) {

        }

        if (clientRepository.findClientByPassword(login.getPassword()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is not valid!");
        }

        map.put("token", clientRepository.findClientByUsername(login.getUsername()).get().getId().toString());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
