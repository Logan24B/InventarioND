package com.colegio.inventario.application.dto.software;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfimaticaDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
