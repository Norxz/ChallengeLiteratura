package com.alurachallenge.biblioteca;

import com.alurachallenge.biblioteca.principal.Principal;
import com.alurachallenge.biblioteca.repository.AutorRepository;
import com.alurachallenge.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication  implements CommandLineRunner {
	
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	
	
	public static void main(String[] args)  {
		
		SpringApplication.run(BibliotecaApplication.class, args);
	}
	
	@Override
	public void run (String... args) throws Exception {
		Principal principal = new Principal (libroRepository, autorRepository);
		principal.iniciar ();
	}
}
