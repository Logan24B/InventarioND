package com.colegio.inventario.application.mapper.hardware;

import com.colegio.inventario.application.dto.hardware.TipoRomDTO;
import com.colegio.inventario.domain.catalogo.hardware.TipoRom;

public class TipoRomMapper {

    public static TipoRomDTO toDTO(TipoRom d) {
        if (d == null) return null;
        return new TipoRomDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
