package com.itau.personnalite.cadastrocliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CadastroClienteItauPersonnaliteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroClienteItauPersonnaliteApplication.class, args);
	}

}
