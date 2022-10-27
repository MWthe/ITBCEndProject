package com.example.EndProjectITBC.models;

import com.example.EndProjectITBC.enums.LogType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String message;


    private LogType logType;

    private LocalDate dateOfLog;

    @ManyToOne
    @JoinColumn(name = "clientid")
    private Client clientid;

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    public Log() {
    }

    public Log(String message, LogType logType) {
        this.message = message;
        this.logType = logType;
    }

    public Log(UUID id, String message, LogType logType) {
        this.id = id;
        this.message = message;
        this.logType = logType;
        this.dateOfLog = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientId(Client clientid) {
        this.clientid = clientid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public LocalDate getDateOfLog() {
        return dateOfLog;
    }

    public void setDateOfLog(LocalDate dateOfLog) {
        this.dateOfLog = dateOfLog;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", logType=" + logType +
                ", dateOfLog=" + dateOfLog +
                '}';
    }
}
