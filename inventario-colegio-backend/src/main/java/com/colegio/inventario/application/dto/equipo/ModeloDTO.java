package com.colegio.inventario.application.dto.equipo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ModeloDTO {

    private Long id;
    private String marca;
    private String categoria;
    private String nombre;
    private String rom;
    private String ram;
    private LocalDate fecha;
    private String observaciones;
}
