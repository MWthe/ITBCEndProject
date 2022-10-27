package com.example.EndProjectITBC.controllers;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Login;
import com.example.EndProjectITBC.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = ("/register"))
    public void registerClient(@RequestBody Client client) {
        clientService.registerClient(client);
    }

    @PostMapping(path = ("/register/admin"))
    public void registerAdmin(@RequestBody Client admin) {
        clientService.registerAdmin(admin);
    }

    @PostMapping(path = ("/login"))
    public ResponseEntity<?> logInClient(@RequestBody Login login) {
        return clientService.logInClient(login);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getClients();
    }

}

