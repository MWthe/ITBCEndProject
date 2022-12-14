package com.example.EndProjectITBC.controllers;

import com.example.EndProjectITBC.models.Log;
import com.example.EndProjectITBC.models.Token;
import com.example.EndProjectITBC.repository.ClientRepository;
import com.example.EndProjectITBC.repository.LogRepository;
import com.example.EndProjectITBC.requests.SearchLogsRequest;
import com.example.EndProjectITBC.services.LogServices;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogRepository logRepository;
    private final ClientRepository clientRepository;
    private final LogServices logServices;

    @Autowired
    public LogController(LogRepository logRepository, ClientRepository clientRepository, LogServices logServices) {
        this.logRepository = logRepository;
        this.clientRepository = clientRepository;
        this.logServices = logServices;
    }

    @PostMapping(path = ("/create"))
    public ResponseEntity<?> createLog(@RequestBody Log log, @RequestHeader Token token) {
        return logServices.newLog(log, token);
    }

    @JsonProperty
    @GetMapping(path = ("/search"))
    public ResponseEntity<?> searchLogs(@RequestBody SearchLogsRequest searchLogsRequest,
                                        @RequestHeader Token token) {
        return logServices.searchAllLogsByClient(searchLogsRequest, token);
    }

}
