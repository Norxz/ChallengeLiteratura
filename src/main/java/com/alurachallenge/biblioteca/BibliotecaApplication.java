package com.alurachallenge.biblioteca;

import com.alurachallenge.biblioteca.principal.Principal;
import com.alurachallenge.biblioteca.repository.AutorRepository;
import com.alurachallenge.biblioteca.repository.LibroRepository;
import com.alurachallenge.biblioteca.service.ConsumoAPI;
import com.alurachallenge.biblioteca.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class BibliotecaApplication  implements CommandLineRunner {
	
	private final Principal principal;
	
	public 	BibliotecaApplication(@Lazy Principal principal){
		this.principal=principal;
	}
	
	public static void main(String[] args)  {
		
		SpringApplication.run(BibliotecaApplication.class, args);
	}
	
	@Override
	public void run (String... args) throws Exception {
		principal.iniciar ();
	}
	@Bean
	public ConsumoAPI consumoAPI(){
		return new ConsumoAPI ();
	}
	
	@Bean
	public ConvierteDatos conversor() {
		
		return new ConvierteDatos();
	}
	
	public Principal getPrincipal() {
		
		return principal;
	}
}
