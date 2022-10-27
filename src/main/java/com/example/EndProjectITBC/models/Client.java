package com.example.EndProjectITBC.models;

import com.example.EndProjectITBC.enums.ClientType;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clientid", nullable = false)

    private UUID id;
    private String username;
    private String password;
    private String email;
    @Column (name = "client_type")
    private Integer clientType;

    public Client() {
    }

    public Client(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Client(UUID id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
