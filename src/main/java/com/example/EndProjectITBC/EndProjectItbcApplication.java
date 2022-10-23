package com.example.EndProjectITBC;

import com.example.EndProjectITBC.models.Client;
import com.example.EndProjectITBC.models.Role;
import com.example.EndProjectITBC.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class EndProjectItbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(EndProjectItbcApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ClientService clientService) {
		return args -> {
			clientService.saveRole(new Role(null, "ROLE_USER"));
			clientService.saveRole(new Role(null, "ROLE_ADMIN"));


			clientService.registerClient(new Client("Nikola", "password", "janke.nikola@gmail.com", new ArrayList<>()));
			clientService.registerClient(new Client("Stefan", "password", "stefan@gmail.com", new ArrayList<>()));

			clientService.addRoleToClient("Nikola", "ROLE_ADMIN");
			clientService.addRoleToClient("Stefan", "ROLE_USER");
		};
	}
}
