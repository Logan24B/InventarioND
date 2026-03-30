package com.colegio.inventario.application.dto.hardware;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcesadorDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
