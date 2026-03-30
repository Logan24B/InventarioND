package com.colegio.inventario.application.mapper.hardware;

import com.colegio.inventario.application.dto.hardware.RomDTO;
import com.colegio.inventario.domain.catalogo.hardware.Rom;

public class RomMapper {

    public static RomDTO toDTO(Rom d) {
        if (d == null) return null;
        return new RomDTO(
                d.getId(),
                d.getTamano(),
                d.getTipoRom().getNombre()
        );
    }
}
