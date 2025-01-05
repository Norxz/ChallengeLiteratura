package com.alurachallenge.biblioteca.repository;

import com.alurachallenge.biblioteca.model.Autor;
import com.alurachallenge.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    @Query("SELECT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> findAutoresConLibros();
    
    Optional<Autor> findByNombre(String nombre);
}
