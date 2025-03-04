package com.alurachallenge.biblioteca.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    
    @Column(name = "fechaNacimiento")
    private String fechaNacimiento;
    
    @Column(name = "fechaFallecimiento")
    private  String fechaFallecimiento;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;

    public Autor () {
    
    }
    
    public Autor (String nombre, String fechaNacimiento, String fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }
    
    public Autor (DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento ();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }
    
    public Long getId () {
        return id;
    }
    
    public void setId (Long id) {
        this.id = id;
    }
    
    public String getNombre () {
        return nombre;
    }
    
    public void setNombre (String nombre) {
        this.nombre = nombre;
    }
    
    public String getFechaNacimiento () {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento (String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getFechaFallecimiento () {
        return fechaFallecimiento;
    }
    
    public void setFechaFallecimiento (String fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }
    
    public List<Libro> getLibros () {
        return libros;
    }
    
    public void setLibros (List<Libro> libros) {
        this.libros = libros;
    }
    
}
