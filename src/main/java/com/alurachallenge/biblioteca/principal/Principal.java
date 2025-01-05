package com.alurachallenge.biblioteca.principal;

import com.alurachallenge.biblioteca.model.*;
import com.alurachallenge.biblioteca.repository.*;
import com.alurachallenge.biblioteca.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    
    private static final String URL_BASE = "https://gutendex.com/books/";
    
    
    @Autowired
    private ConsumoAPI consumoAPI;
    
    @Autowired
    private ConvierteDatos conversor;
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    private final Scanner sc;
    private boolean ejecutando;
    
    
    public Principal() {
        this.sc = new Scanner(System.in);
        this.ejecutando = true;
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
        try {
            System.out.println ("Ingrese el titulo del libro: ");
            String tituloLibro = sc.nextLine ().trim ();
            
            if (tituloLibro.isEmpty ()) {
                System.out.println ("El titulo no puede null");
                return;
            }
            
            Optional<Libro> libroBD = libroRepository.findByTituloContainingIgnoreCase (tituloLibro);
            if (libroBD.isPresent ()) {
                System.out.println ("Libro en la base de datos: " + libroBD.get ());
                return;
            }
            
            System.out.println ("Buscando en la API externa.....");
            String libroAPI = URL_BASE + "?search=" + URLEncoder.encode (tituloLibro, StandardCharsets.UTF_8);
            String json = consumoAPI.obtenerDatos (libroAPI);
            
            if (json == null || json.isEmpty ()) {
                System.out.println ("No hay respuesta de la API");
                return;
            }
            
            Datos datosBusqueda = conversor.obtenerDatos (json, Datos.class);
            
            if (datosBusqueda.listaResultados () == null || datosBusqueda.listaResultados ().isEmpty ()) {
                System.out.println ("No se encontraron resultados para: " + tituloLibro);
                return;
            }
            
            procesarResultadosBusqueda (datosBusqueda.listaResultados (), tituloLibro);
        }catch (Exception e){
            System.out.println("Error durante la búsqueda: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void procesarResultadosBusqueda(List<DatosLibro> resultados, String tituloLibro) {
        boolean encontrado = false;
        for (DatosLibro datosLibro : resultados) {
            if (datosLibro.titulo().toUpperCase().contains(tituloLibro.toUpperCase())) {
                guardarLibroYAutor(datosLibro);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            System.out.println("No se encontró ningún libro que coincida exactamente con: " + tituloLibro);
        }
    }
    
    private void guardarLibroYAutor(DatosLibro datosLibro) {
        try {
            if (datosLibro.autor() == null || datosLibro.autor().isEmpty()) {
                System.out.println("El libro no tiene autor registrado.");
                return;
            }
            
            // Guardar o recuperar el autor
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                                  .orElseGet(() -> {
                                      Autor nuevoAutor = new Autor(datosAutor);
                                      System.out.println("Guardando nuevo autor: " + datosAutor.nombre());
                                      return autorRepository.save(nuevoAutor);
                                  });
            
            // Verificar si el libro ya existe
            if (libroRepository.findByTituloContainingIgnoreCase(datosLibro.titulo()).isPresent()) {
                System.out.println("El libro ya existe en la base de datos.");
                return;
            }
            
            // Guardar el nuevo libro
            Libro libro = new Libro(datosLibro, autor);
            Libro libroGuardado = libroRepository.save(libro);
            System.out.println("\nLibro guardado exitosamente:");
            System.out.println(libroGuardado);
            
        } catch (Exception e) {
            System.out.println("Error al guardar el libro y autor: " + e.getMessage());
        }
    }
    
    
    private void listarLibros() {
        System.out.println ("LIBROS EN BS: ");
        List<Libro> libros = libroRepository.findAll ();
        if (libros.isEmpty ()){
            System.out.println ("NO HAY LIBROS EN BS");
            return;
        }
        libros.forEach (System.out::println);
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
        System.out.println ("""
                INGRESE EL IDIOMA
                
                ES - ESPANOL
                EN - INGLES
                
                """);
        
        String idioma = sc.nextLine ().trim ().toUpperCase ();
        if (!esIdiomaValido(idioma)){
            System.out.println ("Idioma no valido");
            return;
        }
        
        List<Libro> libros = libroRepository.findAll ();
        List<Libro> librosIdiomas = libros.stream()
                                            .filter(l -> l.getIdioma() .equalsIgnoreCase (idioma))
                                            .toList();
        
        if (librosIdiomas.isEmpty ()) {
            System.out.println("No se encontraron libros en " + obtenerNombreIdioma(idioma));
            return;
        }
    
    }
    
    private boolean esIdiomaValido(String idioma) {
        
        return idioma.matches("^(ES|EN|FR|PT)$");
    }
    
    private String obtenerNombreIdioma(String codigo) {
        return switch (codigo) {
            case "ES" -> "Español";
            case "EN" -> "Inglés";
            case "FR" -> "Francés";
            case "PT" -> "Portugués";
            default -> codigo;
        };
    }
}