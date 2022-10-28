package com.example.EndProjectITBC.services;

import com.example.EndProjectITBC.models.Log;
import com.example.EndProjectITBC.models.Token;
import com.example.EndProjectITBC.repository.ClientRepository;
import com.example.EndProjectITBC.repository.LogRepository;
import com.example.EndProjectITBC.requests.SearchLogsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
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

    public ResponseEntity<?> searchAllLogsByClient(SearchLogsRequest searchLogsRequest, Token token) {

        if (searchLogsRequest.getLogType() > 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect logType!");
        }

        if (searchLogsRequest.getDateFrom() == null || searchLogsRequest.getDateTo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
        }

        if (clientRepository.getClientById(UUID.fromString(token.getToken())).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect token");
        }

        return ResponseEntity.status(HttpStatus.OK).body(logRepository.getAllLogsByClient(UUID.fromString(token.getToken()),
                searchLogsRequest.getDateFrom(),
                searchLogsRequest.getDateTo()));
    }
}
