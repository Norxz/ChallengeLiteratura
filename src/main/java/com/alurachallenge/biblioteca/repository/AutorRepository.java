package com.alurachallenge.biblioteca.repository;

import com.alurachallenge.biblioteca.model.Autor;
import com.alurachallenge.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    List<Autor> findByNombreContaining(String nombre);
    
    

}
