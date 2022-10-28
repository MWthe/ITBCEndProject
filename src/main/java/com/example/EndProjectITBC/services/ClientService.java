package com.example.EndProjectITBC.services;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Login;
import com.example.EndProjectITBC.models.Token;
import com.example.EndProjectITBC.repository.ClientRepository;
import com.example.EndProjectITBC.repository.LogRepository;
import com.example.EndProjectITBC.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final LogRepository logRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, LogRepository logRepository) {
        this.clientRepository = clientRepository;
        this.logRepository = logRepository;
    }

    //Checks if email entered is valid
    public boolean validEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    //Checks if password entered is valid
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

    public ResponseEntity<?> getClients(@RequestHeader Token token) {
        if (clientRepository.getClientById(UUID.fromString(token.getToken())).isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect token!");
        }

        if (clientRepository.getClientById(UUID.fromString(token.getToken())).get().getClientType() == 2) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correct token, but not admin!");
        }

        if (clientRepository.getClientById(UUID.fromString(token.getToken())).get().getClientType() == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.findAll() + " " + logRepository.getLogCount(UUID.fromString(token.getToken())));
        }

        return ResponseEntity.status(HttpStatus.OK).body("Ok");
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

    public ResponseEntity<?> updateClientPassword(@RequestHeader Token token, UUID id, UpdatePasswordRequest password) {

        if (clientRepository.getClientById(UUID.fromString(token.getToken())).isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect token!");
        }

        if (clientRepository.getClientById(UUID.fromString(token.getToken())).get().getClientType() == 2) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correct token, but not admin!");
        }

        Client client = clientRepository.findById(id).get();
        client.setPassword(password.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(client));
    }
}
