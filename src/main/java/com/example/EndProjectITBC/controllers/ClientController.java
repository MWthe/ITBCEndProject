package com.example.EndProjectITBC.controllers;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Login;
import com.example.EndProjectITBC.models.Role;
import com.example.EndProjectITBC.services.ClientService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = ("/clients/register"))
    public void registerClient(@RequestBody Client client) {
        clientService.registerClient(client);
    }

    @PostMapping(path = ("/clients/login"))
    public void logInClient(@RequestBody Login login) {
        clientService.logInClient(login);
    }

    @GetMapping
    public void getAllClients() {
        clientService.getClients();
    }

}

