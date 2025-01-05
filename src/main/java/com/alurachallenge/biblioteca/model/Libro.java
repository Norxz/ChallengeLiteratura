package com.alurachallenge.biblioteca.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "libros")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    @Column(name = "numero_de_descargas")
    private Double numeroDeDescargas;
    private List<String> subjects;
    private List<String> bookshelves;
    public Libro () {
    }
    
    public Libro(DatosLibro datosLibro, Autor autor){
        this.titulo = datosLibro.titulo ();
        this.autor = autor;
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDeDescargas=datosLibro.numeroDeDescargas ();
        this.subjects = datosLibro.subjects();
        this.bookshelves = datosLibro.bookshelves();
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
    
    public Autor getAutor () {
        return autor;
    }
    
    public void setAutor (Autor autor) {
        this.autor = autor;
    }
    
    public String getIdioma () {
        return idioma;
    }
    
    public void setIdioma (String idioma) {
        this.idioma = idioma;
    }
    
    
    public Double getNumeroDeDescargas () {
        return numeroDeDescargas;
    }
    
    public void setNumeroDeDescargas (Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
    
    public List<String> getSubjects () {
        return subjects;
    }
    
    public void setSubjects (List<String> subjects) {
        this.subjects = subjects;
    }
    
    public List<String> getBookshelves () {
        return bookshelves;
    }
    
    public void setBookshelves (List<String> bookshelves) {
        this.bookshelves = bookshelves;
    }
    
    @Override
    public String toString () {
        return "Libro{" +
                       "id=" + id +
                       ", titulo='" + titulo + '\'' +
                       ", autor=" + autor +
                       ", idioma='" + idioma + '\'' +
                       ", numeroDeDescargas=" + numeroDeDescargas +
                       ", subjects=" + subjects +
                       ", bookshelves=" + bookshelves +
                       '}';
    }
}
