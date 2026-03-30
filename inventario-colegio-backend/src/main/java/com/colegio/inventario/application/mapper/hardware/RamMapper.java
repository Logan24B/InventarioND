package com.colegio.inventario.application.mapper.hardware;

import com.colegio.inventario.application.dto.hardware.RamDTO;
import com.colegio.inventario.domain.catalogo.hardware.Ram;

public class RamMapper {

    public static RamDTO toDTO(Ram d) {
        if (d == null) return null;
        return new RamDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
