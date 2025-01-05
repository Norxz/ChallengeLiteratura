package com.alurachallenge.biblioteca.principal;

import com.alurachallenge.biblioteca.model.*;
import com.alurachallenge.biblioteca.repository.*;
import com.alurachallenge.biblioteca.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    
    private Scanner sc = new Scanner (System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI ();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos ();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;
    
    public Principal (LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    
    public void iniciar () {
        
        while (true){
            System.out.println ("""
                    --------------MENU-----------
                    1. Buscar libro por titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar libros por idioma
                    
                    5. Salir
                    ------------------------------
                    """);
            
            int opt = sc.nextInt ();
            sc.nextLine ();
            
            switch (opt){
                case 1->buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarLibrosPorIdioma();
                case 5 -> {
                    System.out.println ("HASTA LUEGO :D");
                    return;
                }
                default -> System.out.println ("Opcion no valida");
            }
        }
    }
    
    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro:");
        String titulo = sc.nextLine();
        libroRepository.findByTitulo(titulo).ifPresentOrElse(
                libro -> {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Idiomas: " + libro.getIdiomas());
                    System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                    System.out.println("Autores: ");
                    libro.getAutores().forEach(autor -> System.out.println("- " + autor.getNombre()));
                },
                () -> System.out.println("No se encontró un libro con el título especificado.")
        );
    }
    
    private void listarLibros() {
        List<Libro> libros = libroRepository.findAllWithAutores();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("Autores: ");
                libro.getAutores().forEach(autor -> System.out.println("- " + autor.getNombre()));
                System.out.println("----------------------------");
            });
        }
    }
    
    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de fallecimiento: " + (autor.getFechaFallecimiento() == null ? "N/A" : autor.getFechaFallecimiento()));
                System.out.println("----------------------------");
            });
        }
    }
    
    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (en inglés) para buscar libros:");
        String idioma = sc.nextLine();
        List<Libro> libros = libroRepository.findByIdiomasContaining(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("Autores: ");
                libro.getAutores().forEach(autor -> System.out.println("- " + autor.getNombre()));
                System.out.println("----------------------------");
            });
        }
    }
    
}