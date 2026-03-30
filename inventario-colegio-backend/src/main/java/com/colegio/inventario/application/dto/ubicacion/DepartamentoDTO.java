package com.colegio.inventario.application.dto.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DepartamentoDTO {

    private Long id;
    private String edificio;
    private String seccion;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
    private String descripcion;
}

