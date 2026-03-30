package com.colegio.inventario.application.dto.personal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CargoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
