package com.example.EndProjectITBC.services;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Log;
import com.example.EndProjectITBC.models.Token;
import com.example.EndProjectITBC.repository.ClientRepository;
import com.example.EndProjectITBC.repository.LogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class LogServices {

    private final LogRepository logRepository;
    private final ClientRepository clientRepository;

    public LogServices(LogRepository logRepository, ClientRepository clientRepository) {
        this.logRepository = logRepository;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<?> newLog(Log log, @RequestHeader Token token) {

        if (log.getLogType().ordinal() > 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect logType");
        }

        if (log.getMessage().length() > 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Message should be less than 1024");
        }
        if (clientRepository.getClientById(UUID.fromString(token.getToken())).isPresent()) {
            log.setClientId(clientRepository.getClientByUUId(UUID.fromString(token.getToken())));
            log.setDateOfLog(LocalDate.now());
            logRepository.save(log);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect token");
        }
    }
}
