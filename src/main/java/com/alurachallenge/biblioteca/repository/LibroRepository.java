package com.alurachallenge.biblioteca.repository;

import com.alurachallenge.biblioteca.model.Autor;
import com.alurachallenge.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    @EntityGraph (attributePaths = "autores")
    Optional<Libro> findByTitulo(String titulo);
    
    @Query ("SELECT l FROM Libro l LEFT JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();
    
    @EntityGraph(attributePaths = "autores")
    List<Libro> findByIdiomasContaining(String idiomas);
    
    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();
    
}
