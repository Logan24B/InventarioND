package com.colegio.inventario.application.dto.hardware;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RomDTO {

    private Long id;
    private Integer tamano;
    private String tipoRom;
}
