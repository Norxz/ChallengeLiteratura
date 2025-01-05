package com.alurachallenge.biblioteca.dto;

import java.util.List;

public record LibroDTO (Long id,
                        String titulo,
                        List<AutorDTO> autores,
                        String idiomas,
                        Double numeroDeDescargas) {
}
