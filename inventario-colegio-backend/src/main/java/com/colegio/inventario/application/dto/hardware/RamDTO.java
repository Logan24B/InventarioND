package com.colegio.inventario.application.dto.hardware;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RamDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
