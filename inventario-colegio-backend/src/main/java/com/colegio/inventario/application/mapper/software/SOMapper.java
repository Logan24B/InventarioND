package com.colegio.inventario.application.mapper.software;

import com.colegio.inventario.application.dto.software.SODTO;
import com.colegio.inventario.domain.catalogo.software.SO;

public class SOMapper {

    public static SODTO toDTO(SO d) {
        if (d == null) return null;
        return new SODTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
