package com.colegio.inventario.application.dto.equipo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolEquipoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
