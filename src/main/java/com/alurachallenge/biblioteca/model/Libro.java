package com.alurachallenge.biblioteca.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "libros")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String titulo;
    
    @Column(name = "idiomas")
    private String idiomas;
    
    private Integer numeroDeDescargas;
    
    @ManyToOne
    private Autor autor;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores = new ArrayList<> ();
    
    public Libro () {
    }
    
    public Libro (String titulo, String idiomas, Integer numeroDeDescargas) {
        this.titulo = titulo;
        this.idiomas = idiomas;
        this.numeroDeDescargas = numeroDeDescargas;
    }
    
    public Long getId () {
        return id;
    }
    
    public void setId (Long id) {
        this.id = id;
    }
    
    public String getTitulo () {
        return titulo;
    }
    
    public void setTitulo (String titulo) {
        this.titulo = titulo;
    }
    
    public String getIdiomas () {
        return idiomas;
    }
    
    public void setIdiomas (String idiomas) {
        this.idiomas = idiomas;
    }
    
    public Integer getNumeroDeDescargas () {
        return numeroDeDescargas;
    }
    
    public void setNumeroDeDescargas (Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
    
    public List<Autor> getAutores () {
        return autores;
    }
    
    public void setAutores (List<Autor> autores) {
        this.autores = autores;
    }
    
    @Override
    public String toString () {
        return "Libro{" +
                       "id=" + id +
                       ", titulo='" + titulo + '\'' +
                       ", idiomas='" + idiomas + '\'' +
                       ", numeroDeDescargas=" + numeroDeDescargas +
                       ", autores=" + autores +
                       '}';
    }
}
