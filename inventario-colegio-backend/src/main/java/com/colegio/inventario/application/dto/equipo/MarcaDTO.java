package com.colegio.inventario.application.dto.equipo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarcaDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
