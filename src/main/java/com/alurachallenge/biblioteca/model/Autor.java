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
    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private  String fechaFallecimiento;
    
    public Autor () {
    
    }
    
    public Autor (String nombre, String fechaNacimiento, String fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
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

    @Override
    public String toString () {
        return "Autor{" +
                       "id=" + id +
                       ", nombre='" + nombre + '\'' +
                       ", fechaNacimiento='" + fechaNacimiento + '\'' +
                       ", fechaFallecimiento='" + fechaFallecimiento + '\'' +
                       '}';
    }
}
