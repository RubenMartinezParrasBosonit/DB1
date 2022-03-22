package io.github.ruben;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class Db1Application{

	@Autowired
	PersonaRepositorio personaRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(Db1Application.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(PersonaService personaService){

		return args -> {
			personaService.aniadirPersona(new PersonaInputDto(new Persona(1, "Johnny", "1234"
					, "Ruf", "Mar", "r@gmail.com", "r@gmail.com", "Jaén"
					, true, new Date(), "imgurl", new Date(), true)));

			personaService.aniadirPersona(new PersonaInputDto(new Persona(2, "Manuel", "1234"
					, "Ruf", "Mar", "r@gmail.com", "r@gmail.com", "Jaén"
					, true, new Date(), "imgurl", new Date(), false)));
		};

	}

}
