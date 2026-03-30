package com.colegio.inventario.application.mapper.hardware;

import com.colegio.inventario.application.dto.hardware.CategoriaHardwareDTO;
import com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware;

public class CategoriaHardwareMapper {

    public static CategoriaHardwareDTO toDTO(CategoriaHardware d) {
        if (d == null) return null;
        return new CategoriaHardwareDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
